package com.jcohy.framework.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.springframework.util.ObjectUtils;

/**
 * 描述: 继承 {@link org.springframework.util.StringUtils}.
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2/18/22:15:35
 * @since 2022.0.1
 */
public class StringUtils extends org.springframework.util.StringUtils {

	/**
	 * INDEX_NOT_FOUND.
	 */
	public static final int INDEX_NOT_FOUND = -1;

	// ----------------------------------------------------------------------------------------------
	// Blank 和 Empty

	/**
	 * 检查给定字符串是否包含文本字符.
	 * 如果为 {@code true}, 需要满足以下条件之一即可
	 * <ol>
	 *     <li>为 null</li>
	 *     <li>为 "" </li>
	 *     <li>全部由空白符组成</li>
	 * </ol>
	 * <pre class="code">
	 * StringUtil.isBlank(null) = true
	 * StringUtil.isBlank("") = true
	 * StringUtil.isBlank(" ") = true
	 * StringUtil.isBlank("12345") = false
	 * StringUtil.isBlank(" 12345 ") = false
	 * </pre>
	 * @param cs 给定字符
	 * @return {@code true} 如果 {@code CharSequence} 为 {@code null}, 为 "", 或全部由空白符组成
	 * @see StringUtils#hasText
	 * @see Character#isWhitespace
	 * @since 2022.0.1
	 */
	public static boolean isBlank(final CharSequence cs) {
		return !StringUtils.hasText(cs);
	}

	/**
	 * 检查给定字符串是否不为 "" , null , 只包含空白符.
	 *
	 * <pre class="code">
	 * StringUtil.isNotBlank(null)	  = false
	 * StringUtil.isNotBlank("")		= false
	 * StringUtil.isNotBlank(" ")	   = false
	 * StringUtil.isNotBlank("bob")	 = true
	 * StringUtil.isNotBlank("  bob  ") = true
	 * </pre>
	 * @param cs 给定字符
	 * @return {@code true} 如果字符串不为 ""， 不为 null, 并且包含非空白符
	 * @see StringUtils#hasText
	 * @see StringUtils#isBlank
	 * @see Character#isWhitespace
	 * @since 2022.0.1
	 */
	public static boolean isNotBlank(final CharSequence cs) {
		return StringUtils.hasText(cs);
	}

	/**
	 * 有任意一个为 blank.
	 * @param css 字符序列
	 * @return {@code true} 有一个 blank.
	 * @see StringUtils#isBlank
	 * @since 2022.0.1
	 */
	public static boolean isAnyBlank(final CharSequence... css) {
		if (ObjectUtils.isEmpty(css)) {
			return true;
		}
		return Stream.of(css).anyMatch(StringUtils::isBlank);
	}

	/**
	 * 是否全部不为 Blank.
	 * @param css 字符序列
	 * @return {@code true} 全部不为 blank.
	 * @see StringUtils#isNotBlank
	 * @since 2022.0.1
	 */
	public static boolean isNoneBlank(final CharSequence... css) {
		if (ObjectUtils.isEmpty(css)) {
			return false;
		}
		return Stream.of(css).allMatch(StringUtils::isNotBlank);
	}

	/**
	 * 是否全为 Blank.
	 * @param css 字符序列
	 * @return {@code true} 全为 blank.
	 * @see StringUtils#isBlank
	 * @since 2022.0.1
	 */
	public static boolean isAllBlank(final CharSequence... css) {
		return Stream.of(css).allMatch(StringUtils::isBlank);
	}

	/**
	 * 判断字符串是否为空，符合以下条件一种即可：
	 * <ol>
	 *     <li> 为 null</li>
	 *     <li> 长度小于 0 </li>
	 * </ol>
	 * <pre class="code">
	 * StringUtil.isEmpty(null) = true
	 * StringUtil.isEmpty("") = true
	 * StringUtil.isEmpty(" ") = false
	 * StringUtil.isEmpty("12345") = false
	 * StringUtil.isEmpty(" 12345 ") = false
	 * </pre>
	 * @param cs 给定字符
	 * @return {@code true} 如果 {@code CharSequence} 为 {@code null}, 或 ""
	 * @see StringUtils#hasLength
	 * @since 2022.0.1
	 */
	public static boolean isEmpty(final CharSequence cs) {
		return 	!StringUtils.hasLength(cs);
	}

