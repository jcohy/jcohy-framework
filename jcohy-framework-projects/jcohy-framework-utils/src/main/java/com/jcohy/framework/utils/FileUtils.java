package com.jcohy.framework.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import com.jcohy.framework.utils.constant.StringPools;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:14:30
 * @since 2022.0.1
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    /**
     * FILENAME_PATTERN.
     */
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * 获取文件后缀名.
     * @param path 文件全名
     * @return {@code String}
     * @since 2022.0.1
     */
    public static String getFileExtension(String path) {
        return StringUtils.getFilenameExtension(path);
    }

    /**
     * 从给定的 Java 资源路径中提取文件名. 例如. {@code "mypath/myfile.txt" -&gt; "myfile.txt"}.
     * @param path 文件
     * @return {@code string}
     * @since 2022.0.1
     */
    public static String getFileName(String path) {
        return StringUtils.getFilename(path);
    }

    /**
     * 读取文件的每一行，转为集合.
     * @param file file
     * @return 文件每一行集合
     * @throws IOException exception
     * @since 2022.0.1
     */
    public static List<String> readLines(File file) throws IOException {
        return FileUtils.readLines(file, StandardCharsets.UTF_8);
    }

    /**
     * 读取文件的每一行,并进行排序，转为集合.
     * @param file file
     * @return 文件每一行集合
     * @throws IOException exception
     * @since 2022.0.1
     */
    public static List<String> readLinesAndSort(File file) throws IOException {
        return readLines(file, StandardCharsets.UTF_8).stream().sorted().collect(Collectors.toList());
    }

    /**
     * 输出文件.
     * @param file file
     * @param consumer consumer
     * @since 2022.0.1
     */
    public static void writeWithPrintWriter(File file, Consumer<PrintWriter> consumer) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            consumer.accept(writer);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 递归遍历所有文件.
     * @param directory 文件夹
     * @return 返回所有文件
     * @since 2022.0.1
     */
    public static Collection<File> listAllFiles(String directory) {
        return FileUtils.listFiles(new File(directory), null, true);
    }

    /**
     * 获取网络文件名.
     * @param fileUrl url
     * @return 文件名
     * @since 2022.0.1
     */
    public static String getNetFileName(String fileUrl) {
        int dotIndex = fileUrl.lastIndexOf(StringPools.SLASH);
        return (dotIndex == -1) ? fileUrl : fileUrl.substring(dotIndex + 1);
    }

    /**
     * 获取网络文件，下载到临时目录.
     * @param url 文件网络地址
     * @return 返回保存后的地址
     * @since 2022.0.1
     */
    public static String toNetFile(String url) {
        String temp = System.getProperty("java.io.tmpdir");
        return toNetFile(url, temp.substring(0, temp.length() - 1));
    }

    /**
     * 获取网络文件.
     * @param url 文件网络地址
     * @param saveUrl 保存地址
     * @return 返回保存后的地址
     * @since 2022.0.1
     */
    public static String toNetFile(String url, String saveUrl) {
        String fileName = getNetFileName(url);
        try {
            Path path = Paths.get(saveUrl);
            if (!Files.isDirectory(path)) {
                throw new IllegalArgumentException("saveUrl must be a directory");
            }
            InputStream inputStream = new URL(url).openStream();
            Path target = Paths.get(saveUrl + File.separator + fileName);
            Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
            return saveUrl + File.separator + fileName;
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 文件名称验证.
     * @param filename 文件名称
     * @return true 正常 false 非法
     * @since 2022.0.1
     */
    public static boolean isValidFilename(String filename) {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * 下载文件名重新编码.
     * @param response 响应对象
     * @param realFileName 真实文件名
     * @throws UnsupportedEncodingException /
     * @since 2022.0.1
     */
    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName)
            throws UnsupportedEncodingException {
        String percentEncodedFileName = percentEncode(realFileName);

        StringBuilder contentDispositionValue = new StringBuilder();
        contentDispositionValue.append("attachment; filename=").append(percentEncodedFileName).append(";")
                .append("filename*=").append("utf-8''").append(percentEncodedFileName);

        response.setHeader("Content-disposition", contentDispositionValue.toString());
    }

    /**
     * 百分号编码工具方法.
     * @param s 需要百分号编码的字符串
     * @return 百分号编码后的字符串
     * @throws UnsupportedEncodingException /
     * @since 2022.0.1
     */
    public static String percentEncode(String s) throws UnsupportedEncodingException {
        String encode = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        return encode.replaceAll("\\+", "%20");
    }

}
