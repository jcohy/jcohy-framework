package com.jcohy.framework.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
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

    /**
     * 最大填充长度.
     */
    private static final int PAD_LIMIT = 8192;

    // ----------------------------------------------------------------------------------------------
    // Blank 和 Empty

    /**
     * 检查给定字符串是否包含文本字符. 如果为 {@code true}, 需要满足以下条件之一即可
     * <ol>
     * <li>为 null</li>
     * <li>为 ""</li>
     * <li>全部由空白符组成</li>
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
     * @since 2022.0.1
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
     * @since 2022.0.1
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
     * @since 2022.0.1
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
     * @since 2022.0.1
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
     * @since 2022.0.1
     * @see StringUtils#isBlank
     */
    public static boolean isAllBlank(final CharSequence... css) {
        return Stream.of(css).allMatch(StringUtils::isBlank);
    }

    /**
     * 判断字符串是否为空，符合以下条件一种即可.
     * <ol>
     * <li>为 null</li>
     * <li>长度小于 0</li>
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
     * @since 2022.0.1
     * @see StringUtils#hasLength
     */
    public static boolean isEmpty(final CharSequence cs) {
        return !StringUtils.hasLength(cs);
    }

    /**
     * 判断给定字符串是否为空.
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
     * @since 2022.0.1
     * @see StringUtils#hasLength
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return StringUtils.hasLength(cs);
    }

    /**
     * 有任意一个为空.
     * @param css 字符串数组
     * @return {@code true} 有一个为空.
     * @since 2022.0.1
     */
    public static boolean isAnyEmpty(final CharSequence... css) {
        if (ObjectUtils.isEmpty(css)) {
            return true;
        }
        return Stream.of(css).anyMatch(StringUtils::isEmpty);
    }

    /**
     * 全部不为空.
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
     * 全部为空.
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
    public static String format(@Nullable String message, @Nullable Object... args) {
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
    public static String formatStr(@org.springframework.lang.Nullable String message,
            @org.springframework.lang.Nullable Object... arguments) {
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
     * @param ignoreEmpty 是否忽略空串，长度为 0 的字符串。" " 不是空串
     * @return 切分后的集合
     * @since 2021.0.1
     */
    public static List<String> split(String str, char separator, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, 0, isTrim, ignoreEmpty);
    }

    /**
     * 切分字符串，默认不忽略大小写.
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

    // /**
    // * 切分字符串，忽略大小写.
    // * @param str 被切分的字符串
    // * @param separator 分隔符字符
    // * @param limit 限制分片数，-1 不限制
    // * @param isTrim 是否去除切分字符串后每个元素两边的空格
    // * @param ignoreEmpty 是否忽略空串
    // * @return 切分后的集合
    // * @since 2022.0.1
    // */
    // public static List<String> splitIgnoreCase(String str, char separator, int limit,
    // boolean isTrim,
    // boolean ignoreEmpty) {
    // return split(str, separator, limit, isTrim, ignoreEmpty, true);
    // }

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

    // /**
    // * 切分字符串，忽略大小写.
    // * @param str 被切分的字符串
    // * @param separator 分隔符字符串
    // * @param limit 限制分片数
    // * @param isTrim 是否去除切分字符串后每个元素两边的空格
    // * @param ignoreEmpty 是否忽略空串
    // * @return 切分后的集合
    // * @since 2022.0.1
    // */
    // public static List<String> splitIgnoreCase(String str, String separator, int limit,
    // boolean isTrim,
    // boolean ignoreEmpty) {
    // return split(str, separator, limit, isTrim, ignoreEmpty, true);
    // }

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
        return splitToArray(str, separatorStr, 0, false, false);
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

    // ----------------------------------------------------------------------------------------------
    // IndexOf

    /**
     * 指定范围内查找指定字符.
     * @param str 字符串
     * @param searchChar 被查找的字符
     * @return 位置
     * @since 2022.0.1
     */
    public static int indexOf(final CharSequence str, char searchChar) {
        return indexOf(str, searchChar, 0);
    }

    /**
     * 指定范围内查找指定字符.
     * @param str 字符串
     * @param searchChar 被查找的字符
     * @param start 起始位置，如果小于 0，从 0 开始查找
     * @return 位置
     * @since 2022.0.1
     */
    public static int indexOf(final CharSequence str, char searchChar, int start) {
        if (str instanceof String) {
            return ((String) str).indexOf(searchChar, start);
        }
        else {
            return indexOf(str, searchChar, start, -1);
        }
    }

    /**
     * 指定范围内查找指定字符.
     * @param str 字符串
     * @param searchChar 被查找的字符
     * @param start 起始位置，如果小于 0，从 0 开始查找
     * @param end 终止位置，如果超过 str.length() 则默认查找到字符串末尾
     * @return 位置
     * @since 2022.0.1
     */
    public static int indexOf(final CharSequence str, char searchChar, int start, int end) {
        final int len = str.length();
        if (start < 0 || start > len) {
            start = 0;
        }
        if (end > len || end < 0) {
            end = len;
        }
        for (int i = start; i < end; i++) {
            if (str.charAt(i) == searchChar) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 指定范围内查找字符串.
     *
     * <pre>
     * StringUtil.indexOfIgnoreCase(null, *, *)          = -1
     * StringUtil.indexOfIgnoreCase(*, null, *)          = -1
     * StringUtil.indexOfIgnoreCase("", "", 0)           = 0
     * StringUtil.indexOfIgnoreCase("aabaabaa", "A", 0)  = 0
     * StringUtil.indexOfIgnoreCase("aabaabaa", "B", 0)  = 2
     * StringUtil.indexOfIgnoreCase("aabaabaa", "AB", 0) = 1
     * StringUtil.indexOfIgnoreCase("aabaabaa", "B", 3)  = 5
     * StringUtil.indexOfIgnoreCase("aabaabaa", "B", 9)  = -1
     * StringUtil.indexOfIgnoreCase("aabaabaa", "B", -1) = 2
     * StringUtil.indexOfIgnoreCase("aabaabaa", "", 2)   = 2
     * StringUtil.indexOfIgnoreCase("abc", "", 9)        = -1
     * </pre>.
     * @param str 字符串
     * @param searchStr 需要查找位置的字符串
     * @param fromIndex 起始位置
     * @return 位置
     * @since 2022.0.1
     */
    public static int indexOfIgnoreCase(final CharSequence str, final CharSequence searchStr, int fromIndex) {
        return indexOf(str, searchStr, fromIndex, true);
    }

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

    // ----------------------------------------------------------------------------------------------
    // Equals

    /**
     * 比较两个字符串（大小写敏感）.
     *
     * <pre>
     * equals(null, null)   = true
     * equals(null, &quot;abc&quot;)  = false
     * equals(&quot;abc&quot;, null)  = false
     * equals(&quot;abc&quot;, &quot;abc&quot;) = true
     * equals(&quot;abc&quot;, &quot;ABC&quot;) = false
     * </pre>
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     * @since 2022.0.1
     */
    public static boolean equals(CharSequence str1, CharSequence str2) {
        return equals(str1, str2, false);
    }

    /**
     * 比较两个字符串（大小写不敏感）.
     *
     * <pre>
     * equalsIgnoreCase(null, null)   = true
     * equalsIgnoreCase(null, &quot;abc&quot;)  = false
     * equalsIgnoreCase(&quot;abc&quot;, null)  = false
     * equalsIgnoreCase(&quot;abc&quot;, &quot;abc&quot;) = true
     * equalsIgnoreCase(&quot;abc&quot;, &quot;ABC&quot;) = true
     * </pre>
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     * @since 2022.0.1
     */
    public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        return equals(str1, str2, true);
    }

    /**
     * 比较两个字符串是否相等.
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     * @param ignoreCase 是否忽略大小写
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     * @since 2022.0.1
     */
    public static boolean equals(CharSequence str1, CharSequence str2, boolean ignoreCase) {
        if (null == str1) {
            // 只有两个都为 null 才判断相等
            return str2 == null;
        }
        if (null == str2) {
            // 字符串 2 空，字符串 1 非空，直接 false
            return false;
        }

        if (ignoreCase) {
            return str1.toString().equalsIgnoreCase(str2.toString());
        }
        else {
            return str1.equals(str2);
        }
    }

    /**
     * 截取两个字符串的不同部分（长度一致），判断截取的子串是否相同.<br>
     * 任意一个字符串为 null 返回 false.
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

    // ----------------------------------------------------------------------------------------------
    // SubString

    /**
     * 去掉指定后缀.
     * @param str 字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
     * @since 2022.0.1
     */
    public static String subSuffix(CharSequence str, CharSequence suffix) {
        if (!hasLength(str) || !hasLength(suffix)) {
            return StringPools.EMPTY;
        }

        final String str2 = str.toString();
        if (str2.endsWith(suffix.toString())) {
            return subBefore(str2, str2.length() - suffix.length());
        }
        return str2;
    }

    /**
     * 忽略大小写去掉指定后缀.
     * @param str 字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
     * @since 2022.0.1
     */
    public static String subSuffixIgnoreCase(CharSequence str, CharSequence suffix) {
        if (!hasLength(str) || !hasLength(suffix)) {
            return StringPools.EMPTY;
        }

        final String str2 = str.toString();
        if (str2.toLowerCase().endsWith(suffix.toString().toLowerCase())) {
            return subBefore(str2, str2.length() - suffix.length());
        }
        return str2;
    }

    /**
     * 去掉指定后缀，并小写首字母.
     * @param str 字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
     * @since 2022.0.1
     */
    public static String subSuffixAndLowerFirst(CharSequence str, CharSequence suffix) {
        return firstToLower(subSuffix(str, suffix));
    }

    /**
     * 去掉指定前缀.
     * @param str 字符串
     * @param prefix 前缀
     * @return 切掉后的字符串，若前缀不是 prefix， 返回原字符串
     * @since 2022.0.1
     */
    public static String subPrefix(CharSequence str, CharSequence prefix) {
        if (!hasLength(str) || !hasLength(prefix)) {
            return StringPools.EMPTY;
        }

        final String str2 = str.toString();
        if (str2.startsWith(prefix.toString())) {
            return subAfter(str2, prefix.length());
        }
        return str2;
    }

    /**
     * 改进 JDK subString. index 从 0 开始计算，最后一个字符为 -1 <br>
     * 如果 from 和 to 位置一样，返回 "" <br>
     * 如果 from 或 to 为负数，则按照 length 从后向前数位置，如果绝对值大于字符串长度，则 from 归到0，to 归到 length<br>
     * 如果经过修正的 index 中 from 大于 to，则互换 from 和 to example: <br>
     * abcdefgh 2 3 =》 c <br>
     * abcdefgh 2 -3 =》 cde <br>
     * .
     * @param str string
     * @param fromIndex 开始的index（包括）
     * @param toIndex 结束的index（不包括）
     * @return 字串
     * @since 2022.0.1
     */
    public static String sub(CharSequence str, int fromIndex, int toIndex) {
        if (!hasLength(str)) {
            return StringPools.EMPTY;
        }
        int len = str.length();

        if (fromIndex < 0) {
            fromIndex = len + fromIndex;
            if (fromIndex < 0) {
                fromIndex = 0;
            }
        }
        else if (fromIndex > len) {
            fromIndex = len;
        }

        if (toIndex < 0) {
            toIndex = len + toIndex;
            if (toIndex < 0) {
                toIndex = len;
            }
        }
        else if (toIndex > len) {
            toIndex = len;
        }

        if (toIndex < fromIndex) {
            int tmp = fromIndex;
            fromIndex = toIndex;
            toIndex = tmp;
        }

        if (fromIndex == toIndex) {
            return StringPools.EMPTY;
        }

        return str.toString().substring(fromIndex, toIndex);
    }

    /**
     * 切割指定位置之前部分的字符串.
     * @param string 字符串
     * @param toIndex 切割到的位置（不包括）
     * @return 切割后的剩余的前半部分字符串
     * @since 2022.0.1
     */
    public static String subBefore(CharSequence string, int toIndex) {
        return sub(string, 0, toIndex);
    }

    /**
     * 截取分隔字符串之前的字符串，不包括分隔字符串. 如果给定的字符串为空串（null或""）或者分隔字符串为null，返回原字符串<br>
     * 如果分隔字符串为空串""，则返回空串，如果分隔字符串未找到，返回原字符串
     * <p>
     * 例子：
     *
     * <pre>
     * StringUtil.subBefore(null, *)      = null
     * StringUtil.subBefore("", *)        = ""
     * StringUtil.subBefore("abc", "a")   = ""
     * StringUtil.subBefore("abcba", "b") = "a"
     * StringUtil.subBefore("abc", "c")   = "ab"
     * StringUtil.subBefore("abc", "d")   = "abc"
     * StringUtil.subBefore("abc", "")    = ""
     * StringUtil.subBefore("abc", null)  = "abc"
     * </pre>.
     * @param string 被查找的字符串
     * @param separator 分隔字符串（不包括）
     * @param isLastSeparator 是否查找最后一个分隔字符串（多次出现分隔字符串时选取最后一个），true 为选取最后一个
     * @return 切割后的字符串
     * @since 2022.0.1
     */
    public static String subBefore(CharSequence string, CharSequence separator, boolean isLastSeparator) {
        if (!hasLength(string) || separator == null) {
            return (string != null) ? string.toString() : null;
        }

        final String str = string.toString();
        final String sep = separator.toString();
        if (sep.isEmpty()) {
            return StringPools.EMPTY;
        }
        final int pos = isLastSeparator ? str.lastIndexOf(sep) : str.indexOf(sep);
        if (pos == INDEX_NOT_FOUND) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * 切割指定位置之后部分的字符串.
     * @param string 字符串
     * @param fromIndex 切割开始的位置（包括）
     * @return 切割后后剩余的后半部分字符串
     * @since 2022.0.1
     */
    public static String subAfter(CharSequence string, int fromIndex) {
        if (!hasLength(string)) {
            return StringPools.EMPTY;
        }
        return sub(string, fromIndex, string.length());
    }

    /**
     * 截取分隔字符串之后的字符串，不包括分隔字符串. 如果给定的字符串为空串（null或""），返回原字符串<br>
     * 如果分隔字符串为空串（null或""），则返回空串，如果分隔字符串未找到，返回空串
     * <p>
     * 例子：
     *
     * <pre>
     * StringUtil.subAfter(null, *)      = null
     * StringUtil.subAfter("", *)        = ""
     * StringUtil.subAfter(*, null)      = ""
     * StringUtil.subAfter("abc", "a")   = "bc"
     * StringUtil.subAfter("abcba", "b") = "cba"
     * StringUtil.subAfter("abc", "c")   = ""
     * StringUtil.subAfter("abc", "d")   = ""
     * StringUtil.subAfter("abc", "")    = "abc"
     * </pre>.
     * @param string 被查找的字符串
     * @param separator 分隔字符串（不包括）
     * @param isLastSeparator 是否查找最后一个分隔字符串（多次出现分隔字符串时选取最后一个），true为选取最后一个
     * @return 切割后的字符串
     * @since 2022.0.1
     */
    public static String subAfter(CharSequence string, CharSequence separator, boolean isLastSeparator) {
        if (!hasLength(string)) {
            return (string != null) ? string.toString() : null;
        }
        if (separator == null) {
            return StringPools.EMPTY;
        }
        final String str = string.toString();
        final String sep = separator.toString();
        final int pos = isLastSeparator ? str.lastIndexOf(sep) : str.indexOf(sep);
        if (pos == INDEX_NOT_FOUND) {
            return StringPools.EMPTY;
        }
        return str.substring(pos + separator.length());
    }

    /**
     * 截取指定字符串中间部分，不包括标识字符串<br>
     * <p>
     * 例子：
     *
     * <pre>
     * StringUtil.subBetween("wx[b]yz", "[", "]") = "b"
     * StringUtil.subBetween(null, *, *)          = null
     * StringUtil.subBetween(*, null, *)          = null
     * StringUtil.subBetween(*, *, null)          = null
     * StringUtil.subBetween("", "", "")          = ""
     * StringUtil.subBetween("", "", "]")         = null
     * StringUtil.subBetween("", "[", "]")        = null
     * StringUtil.subBetween("yabcz", "", "")     = ""
     * StringUtil.subBetween("yabcz", "y", "z")   = "abc"
     * StringUtil.subBetween("yabczyabcz", "y", "z")   = "abc"
     * </pre>.
     * @param str 被切割的字符串
     * @param before 截取开始的字符串标识
     * @param after 截取到的字符串标识
     * @return 截取后的字符串
     * @since 2022.0.1
     */
    public static String subBetween(CharSequence str, CharSequence before, CharSequence after) {
        if (str == null || before == null || after == null) {
            return null;
        }

        final String str2 = str.toString();
        final String before2 = before.toString();
        final String after2 = after.toString();

        final int start = str2.indexOf(before2);
        if (start != INDEX_NOT_FOUND) {
            final int end = str2.indexOf(after2, start + before2.length());
            if (end != INDEX_NOT_FOUND) {
                return str2.substring(start + before2.length(), end);
            }
        }
        return null;
    }

    /**
     * 截取指定字符串中间部分，不包括标识字符串.
     * <p>
     * 例子：
     *
     * <pre>
     * StringUtil.subBetween(null, *)            = null
     * StringUtil.subBetween("", "")             = ""
     * StringUtil.subBetween("", "tag")          = null
     * StringUtil.subBetween("tagabctag", null)  = null
     * StringUtil.subBetween("tagabctag", "")    = ""
     * StringUtil.subBetween("tagabctag", "tag") = "abc"
     * </pre>.
     * @param str 被切割的字符串
     * @param beforeAndAfter 截取开始和结束的字符串标识
     * @return 截取后的字符串
     * @since 2022.0.1
     */
    public static String subBetween(CharSequence str, CharSequence beforeAndAfter) {
        return subBetween(str, beforeAndAfter, beforeAndAfter);
    }

    // ----------------------------------------------------------------------------------------------
    // repeat

    /**
     * 重复指定字符.
     * @param ch 重复字符
     * @param repeat 重复次数
     * @return 操作后的字符串
     * @since 2022.0.1
     */
    public static String repeat(final char ch, final int repeat) {
        if (repeat <= 0) {
            return StringPools.EMPTY;
        }
        final char[] buf = new char[repeat];
        for (int i = repeat - 1; i >= 0; i--) {
            buf[i] = ch;
        }
        return new String(buf);
    }

    /**
     * 重复指定字符.
     * @param str 字符串
     * @param repeat 重复次数
     * @return 操作后的字符串
     * @since 2022.0.1
     */
    public static String repeat(final String str, final int repeat) {

        // Performance tuned for 2.0 (JDK1.4)
        if (str == null) {
            return null;
        }
        if (repeat <= 0) {
            return StringPools.EMPTY;
        }
        final int inputLength = str.length();
        if (repeat == 1 || inputLength == 0) {
            return str;
        }
        if (inputLength == 1 && repeat <= PAD_LIMIT) {
            return repeat(str.charAt(0), repeat);
        }

        final int outputLength = inputLength * repeat;
        switch (inputLength) {
        case 1:
            return repeat(str.charAt(0), repeat);
        case 2:
            final char ch0 = str.charAt(0);
            final char ch1 = str.charAt(1);
            final char[] output2 = new char[outputLength];
            for (int i = repeat * 2 - 2; i >= 0; i--, i--) {
                output2[i] = ch0;
                output2[i + 1] = ch1;
            }
            return new String(output2);
        default:
            final StringBuilder buf = new StringBuilder(outputLength);
            for (int i = 0; i < repeat; i++) {
                buf.append(str);
            }
            return buf.toString();
        }
    }

    /**
     * 重复指定字符.
     * @param str 字符串
     * @param separator 分隔符
     * @param repeat 重复次数
     * @return 操作后的字符串
     * @since 2022.0.1
     */
    public static String repeat(final String str, final String separator, final int repeat) {
        if (str == null || separator == null) {
            return repeat(str, repeat);
        }
        // given that repeat(String, int) is quite optimized, better to rely on it than
        // try and splice this into it
        final String result = repeat(str + separator, repeat);
        return subBefore(result, separator, true);
    }

    // ----------------------------------------------------------------------------------------------
    // contains

    /**
     * 判断给定的字符串是否包含指定字符.
     *
     * <p>
     * {@code null} 的字符串和 {@code null} 的搜索字符都返回 {@code false}.
     * </p>
     *
     * <pre>
     * StringUtils.containsAny(null, *)               = false
     * StringUtils.containsAny("", *)                 = false
     * StringUtils.containsAny(*, null)               = false
     * StringUtils.containsAny(*, "")                 = false
     * StringUtils.containsAny("zzabyycdxx", "za")    = true
     * StringUtils.containsAny("zzabyycdxx", "by")    = true
     * StringUtils.containsAny("zzabyycdxx", "zy")    = true
     * StringUtils.containsAny("zzabyycdxx", "\tx")   = true
     * StringUtils.containsAny("zzabyycdxx", "$.#yF") = true
     * StringUtils.containsAny("aba", "z")            = false
     * </pre>.
     * @param cs 被检查的字符串，可能为空
     * @param searchChars 搜索字符串，可能为空
     * @return 如果包含搜索字符，则返回 {@code true}
     * @since 2022.0.1
     */
    public static boolean containsAny(final CharSequence cs, final CharSequence searchChars) {
        if (searchChars == null) {
            return false;
        }
        return containsAny(cs, ((String) searchChars).toCharArray());
    }

    /**
     * <p>
     * 判断给定的字符串是否包含指定字符.
     * </p>
     *
     * <p>
     * {@code null} 的字符串和 {@code null} 的搜索字符都返回 {@code false}.
     * </p>
     *
     * <pre>
     * StringUtils.containsAny(null, *)                  = false
     * StringUtils.containsAny("", *)                    = false
     * StringUtils.containsAny(*, null)                  = false
     * StringUtils.containsAny(*, [])                    = false
     * StringUtils.containsAny("zzabyycdxx", ['z', 'a']) = true
     * StringUtils.containsAny("zzabyycdxx", ['b', 'y']) = true
     * StringUtils.containsAny("zzabyycdxx", ['z', 'y']) = true
     * StringUtils.containsAny("aba", ['z'])             = false
     * </pre>.
     * @param cs 被检查的字符串，可能为空
     * @param searchChars 搜索字符串，可能为空
     * @return 如果包含搜索字符，则返回 {@code true}
     * @since 2022.0.1
     */
    public static boolean containsAny(final CharSequence cs, final char... searchChars) {
        if (!hasLength(cs) || Array.getLength(searchChars) == 0) {
            return false;
        }
        final int csLength = cs.length();
        final int searchLength = searchChars.length;
        final int csLast = csLength - 1;
        final int searchLast = searchLength - 1;
        for (int i = 0; i < csLength; i++) {
            final char ch = cs.charAt(i);
            for (int j = 0; j < searchLength; j++) {
                if (searchChars[j] == ch) {
                    if (Character.isHighSurrogate(ch)) {
                        if (j == searchLast) {
                            // missing low surrogate, fine, like String.indexOf(String)
                            return true;
                        }
                        if (i < csLast && searchChars[j + 1] == cs.charAt(i + 1)) {
                            return true;
                        }
                    }
                    else {
                        // ch is in the Basic Multilingual Plane
                        return true;
                    }
                }
            }
        }
        return false;
    }
    // ----------------------------------------------------------------------------------------------
    //

    // ----------------------------------------------------------------------------------------------
    //

    // ==================================== Convert ====================================

    /**
     * 首字母变小写.
     * @param str 字符串
     * @return {string}
     * @since 2022.0.1
     */
    public static String firstToLower(String str) {
        if (isBlank(str)) {
            return StringPools.EMPTY;
        }
        char firstChar = str.charAt(0);
        if (firstChar >= CharPools.UPPER_A && firstChar <= CharPools.UPPER_Z) {
            char[] arr = str.toCharArray();
            arr[0] += (CharPools.LOWER_A - CharPools.UPPER_A);
            return new String(arr);
        }
        return str;
    }

    /**
     * 首字母变大写.
     * @param str 字符串
     * @return {string}
     * @since 2022.0.1
     */
    public static String firstToUpper(String str) {
        if (isBlank(str)) {
            return StringPools.EMPTY;
        }
        char firstChar = str.charAt(0);
        if (firstChar >= CharPools.LOWER_A && firstChar <= CharPools.LOWER_Z) {
            char[] arr = str.toCharArray();
            arr[0] -= (CharPools.LOWER_A - CharPools.UPPER_A);
            return new String(arr);
        }
        return str;
    }

    // ==================================== ====================================

    // ==================================== ====================================

    // ==================================== ====================================

    // ==================================== ====================================

    // ==================================== Lambda ====================================

    public static void commaArrayWithOperation(Collection<String> arrays, Consumer<String> consumer) {
        arrays.forEach(consumer);
    }

    public static void commaArrayWithOperation(String array, Consumer<String> consumer) {
        Set<String> result = commaDelimitedListToSet(array);
        commaArrayWithOperation(result, consumer);
    }

    /**
     * 创建 stringBuilder 对象.
     * @param sb 初始stringBuilder
     * @param strs 初始字符串列表
     * @return stringBuilder对象
     * @since 2022.0.1
     */
    public static StringBuilder appendBuilder(StringBuilder sb, CharSequence... strs) {
        for (CharSequence str : strs) {
            sb.append(str);
        }
        return sb;
    }

    // ==================================== Private Other
    // ====================================

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
     * List 转 array.
     * @param list list
     * @return array
     */
    private static String[] toArray(List<String> list) {
        return list.toArray(new String[0]);
    }

}