	/**
	 * 判断给定字符串是否为空
	 *
	 * <pre class="code">
	 * StringUtil.isNotEmpty(null) = false
	 * StringUtil.isNotEmpty("") = false
	 * StringUtil.isNotEmpty(" ") = true
	 * StringUtil.isNotEmpty("12345") = true
	 * StringUtil.isNotEmpty(" 12345 ") = true
	 * </pre>
	 * @param cs 给定字符
	 * @return {@code true} 如果 {@code CharSequence} 为 {@code null}, 或 ""
	 * @see StringUtils#hasLength
	 * @since 2022.0.1
	 */
	public static boolean isNotEmpty(final CharSequence cs) {
		return 	StringUtils.hasLength(cs);
	}

	/**
	 * 有任意一个为空
	 * @param css 字符串数组
	 * @return @return {@code true} 有一个为空.
	 * @since 2022.0.1
	 */
	public static boolean isAnyEmpty(final CharSequence... css) {
		if (ObjectUtils.isEmpty(css)) {
			return true;
		}
		return 	Stream.of(css).anyMatch(StringUtils::isEmpty);
	}

	/**
	 * 全部不为空
	 * @param css 给定字符串
	 * @return {@code true} 全部不为空.
	 * @since 2022.0.1
	 */
	public static boolean isNoneEmpty(final CharSequence... css) {
		if (ObjectUtils.isEmpty(css)) {
			return false;
		}
		return Stream.of(css).allMatch(StringUtils::isNotEmpty);
	}

	/**
	 * 全部为空
	 * @param css 给定字符串
	 * @return {@code true} 全部为空.
	 * @since 2022.0.1
	 */
	public static boolean isAllEmpty(final CharSequence... css) {
		if (ObjectUtils.isEmpty(css)) {
			return false;
		}
		return Stream.of(css).allMatch(StringUtils::isEmpty);
	}

	// ----------------------------------------------------------------------------------------------
	// 判断

	/**
	 * 判断一个字符串是否是数字, 前后不能有空白符.
	 * @param cs 给定字符串
	 * @return {@code true} 是数字
	 * @since 2022.0.1
	 */
	public static boolean isNumeric(final CharSequence cs) {
		if (isBlank(cs)) {
			return false;
		}
		for (int i = cs.length(); --i >= 0;) {
			int chr = cs.charAt(i);
			if (chr < 48 || chr > 57) {
				return false;
			}
		}
		return true;
	}

	// ----------------------------------------------------------------------------------------------
	// 格式化

	/**
	 * 同 log 格式的 format 规则.
	 * <p>
	 * use: format("my name is {}, and i like {}!", "jiac", "Java").
	 * @param message 需要转换的字符串.
	 * @param args 需要替换的变量.
	 * @return 转换后的字符串.
	 * @since 2022.0.1
	 */
	public static String format(@Nullable String message,@Nullable Object... args) {
		// message 为 null 返回空字符串
		if (message == null) {
			return StringPools.EMPTY;
		}
		// 参数为 null 或者为空
		if (args == null || args.length == 0) {
			return message;
		}

		StringBuilder sb = new StringBuilder((int) (message.length() * 1.5));
		int cursor = 0;
		int index = 0;
		int argsLength = args.length;
		for (int start, end; (start = message.indexOf('{', cursor)) != -1 && (end = message.indexOf('}', start)) != -1
				&& index < argsLength;) {
			sb.append(message, cursor, start);
			sb.append(args[index]);
			cursor = end + 1;
			index++;
		}
		sb.append(message.substring(cursor));
		return sb.toString();
	}

	/**
	 * 同 log 格式的 format 规则
	 * <p>
	 * use: format("my name is %d, and i like %d!", "jiac", "Java").
	 * @param message 需要转换的字符串
	 * @param arguments 需要替换的变量
	 * @return 转换后的字符串
	 * @since 2022.0.1
	 */
	public static String formatStr(@org.springframework.lang.Nullable String message, @org.springframework.lang.Nullable Object... arguments) {
		// message 为 null 返回空字符串
		if (message == null) {
			return StringPools.EMPTY;
		}
		// 参数为 null 或者为空
		if (arguments == null || arguments.length == 0) {
			return message;
		}
		return String.format(message, arguments);
	}

