package com.framework.utils.base;

import java.util.ArrayDeque;
import java.util.Deque;

import com.framework.utils.lang.constants.Chars;
import com.framework.utils.lang.constants.Strings;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Copyright: Copyright (c) 2020 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 1.0.0 2021/1/4:12:01
 * @since 1.0.0
 */
public class FileUtils {

	/**
	 * 从 Java 资源路径中提取文件名
	 * 例如 {@code "mypath/myfile.txt" -> "myfile.txt"}.
	 * @param path 文件路径( 有可能为 {@code null} )
	 * @return 提取的文件名，如果没有，则为 {@code null}
	 */
	@Nullable
	public static String getFilename(@Nullable String path) {
		if (path == null) {
			return null;
		}

		int separatorIndex = path.lastIndexOf(Strings.FOLDER_SEPARATOR);
		return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
	}


	/**
	 * 从 Java 资源路径中提取文件名扩展
	 * 例如. "mypath/myfile.txt" -> "txt".
	 * @param path 文件路径( 有可能为 {@code null} )
	 * @return 提取的文件名扩展，如果没有，则为 {@code null}
	 */
	@Nullable
	public static String getFilenameExtension(@Nullable String path) {
		if (path == null) {
			return null;
		}

		int extIndex = path.lastIndexOf(Strings.DOT);
		if (extIndex == -1) {
			return null;
		}

		int folderIndex = path.lastIndexOf(Strings.FOLDER_SEPARATOR);
		if (folderIndex > extIndex) {
			return null;
		}

		return path.substring(extIndex + 1);
	}

	/**
	 * 从 Java 资源路径中删除文件名扩展
	 * 例如 : "mypath/myfile.txt" -> "mypath/myfile".
	 * @param path 文件路径
	 * @return 删除文件扩展名后的路径字符串
	 */
	public static String stripFilenameExtension(String path){
		int extIndex = path.lastIndexOf(Strings.DOT);

		if(extIndex == -1){
			return path;
		}

		int folderIndex = path.lastIndexOf(Strings.FOLDER_SEPARATOR);

		if(folderIndex > extIndex){
			return path;
		}

		return path.substring(0,extIndex);
	}


	/**
	 * 拼接路径，使用标准的文件夹分隔符 {@code /}
	 * @param basePath 基本路径
	 * @param relativePath 相对路径
	 * @return 返回拼接后的路径
	 */
	public static String applyRelativePath(String basePath,String relativePath){
		int separatorIndex = basePath.lastIndexOf(Strings.FOLDER_SEPARATOR);
		if(separatorIndex != -1){
			String newPath = basePath.substring(0, separatorIndex);
			if(!relativePath.startsWith(Strings.FOLDER_SEPARATOR)){
				newPath += Strings.FOLDER_SEPARATOR;
			}
			return newPath + relativePath;
		} else {
			return relativePath;
		}
	}

	/**
	 * 规范化路径。通过删除 "path/.." 之间的 .. 等之类来规范化路径。主要有以下过程
	 * <ul>
	 *     <li> 1、将  Windows 分隔符 ("\")  替换为简单的斜杠 ("/")</li>
	 *     <li> 2、例如:"file:core/../core/io/Resource.class" ,"core/.." 应该删除，但是 file: 应该保留。</li>
	 * </ul>
	 *
	 * <p><strong>NOTE</strong> formatPath 不应在安全上下文中依赖。 应该使用其他机制来防止路径遍历问题。
	 * @param path 原始路径
	 * @return 规范化后的路径
	 */
	public static String formatPath(String path){
		if(StringUtils.isEmpty(path)){
			return path;
		}

		String pathToUse = StringUtils.replace(path, Strings.WINDOWS_FOLDER_SEPARATOR, Strings.FOLDER_SEPARATOR);

		if(pathToUse.indexOf(Chars.DOT) == -1){
			return pathToUse;
		}

		// 以 ":" 获取前缀
		// "file:core/../core/io/Resource.class"
		int prefixIndex = pathToUse.indexOf(Chars.COLON);
		String prefix = "";
		if(prefixIndex != -1 ){
			prefix = pathToUse.substring(0,prefixIndex + 1);
			if(prefix.contains(Strings.FOLDER_SEPARATOR)){
				prefix = "";
			} else {
				pathToUse = pathToUse.substring(prefixIndex + 1);
			}
		}

		if(pathToUse.startsWith(Strings.FOLDER_SEPARATOR)){
			prefix = prefix + Strings.FOLDER_SEPARATOR;
			pathToUse = pathToUse.substring(1);
		}
		String[] pathArray = StringUtils.delimitedListToStringArray(pathToUse, Strings.FOLDER_SEPARATOR);
		Deque<String> pathElements = new ArrayDeque<>();
		int tops = 0;

		for (int i = pathArray.length - 1; i >= 0; i--) {
			String element = pathArray[i];
			if (Strings.CURRENT_PATH.equals(element)) {
				// Points to current directory - drop it.
			}
			else if (Strings.TOP_PATH.equals(element)) {
				// Registering top path found.
				tops++;
			}
			else {
				if (tops > 0) {
					// Merging path element with element corresponding to top path.
					tops--;
				}
				else {
					// Normal path element found.
					pathElements.addFirst(element);
				}
			}
		}

		// All path elements stayed the same - shortcut
		if (pathArray.length == pathElements.size()) {
			return prefix + pathToUse;
		}
		// Remaining top paths need to be retained.
		for (int i = 0; i < tops; i++) {
			pathElements.addFirst(Strings.TOP_PATH);
		}
		// If nothing else left, at least explicitly point to current path.
		if (pathElements.size() == 1 && pathElements.getLast().isEmpty() && !prefix.endsWith(Strings.FOLDER_SEPARATOR)) {
			pathElements.addFirst(Strings.CURRENT_PATH);
		}

		return prefix + StringUtils.collectionToDelimitedString(pathElements, Strings.FOLDER_SEPARATOR);
	}

	/**
	 * 通过格式化然后比较两个路径是否相同
	 * @param path1 第一个路径
	 * @param path2 第二个路径
	 * @return {@code true} 规范后的两条路径相等
	 */
	public static boolean pathEquals(String path1,String path2){
		return formatPath(path1).equals(formatPath(path2));
	}



}
