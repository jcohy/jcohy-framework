package com.jcohy.framework.utils;

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


	// ==================================== Blank And Empty ====================================

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
	 */
	public static boolean isNotBlank(final CharSequence cs) {
		return StringUtils.hasText(cs);
	}

	/**
	 * 有任意一个为 blank.
	 * @param css 字符序列
	 * @return {@code true} 有一个 blank.
	 * @see StringUtils#isBlank
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
	 */
	public static boolean isNotEmpty(final CharSequence cs) {
		return 	StringUtils.hasLength(cs);
	}

	/**
	 * 有任意一个为空
	 * @param css 字符串数组
	 * @return @return {@code true} 有一个为空.
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
	 */
	public static boolean isAllEmpty(final CharSequence... css) {
		if (ObjectUtils.isEmpty(css)) {
			return false;
		}
		return Stream.of(css).allMatch(StringUtils::isEmpty);
	}

	// ====================================  判断  ====================================

	/**
	 * 判断一个字符串是否是数字, 前后不能有空白符.
	 * @param cs 给定字符串
	 * @return {@code true} 是数字
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

	// ====================================   格式化   ====================================

	/**
	 * 同 log 格式的 format 规则.
	 * <p>
	 * use: format("my name is {}, and i like {}!", "jiac", "Java").
	 * @param message 需要转换的字符串.
	 * @param args 需要替换的变量.
	 * @return 转换后的字符串.
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

	// ====================================  ====================================

	// ====================================  ====================================

	// ====================================  ====================================

	// ====================================  ====================================

	// ====================================  ====================================

	// ====================================  ====================================
}