	/**
	 * 格式化执行时间，单位为 ms 和 s，保留三位小数.
	 * @param nanos 纳秒
	 * @return 格式化后的时间
	 * @since 2022.0.1
	 */
	public static String formatTime(long nanos) {
		if (nanos < 1) {
			return "0ms";
		}
		double millis = (double) nanos / (1000 * 1000);
		// 不够 1 ms，最小单位为 ms
		if (millis > 1000) {
			return String.format("%.3fs", millis / 1000);
		}
		else {
			return String.format("%.3fms", millis);
		}
	}

	// =========================================== split ============================================
	// ----------------------------------------------------------------------------------------------
	// Split by char 通过 char 类型拆分

	/**
	 * 切分字符串路径，仅支持 Unix 分界符：/.
	 * @param str 被切分的字符串
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static List<String> splitPath(String str) {
		return splitPath(str, 0);
	}

	/**
	 * 切分字符串路径，仅支持unix分界符：/.
	 * @param str 被切分的字符串
	 * @param limit 限制分片数
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static List<String> splitPath(String str, int limit) {
		return split(str, StringPools.SLASH, limit, true, true);
	}

	/**
	 * 切分字符串路径，仅支持 Unix 分界符：/.
	 * @param str 被切分的字符串
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static String[] splitPathToArray(String str) {
		return toArray(splitPath(str));
	}

	/**
	 * 切分字符串路径，仅支持unix分界符：/.
	 * @param str 被切分的字符串
	 * @param limit 限制分片数
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static String[] splitPathToArray(String str, int limit) {
		return toArray(splitPath(str, limit));
	}

	/**
	 * 切分字符串.
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @param ignoreEmpty 是否忽略空串
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static List<String> splitTrim(String str, char separator, boolean ignoreEmpty) {
		return split(str, separator, 0, true, ignoreEmpty);
	}

	/**
	 * 切分字符串，大小写敏感，去除每个元素两边空白符.
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @param limit 限制分片数，-1不限制
	 * @param ignoreEmpty 是否忽略空串
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static List<String> splitTrim(String str, char separator, int limit, boolean ignoreEmpty) {
		return split(str, separator, limit, true, ignoreEmpty, false);
	}

	/**
	 * 切分字符串，不限制分片数量,默认以逗号拆分,并且去除空格和空元素.
	 * @param str 被切分的字符串
	 * @return 切分后的集合
	 * @since 2021.0.1
	 */
	public static List<String> split(String str) {
		return split(str, CharPools.COMMA);
	}

	/**
	 * 切分字符串，不限制分片数量.
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @return 切分后的集合
	 * @since 2021.0.1
	 */
	public static List<String> split(String str, char separator) {
		return split(str, separator, 0, true, true);
	}

	/**
	 * 切分字符串，不限制分片数量.
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @param isTrim 是否去除切分字符串后每个元素两边的空格
	 * @param ignoreEmpty 是否忽略空串，长度为 0 的字符串。"   " 不是空串
	 * @return 切分后的集合
	 * @since 2021.0.1
	 */
	public static List<String> split(String str, char separator, boolean isTrim, boolean ignoreEmpty) {
		return split(str, separator, 0, isTrim, ignoreEmpty);
	}

	/**
	 * 切分字符串.
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @param limit 限制分片数，-1不限制
	 * @param isTrim 是否去除切分字符串后每个元素两边的空格
	 * @param ignoreEmpty 是否忽略空串
	 * @return 切分后的集合
	 * @since 2021.0.1
	 */
	public static List<String> split(String str, char separator, int limit, boolean isTrim, boolean ignoreEmpty) {
		if (null == str) {
			return new ArrayList<>(0);
		}
		return split(str, separator, limit, isTrim, ignoreEmpty, false);
	}


	/**
	 * 切分字符串，忽略大小写.
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @param limit 限制分片数，-1 不限制
	 * @param isTrim 是否去除切分字符串后每个元素两边的空格
	 * @param ignoreEmpty 是否忽略空串
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static List<String> splitIgnoreCase(String str, char separator, int limit, boolean isTrim,
			boolean ignoreEmpty) {
		return split(str, separator, limit, isTrim, ignoreEmpty, true);
	}

	/**
	 * 切分字符串为字符串数组.
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @param limit 限制分片数
	 * @param isTrim 是否去除切分字符串后每个元素两边的空格
	 * @param ignoreEmpty 是否忽略空串
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static String[] splitToArray(String str, char separator, int limit, boolean isTrim, boolean ignoreEmpty) {
		return toArray(split(str, separator, limit, isTrim, ignoreEmpty));
	}

	/**
	 * 切分字符串.
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @param limit 限制分片数，-1不限制
	 * @param isTrim 是否去除切分字符串后每个元素两边的空格
	 * @param ignoreEmpty 是否忽略空串
	 * @param ignoreCase 是否忽略大小写
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static List<String> split(String str, char separator, int limit, boolean isTrim, boolean ignoreEmpty,
			boolean ignoreCase) {
		if (!StringUtils.hasLength(str)) {
			return new ArrayList<String>(0);
		}
		if (limit == 1) {
			return addToList(new ArrayList<String>(1), str, isTrim, ignoreEmpty);
		}

		final ArrayList<String> list = new ArrayList<>((limit > 0) ? limit : 16);
		int len = str.length();
		int start = 0;
		for (int i = 0; i < len; i++) {
			if (Objects.equals(separator, str.charAt(i))) {
				addToList(list, str.substring(start, i), isTrim, ignoreEmpty);
				start = i + 1;

				// 检查是否超出范围（最大允许limit-1个，剩下一个留给末尾字符串）
				if (limit > 0 && list.size() > limit - 2) {
					break;
				}
			}
		}
		return addToList(list, str.substring(start, len), isTrim, ignoreEmpty);
	}

	// ----------------------------------------------------------------------------------------------
	// Split by String 通过 String 类型拆分

	/**
	 * 切分字符串，不忽略大小写.
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符串
	 * @param isTrim 是否去除切分字符串后每个元素两边的空格
	 * @param ignoreEmpty 是否忽略空串
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static List<String> split(String str, String separator, boolean isTrim, boolean ignoreEmpty) {
		return split(str, separator, -1, isTrim, ignoreEmpty, false);
	}

	/**
	 * 切分字符串，去除每个元素两边空格，忽略大小写.
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符串
	 * @param ignoreEmpty 是否忽略空串
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static List<String> splitTrim(String str, String separator, boolean ignoreEmpty) {
		return split(str, separator, true, ignoreEmpty);
	}

	/**
	 * 切分字符串，不忽略大小写.
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符串
	 * @param limit 限制分片数
	 * @param isTrim 是否去除切分字符串后每个元素两边的空格
	 * @param ignoreEmpty 是否忽略空串
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static List<String> split(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty) {
		if (null == str) {
			return new ArrayList<>(0);
		}
		return split(str, separator, limit, isTrim, ignoreEmpty, false);
	}

	/**
	 * 切分字符串，去除每个元素两边空格，忽略大小写.
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符串
	 * @param limit 限制分片数
	 * @param ignoreEmpty 是否忽略空串
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static List<String> splitTrim(String str, String separator, int limit, boolean ignoreEmpty) {
		return split(str, separator, limit, true, ignoreEmpty);
	}

	/**
	 * 切分字符串，忽略大小写.
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符串
	 * @param limit 限制分片数
	 * @param isTrim 是否去除切分字符串后每个元素两边的空格
	 * @param ignoreEmpty 是否忽略空串
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static List<String> splitIgnoreCase(String str, String separator, int limit, boolean isTrim,
			boolean ignoreEmpty) {
		return split(str, separator, limit, isTrim, ignoreEmpty, true);
	}

	/**
	 * 切分字符串，去除每个元素两边空格，忽略大小写.
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符串
	 * @param limit 限制分片数
	 * @param ignoreEmpty 是否忽略空串
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static List<String> splitTrimIgnoreCase(String str, String separator, int limit, boolean ignoreEmpty) {
		return split(str, separator, limit, true, ignoreEmpty, true);
	}

	/**
	 * 切分字符串.
	 * @param str 被切分的字符串
	 * @param separator 分隔符
	 * @return 字符串
	 */
	public static String[] split(String str, String separator) {
		if (str == null) {
			return new String[] {};
		}

		final String separatorStr = (separator != null) ? separator.toString() : null;
		return splitToArray(str.toString(), separatorStr, 0, false, false);
	}

	/**
	 * 切分字符串.
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符串
	 * @param limit 限制分片数
	 * @param isTrim 是否去除切分字符串后每个元素两边的空格
	 * @param ignoreEmpty 是否忽略空串
	 * @param ignoreCase 是否忽略大小写
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static List<String> split(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty,
			boolean ignoreCase) {
		if (!StringUtils.hasLength(str)) {
			return new ArrayList<String>(0);
		}
		if (limit == 1) {
			return addToList(new ArrayList<String>(1), str, isTrim, ignoreEmpty);
		}

		if (!StringUtils.hasLength(separator)) {
			return split(str, limit);
		}
		else if (separator.length() == 1) {
			return split(str, separator.charAt(0), limit, isTrim, ignoreEmpty, ignoreCase);
		}

		final ArrayList<String> list = new ArrayList<>();
		int len = str.length();
		int separatorLen = separator.length();
		int start = 0;
		int i = 0;
		while (i < len) {
			i = StringUtils.indexOf(str, separator, start, ignoreCase);
			if (i > -1) {
				addToList(list, str.substring(start, i), isTrim, ignoreEmpty);
				start = i + separatorLen;

				// 检查是否超出范围（最大允许limit-1个，剩下一个留给末尾字符串）
				if (limit > 0 && list.size() > limit - 2) {
					break;
				}
			}
			else {
				break;
			}
		}
		return addToList(list, str.substring(start, len), isTrim, ignoreEmpty);
	}

	/**
	 * 切分字符串为字符串数组.
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @param limit 限制分片数
	 * @param isTrim 是否去除切分字符串后每个元素两边的空格
	 * @param ignoreEmpty 是否忽略空串
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static String[] splitToArray(String str, String separator, int limit, boolean isTrim, boolean ignoreEmpty) {
		return toArray(split(str, separator, limit, isTrim, ignoreEmpty));
	}

	// ----------------------------------------------------------------------------------------------
	// Split by Whitespace 通过空白符类型拆分

	/**
	 * 使用空白符切分字符串<br>
	 * 切分后的字符串两边不包含空白符，空串或空白符串并不做为元素之一.
	 * @param str 被切分的字符串
	 * @param limit 限制分片数
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static List<String> split(String str, int limit) {
		if (!StringUtils.hasLength(str)) {
			return new ArrayList<String>(0);
		}
		if (limit == 1) {
			return addToList(new ArrayList<String>(1), str, true, true);
		}

		final ArrayList<String> list = new ArrayList<>();
		int len = str.length();
		int start = 0;
		for (int i = 0; i < len; i++) {
			if (ObjectUtils.isEmpty(str.charAt(i))) {
				addToList(list, str.substring(start, i), true, true);
				start = i + 1;
				if (limit > 0 && list.size() > limit - 2) {
					break;
				}
			}
		}
		return addToList(list, str.substring(start, len), true, true);
	}

	/**
	 * 切分字符串为字符串数组.
	 * @param str 被切分的字符串
	 * @param limit 限制分片数
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static String[] splitToArray(String str, int limit) {
		if (null == str) {
			return new String[] {};
		}
		return toArray(split(str, limit));
	}

	// ----------------------------------------------------------------------------------------------
	// Split by regex 通过正则拆分

	/**
	 * 通过正则切分字符串.
	 * @param str 字符串
	 * @param separatorPattern 分隔符正则{@link Pattern}
	 * @param limit 限制分片数
	 * @param isTrim 是否去除切分字符串后每个元素两边的空格
	 * @param ignoreEmpty 是否忽略空串
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static List<String> split(String str, Pattern separatorPattern, int limit, boolean isTrim,
			boolean ignoreEmpty) {
		if (!StringUtils.hasLength(str)) {
			return new ArrayList<>(0);
		}
		if (limit == 1) {
			return addToList(new ArrayList<String>(1), str, isTrim, ignoreEmpty);
		}

		if (null == separatorPattern) {
			return split(str, limit);
		}

		final Matcher matcher = separatorPattern.matcher(str);
		final ArrayList<String> list = new ArrayList<>();
		int len = str.length();
		int start = 0;
		while (matcher.find()) {
			addToList(list, str.substring(start, matcher.start()), isTrim, ignoreEmpty);
			start = matcher.end();

			if (limit > 0 && list.size() > limit - 2) {
				break;
			}
		}
		return addToList(list, str.substring(start, len), isTrim, ignoreEmpty);
	}

	/**
	 * 通过正则切分字符串为字符串数组.
	 * @param str 被切分的字符串
	 * @param separatorPattern 分隔符正则{@link Pattern}
	 * @param limit 限制分片数
	 * @param isTrim 是否去除切分字符串后每个元素两边的空格
	 * @param ignoreEmpty 是否忽略空串
	 * @return 切分后的集合
	 * @since 2022.0.1
	 */
	public static String[] splitToArray(String str, Pattern separatorPattern, int limit, boolean isTrim,
			boolean ignoreEmpty) {
		return toArray(split(str, separatorPattern, limit, isTrim, ignoreEmpty));
	}

	// ----------------------------------------------------------------------------------------------
	// Split by length 通过长度拆分

	/**
	 * 根据给定长度，将给定字符串截取为多个部分.
	 * @param str 字符串
	 * @param len 每一个小节的长度
	 * @return 截取后的字符串数组
	 */
	public static String[] splitByLength(String str, int len) {
		int partCount = str.length() / len;
		int lastPartCount = str.length() % len;
		int fixPart = 0;
		if (lastPartCount != 0) {
			fixPart = 1;
		}

		final String[] strs = new String[partCount + fixPart];
		for (int i = 0; i < partCount + fixPart; i++) {
			if (i == partCount + fixPart - 1 && lastPartCount != 0) {
				strs[i] = str.substring(i * len, i * len + lastPartCount);
			}
			else {
				strs[i] = str.substring(i * len, i * len + len);
			}
		}
		return strs;
	}

	// ==================================== IndexOf ====================================

	/**
	 * 指定范围内反向查找字符串.
	 * @param str 字符串
	 * @param searchStr 需要查找位置的字符串
	 * @param fromIndex 起始位置
	 * @param ignoreCase 是否忽略大小写
	 * @return 位置
	 * @since 2022.0.1
	 */
	public static int indexOf(final CharSequence str, CharSequence searchStr, int fromIndex, boolean ignoreCase) {
		if (str == null || searchStr == null) {
			return INDEX_NOT_FOUND;
		}
		if (fromIndex < 0) {
			fromIndex = 0;
		}

		final int endLimit = str.length() - searchStr.length() + 1;
		if (fromIndex > endLimit) {
			return INDEX_NOT_FOUND;
		}
		if (searchStr.length() == 0) {
			return fromIndex;
		}

		if (!ignoreCase) {
			// 不忽略大小写调用JDK方法
			return str.toString().indexOf(searchStr.toString(), fromIndex);
		}

		for (int i = fromIndex; i < endLimit; i++) {
			if (isSubEquals(str, i, searchStr, 0, searchStr.length(), true)) {
				return i;
			}
		}
		return INDEX_NOT_FOUND;
	}

	// ==================================== Equals ====================================

	/**
	 * 截取两个字符串的不同部分（长度一致），判断截取的子串是否相同<br>
	 * 任意一个字符串为null返回false.
	 * @param str1 第一个字符串
	 * @param start1 第一个字符串开始的位置
	 * @param str2 第二个字符串
	 * @param start2 第二个字符串开始的位置
	 * @param length 截取长度
	 * @param ignoreCase 是否忽略大小写
	 * @return 子串是否相同
	 * @since 2022.0.1
	 */
	public static boolean isSubEquals(CharSequence str1, int start1, CharSequence str2, int start2, int length,
			boolean ignoreCase) {
		if (null == str1 || null == str2) {
			return false;
		}

		return str1.toString().regionMatches(ignoreCase, start1, str2.toString(), start2, length);
	}

	// ====================================  ====================================

	// ====================================  ====================================

	// ====================================  Private Other ====================================

	/**
	 * 将字符串加入 list 中.
	 * @param list 列表
	 * @param part 被加入的部分
	 * @param isTrim 是否去除两端空白符
	 * @param ignoreEmpty 是否略过空字符串（空字符串不做为一个元素）
	 * @return 列表
	 */
	private static List<String> addToList(List<String> list, String part, boolean isTrim, boolean ignoreEmpty) {
		if (isTrim) {
			part = part.trim();
		}
		if (!ignoreEmpty || !part.isEmpty()) {
			list.add(part);
		}
		return list;
	}

	/**
	 * List转array.
	 * @param list list
	 * @return array
	 */
	private static String[] toArray(List<String> list) {
		return list.toArray(new String[list.size()]);
	}
}
