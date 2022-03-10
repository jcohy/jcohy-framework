package com.jcohy.framework.utils;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

import com.jcohy.framework.utils.constant.CharPools;
import com.jcohy.framework.utils.constant.StringPools;

/**
 * 描述: 继承 {@link org.springframework.util.StringUtils}.
 *
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2/18/22:15:35
 * @since 2022.0.1
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final String[] EMPTY_STRING_ARRAY = {};

    // ---------------------------------------------------------------------
    // 格式化
    // ---------------------------------------------------------------------

    /**
     * 同 log 格式的 format 规则.
     * <p>
     * use: format("my name is {}, and i like {}!", "jiac", "Java").
     * @param message 需要转换的字符串.
     * @param args 需要替换的变量.
     * @return 转换后的字符串.
     * @since 2022.0.1
     */
    public static String format(String message, Object... args) {
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
    public static String formatStr(String message, Object... arguments) {
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

    /**
     * 用单引号包含给定的字符串.
     * @param str 输入的 {@code String} (e.g. "myString")
     * @return 需要引入的 {@code String} (e.g. "'myString'"),如果输入为 {@code null} 则返回
     * {@code null}
     * @since 2022.0.1
     */
    public static String quote(String str) {
        return (str != null) ? "'" + str + "'" : null;
    }

    /**
     * 如果给定的对象是 String 的实例，则将他使用单引号括起来，否则，保持 Object不变.
     * @param obj 输入 Object (e.g. "myString")
     * @return 引用的字符串 {@code String} (e.g. "'myString'"),或者如果不是字符串，则输入对象原为字符串
     * @since 2022.0.1
     */
    public static Object quoteIfString(Object obj) {
        return (obj instanceof String) ? quote((String) obj) : obj;
    }

    /**
     * 取消由 '.' 限定的字符串. 例如，"this.name.is.qualified" 返回 "qualified".
     * @param qualifiedName 全限定名
     * @return 取消限定符后的 String
     * @since 2022.0.1
     */
    public static String unqualify(String qualifiedName) {
        return unqualify(qualifiedName, '.');
    }

    /**
     * 取消由分隔符限定的字符串.
     *
     * 例如，如果使用 ':' 分隔符，"this:name:is:qualified" 将返回 "qualified"。
     * @param qualifiedName 全限定名
     * @param separator 分隔符
     * @return 取消限定符后的 String
     * @since 2022.0.1
     */
    public static String unqualify(String qualifiedName, char separator) {
        return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
    }

    /**
     * 大写字符串，根据 {@link Character#toUpperCase(char)} 将第一个字母更改为大写。 其他字母没有变化.
     * @param str 需要大写的字符串
     * @return 大写后的 {@code String}
     * @since 2022.0.1
     */
    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }

    /**
     * 小写字符串，根据 {@link Character#toLowerCase(char)} 将第一个字母变为小写。其他字母不变.
     * @param str 需要小写的字符串
     * @return 小写后的 {@code String}
     * @since 2022.0.1
     */
    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (isEmpty(str)) {
            return str;
        }

        char baseChar = str.charAt(0);
        char updatedChar;
        if (capitalize) {
            updatedChar = Character.toUpperCase(baseChar);
        }
        else {
            updatedChar = Character.toLowerCase(baseChar);
        }
        if (baseChar == updatedChar) {
            return str;
        }

        char[] chars = str.toCharArray();
        chars[0] = updatedChar;
        return new String(chars, 0, chars.length);
    }

    // ---------------------------------------------------------------------
    // 拆分 Split
    // ---------------------------------------------------------------------

    /**
     * <p>
     * 根据指定的分隔符将文本拆分为一个集合，默认分隔符为逗号 {@link StringPools#COMMA}，,去除空元素，去除前后空白符，并且区分大小写.
     *
     * <p>
     * NOTE: 注意 {@link StringUtils#split(String)} 的区别： {@link StringUtils#split(String)}
     * 默认以空白符拆分
     * </p>
     * <p>
     * 返回的字符串数组中不包含分隔符。相邻的分隔符被视为一个分隔度.
     * </p>
     * @param str 待拆分的字符串，可能为空
     * @return 拆分后的数组, 如果输入为 null, 则输出为 {@code null}
     * @since 2021.0.1
     * @see StringUtils#split(String)
     */
    public static List<String> defaultSplit(String str) {
        return defaultSplit(str, -1);
    }

    /**
     * 根据指定的分隔符将文本拆分为一个集合,指定分隔符,排空，去除空元素，并且区分大小写.
     *
     * <p>
     * NOTE: 注意 {@link StringUtils#split(String)} 的区别： {@link StringUtils#split(String)}
     * 默认以空白符拆分
     * </p>
     * <p>
     * 返回的字符串数组中不包含分隔符。相邻的分隔符被视为一个分隔度.
     * </p>
     * @param str 待拆分的字符串，可能为空
     * @param separator 分隔符
     * @return 拆分后的数组, 如果输入为 null, 则输出为 {@code null}
     * @since 2022.0.1
     * @see StringUtils#split(String)
     */
    public static List<String> defaultSplit(String str, String separator) {
        return defaultSplit(str, separator, -1);
    }

    /**
     * 根据指定的分隔符将文本拆分为一个集合,默认分隔符为逗号 {@link StringPools#COMMA},去除空元素，去除前后空白符，并且区分大小写.
     *
     * <p>
     * NOTE: 注意 {@link StringUtils#split(String)} 的区别： {@link StringUtils#split(String)}
     * 默认以空白符拆分
     * </p>
     * <p>
     * 返回的字符串数组中不包含分隔符。相邻的分隔符被视为一个分隔度.
     * </p>
     * @param str 待拆分的字符串，可能为空
     * @param max 数组的最大长度. 零或负值意味着没有限制
     * @return 拆分后的数组, 如果输入为 null, 则输出为 {@code null}
     * @since 2022.0.1
     * @see StringUtils#split(String)
     */
    public static List<String> defaultSplit(String str, int max) {
        return defaultSplit(str, StringPools.COMMA, max);
    }

    /**
     * 根据指定的分隔符将文本拆分为一个集合,指定分隔符 {@link StringPools#COMMA},去除空元素，去除前后空白符，并且区分大小写.
     *
     * <p>
     * NOTE: 注意 {@link StringUtils#split(String)} 的区别： {@link StringUtils#split(String)}
     * 默认以空白符拆分
     * </p>
     * <p>
     * 返回的字符串数组中不包含分隔符。相邻的分隔符被视为一个分隔度.
     * </p>
     * @param str 待拆分的字符串，可能为空
     * @param separator 分隔符
     * @param max 数组的最大长度. 零或负值意味着没有限制
     * @return 拆分后的数组, 如果输入为 null, 则输出为 {@code null}
     * @since 2022.0.1
     * @see StringUtils#split(String)
     */
    public static List<String> defaultSplit(String str, String separator, int max) {
        return split(str, separator, max, true, true, false);
    }

    /**
     * 切分字符串路径，仅支持 Unix 分界符：/.
     * @param str 被切分的字符串
     * @return 切分后的集合
     * @since 2022.0.1
     */
    public static String[] splitPath(String str) {
        return splitPath(str, 0);
    }

    /**
     * 切分字符串路径，仅支持unix分界符：/.
     * @param str 被切分的字符串
     * @param limit 限制分片数
     * @return 切分后的集合
     * @since 2022.0.1
     */
    public static String[] splitPath(String str, int limit) {
        return split(str, StringPools.SLASH, limit);
    }

    /**
     * 切分字符串路径，仅支持 Unix 分界符：/.
     * @param str 被切分的字符串
     * @return 切分后的集合
     * @since 2022.0.1
     */
    public static List<String> splitPathToList(String str) {
        return Arrays.asList(splitPath(str));
    }

    /**
     * 切分字符串路径，仅支持unix分界符：/.
     * @param str 被切分的字符串
     * @param limit 限制分片数
     * @return 切分后的集合
     * @since 2022.0.1
     */
    public static List<String> splitPathToList(String str, int limit) {
        return Arrays.asList(splitPath(str, limit));
    }

    /**
     * 切分字符串，去除每个元素两边空格，不忽略大小写.
     * @param str 被切分的字符串
     * @param separator 分隔符字符串
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     * @since 2022.0.1
     */
    public static List<String> splitTrim(String str, String separator, boolean ignoreEmpty) {
        return split(str, separator, -1, true, ignoreEmpty, false);
    }

    /**
     * 切分字符串，去除每个元素两边空格，不忽略大小写.
     * @param str 被切分的字符串
     * @param separator 分隔符字符串
     * @param limit 限制分片数
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     * @since 2022.0.1
     */
    public static List<String> splitTrim(String str, String separator, int limit, boolean ignoreEmpty) {
        return split(str, separator, limit, true, ignoreEmpty, false);
    }

    /**
     * 切分字符串，忽略大小写.
     * @param str 被切分的字符串
     * @param separator 分隔符字符串
     * @param isTrim 是否去除切分字符串后每个元素两边的空格
     * @param ignoreEmpty 是否忽略空串
     * @return 切分后的集合
     * @since 2022.0.1
     */
    public static List<String> splitIgnoreCase(String str, String separator, boolean isTrim, boolean ignoreEmpty) {
        return split(str, separator, -1, isTrim, ignoreEmpty, true);
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
        return toArray(split(str, separator, limit, isTrim, ignoreEmpty, false));
    }

    /**
     * 使用空白符切分字符串<br>
     * 切分后的字符串两边不包含空白符，空串或空白符串并不做为元素之一.
     * @param str 被切分的字符串
     * @param limit 限制分片数
     * @return 切分后的集合
     * @since 2022.0.1
     */
    public static List<String> split(String str, int limit) {
        if (isEmpty(str)) {
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
        if (isEmpty(str)) {
            return new ArrayList<String>(0);
        }
        if (limit == 1) {
            return addToList(new ArrayList<String>(1), str, isTrim, ignoreEmpty);
        }

        if (isEmpty(separator)) {
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
        if (isEmpty(str)) {
            return new ArrayList<String>(0);
        }
        if (limit == 1) {
            return addToList(new ArrayList<String>(1), str, isTrim, ignoreEmpty);
        }

        final ArrayList<String> list = new ArrayList<>((limit > 0) ? limit : 16);
        int len = str.length();
        int start = 0;
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (CharUtils.asciiAlphaEquals(separator, str.charAt(i), ignoreCase)
                    || Objects.equals(separator, str.charAt(i))) {
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
    public static List<String> splitByReg(String str, Pattern separatorPattern, int limit, boolean isTrim,
            boolean ignoreEmpty) {
        if (isEmpty(str)) {
            return new ArrayList<>(0);
        }
        if (limit == 1) {
            return addToList(new ArrayList<String>(1), str, isTrim, ignoreEmpty);
        }

        if (null == separatorPattern) {
            return defaultSplit(str, limit);
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
     * 根据给定长度，将给定字符串截取为多个部分.
     * @param str 字符串
     * @param len 每一个小节的长度
     * @return 截取后的字符串数组
     * @since 2022.0.1
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

    // ---------------------------------------------------------------------
    // Delimited
    // ---------------------------------------------------------------------

    /**
     * 将逗号分割的字符串转为 Set.
     * <p>
     * 返回集合中的元素将保留 {@link LinkedHashSet} 中的原始顺序.
     * @param str 输入的字符串 (可能为 {@code null} 或空)
     * @return 返回的 {@code String} 集合
     * @since 2022.0.1
     */
    public static Set<String> commaDelimitedListToSet(String str) {
        String[] tokens = commaDelimitedListToStringArray(str);
        return new LinkedHashSet<>(Arrays.asList(tokens));
    }

    /**
     * 根据逗号分隔符转换为字符串数组.
     * @param str 输入的字符串 (可能为 {@code null} 或空)
     * @return string 数组，如果输入为空，则为空数组
     * @since 2022.0.1
     */
    public static String[] commaDelimitedListToStringArray(String str) {
        return delimitedListToStringArray(str, ",");
    }

    /**
     * 根据指定的分隔符将字符串转为字符串列表.
     * @param str 输入的字符串 (可能为 {@code null} 或空)
     * @param delimiter 分隔符
     * @return 数组
     * @since 2022.0.1
     */
    public static String[] delimitedListToStringArray(String str, String delimiter) {
        return delimitedListToStringArray(str, delimiter, null);
    }

    /**
     * 根据指定的分隔符将字符串转为字符串列表.
     * @param str 输入的字符串 (可能为 {@code null} 或空)
     * @param delimiter 分隔符
     * @param charsToDelete 要删除的字符; 可以用来删除不需要的换行符: 例如. "\r\n\f" 将删除字符串中的所有新行和换行符
     * @return 数组
     * @since 2022.0.1
     */
    public static String[] delimitedListToStringArray(String str, String delimiter, String charsToDelete) {

        if (str == null) {
            return EMPTY_STRING_ARRAY;
        }
        if (delimiter == null) {
            return new String[] { str };
        }

        List<String> result = new ArrayList<>();
        if (delimiter.isEmpty()) {
            for (int i = 0; i < str.length(); i++) {
                result.add(deleteAny(str.substring(i, i + 1), charsToDelete));
            }
        }
        else {
            int pos = 0;
            int delPos;
            while ((delPos = str.indexOf(delimiter, pos)) != -1) {
                result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
                pos = delPos + delimiter.length();
            }
            if (str.length() > 0 && pos <= str.length()) {
                // Add rest of String, but not in case of empty input.
                result.add(deleteAny(str.substring(pos), charsToDelete));
            }
        }
        return toStringArray(result);
    }

    /**
     * 将 {@link Collection} 转换为分隔字符串.
     * <p>
     * 对于 {@code toString()} 实现很有用.
     * @param coll 要转换的集合（可能为 null 或空）
     * @param delim 要使用的分隔符（通常是 ","）
     * @param prefix 以每个元素开头的字符串
     * @param suffix 以每个元素结束的字符串
     * @return 分隔的字符串
     * @since 2022.0.1
     */
    public static String collectionToDelimitedString(Collection<?> coll, String delim, String prefix, String suffix) {

        if (CollectionUtils.isEmpty(coll)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        Iterator<?> it = coll.iterator();
        while (it.hasNext()) {
            sb.append(prefix).append(it.next()).append(suffix);
            if (it.hasNext()) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }

    /**
     * 将 {@link Collection} 转换为分隔字符串.
     * <p>
     * 对于 {@code toString()} 实现很有用.
     * @param coll 要转换的集合（可能为 null 或空）
     * @param delim 要使用的分隔符（通常是 ","）
     * @return 分隔的字符串
     * @since 2022.0.1
     */
    public static String collectionToDelimitedString(Collection<?> coll, String delim) {
        return collectionToDelimitedString(coll, delim, "", "");
    }

    /**
     * 将 {@link Collection} 转换为分隔字符串.
     * <p>
     * 对于 {@code toString()} 实现很有用.
     * @param coll 要转换的集合（可能为 null 或空）
     * @return 分隔的字符串
     * @since 2022.0.1
     */
    public static String collectionToCommaDelimitedString(Collection<?> coll) {
        return collectionToDelimitedString(coll, ",");
    }

    /**
     * 将字符串数组转换为分隔字符串. Convert a {@code String} array into a delimited {@code String}
     * (e.g. CSV).
     * <p>
     * 对于 {@code toString()} 实现很有用.
     * @param arr 要转换的数组（可能为 null 或空）
     * @param delim 要使用的分隔符（通常是 ","）
     * @return 分隔的字符串
     * @since 2022.0.1
     */
    public static String arrayToDelimitedString(Object[] arr, String delim) {
        if (ObjectUtils.isEmpty(arr)) {
            return "";
        }
        if (arr.length == 1) {
            return ObjectUtils.nullSafeToString(arr[0]);
        }

        StringJoiner sj = new StringJoiner(delim);
        for (Object elem : arr) {
            sj.add(String.valueOf(elem));
        }
        return sj.toString();
    }

    /**
     * 将字符串数组转换为分隔字符串. Convert a {@code String} array into a delimited {@code String}
     * (e.g. CSV).
     * <p>
     * 对于 {@code toString()} 实现很有用.
     * @param arr 要转换的数组（可能为 null 或空）
     * @return 分隔的字符串
     * @since 2022.0.1
     */
    public static String arrayToCommaDelimitedString(Object[] arr) {
        return arrayToDelimitedString(arr, ",");
    }

    // ---------------------------------------------------------------------
    // IndexOf
    // ---------------------------------------------------------------------

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

    // ---------------------------------------------------------------------
    // Equals
    // ---------------------------------------------------------------------

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

    // ---------------------------------------------------------------------
    // Remove
    // ---------------------------------------------------------------------

    /**
     * 去掉指定后缀，并小写首字母.
     * @param str 字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
     * @since 2022.0.1
     */
    public static String removeEndAndLowerFirst(String str, String suffix) {
        return uncapitalize(removeEnd(str, suffix));
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
    public static String remove(CharSequence str, int fromIndex, int toIndex) {
        if (isEmpty(str)) {
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

    // ---------------------------------------------------------------------
    // Trim
    // ---------------------------------------------------------------------

    /**
     * 去除字符串前后的空格.
     * @param str the {@code String} to check
     * @return the trimmed {@code String}
     * @since 2022.0.1
     * @see java.lang.Character#isWhitespace
     */
    public static String trimWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }

        int beginIndex = 0;
        int endIndex = str.length() - 1;

        while (beginIndex <= endIndex && Character.isWhitespace(str.charAt(beginIndex))) {
            beginIndex++;
        }

        while (endIndex > beginIndex && Character.isWhitespace(str.charAt(endIndex))) {
            endIndex--;
        }

        return str.substring(beginIndex, endIndex + 1);
    }

    /**
     * 删除给定字符串的所有空白字符.
     * @param str 给定字符串
     * @return 修剪后的 {@code String}
     * @since 2022.0.1
     * @see java.lang.Character#isWhitespace
     */
    public static String trimAllWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }

        int len = str.length();
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (!Character.isWhitespace(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 删除给定字符串前面的空白字符.
     * @param str 给定字符串
     * @return 修剪后的 {@code String}
     * @since 2022.0.1
     * @see java.lang.Character#isWhitespace
     */
    public static String trimLeadingWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }

        int beginIdx = 0;
        while (beginIdx < str.length() && Character.isWhitespace(str.charAt(beginIdx))) {
            beginIdx++;
        }
        return str.substring(beginIdx);
    }

    /**
     * 删除给定字符串后面的空白字符.
     * @param str 给定字符串
     * @return 修剪后的 {@code String}
     * @since 2022.0.1
     * @see java.lang.Character#isWhitespace
     */
    public static String trimTrailingWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }

        int endIdx = str.length() - 1;
        while (endIdx >= 0 && Character.isWhitespace(str.charAt(endIdx))) {
            endIdx--;
        }
        return str.substring(0, endIdx + 1);
    }

    /**
     * 从给定的字符串中删除所有出现的提供的前导字符.
     * @param str 给定字符串
     * @param leadingCharacter 前导字符
     * @return 修剪后的 {@code String}
     * @since 2022.0.1
     */
    public static String trimLeadingCharacter(String str, char leadingCharacter) {
        if (isEmpty(str)) {
            return str;
        }

        int beginIdx = 0;
        while (beginIdx < str.length() && leadingCharacter == str.charAt(beginIdx)) {
            beginIdx++;
        }
        return str.substring(beginIdx);
    }

    /**
     * 从给定的 String 中删除所有出现的提供的尾随字符.
     * @param str 给定字符串
     * @param trailingCharacter 尾随字符
     * @return 修剪后的 {@code String}
     * @since 2022.0.1
     */
    public static String trimTrailingCharacter(String str, char trailingCharacter) {
        if (isEmpty(str)) {
            return str;
        }

        int endIdx = str.length() - 1;
        while (endIdx >= 0 && trailingCharacter == str.charAt(endIdx)) {
            endIdx--;
        }
        return str.substring(0, endIdx + 1);
    }

    // ---------------------------------------------------------------------
    // Delete
    // ---------------------------------------------------------------------

    /**
     * 删除指定字符串出现的指定字符.
     * @param inString 原始字符串
     * @param pattern 要删除的所有出现的模式
     * @return 结果字符串
     * @since 2022.0.1
     */
    public static String delete(String inString, String pattern) {
        return replace(inString, pattern, "");
    }

    /**
     * 删除所有指定字符串出现的指定字符 Delete any character in a given {@code String}.
     * @param inString 原始字符串
     * @param charsToDelete 要删除的所有字符的集合
     * @return 结果 {@code String}
     * @since 2022.0.1
     */
    public static String deleteAny(String inString, String charsToDelete) {
        if (isEmpty(inString) || isEmpty(charsToDelete)) {
            return inString;
        }

        int lastCharIndex = 0;
        char[] result = new char[inString.length()];
        for (int i = 0; i < inString.length(); i++) {
            char c = inString.charAt(i);
            if (charsToDelete.indexOf(c) == -1) {
                result[lastCharIndex++] = c;
            }
        }
        if (lastCharIndex == inString.length()) {
            return inString;
        }
        return new String(result, 0, lastCharIndex);
    }

    // ---------------------------------------------------------------------
    // 判断
    // ---------------------------------------------------------------------

    /**
     * 检查给定字符串是否包含任何空白字符.
     * @param seq 给定字符串,可能为空或 {@code null}
     * @return 如果字符串不为空且包含任何一个字符串，则返回 {@code true}
     * @since 2022.0.1
     * @see Character#isWhitespace(char)
     */
    public static boolean containsWhitespace(CharSequence seq) {
        if (isEmpty(seq)) {
            return false;
        }
        int strLen = seq.length();
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(seq.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查给定字符串是否包含任何空白字符.
     * @param str 给定字符串,可能为空或 {@code null}
     * @return 如果字符串不为空且包含任何一个字符串，则返回 {@code true}
     * @since 2022.0.1
     * @see #containsWhitespace(CharSequence)
     */
    public static boolean containsWhitespace(String str) {
        return containsWhitespace((CharSequence) str);
    }

    /**
     * 测试给定的 {@code String} 是否匹配给定的单个字符.
     * @param str 给定字符串
     * @param singleCharacter 比较的字符
     * @return {@code true} 是单子符
     * @since 2022.0.1
     */
    public static boolean matchesCharacter(String str, char singleCharacter) {
        return (str != null && str.length() == 1 && str.charAt(0) == singleCharacter);
    }

    /**
     * 测试给定的字符串是否以指定的前缀开始，忽略大小写.
     * @param str 给定字符串
     * @param prefix 查找的前缀
     * @return {@code true} 是单子符
     * @since 2022.0.1
     * @see java.lang.String#startsWith
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {
        return (str != null && prefix != null && str.length() >= prefix.length()
                && str.regionMatches(true, 0, prefix, 0, prefix.length()));
    }

    /**
     * 测试给定的字符串是否以指定的后缀开始，忽略大小写.
     * @param str t给定字符串
     * @param suffix 查找的后缀
     * @return {@code true} 以指定的后缀开始
     * @since 2022.0.1
     * @see java.lang.String#endsWith
     */
    public static boolean endsWithIgnoreCase(String str, String suffix) {
        return (str != null && suffix != null && str.length() >= suffix.length()
                && str.regionMatches(true, str.length() - suffix.length(), suffix, 0, suffix.length()));
    }

    /**
     * 在给定的索引处，测试给定的字符串是否匹配给定的子字符串.
     * @param str 原始字符串
     * @param index 原始字符串中开始匹配子字符串的索引
     * @param substring 给定索引处要匹配的子字符串
     * @return {@code true} 匹配给定的子字符串
     * @since 2022.0.1
     */
    public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
        if (index + substring.length() > str.length()) {
            return false;
        }
        for (int i = 0; i < substring.length(); i++) {
            if (str.charAt(index + i) != substring.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    // ---------------------------------------------------------------------
    // 文件路径操作
    // ---------------------------------------------------------------------

    /**
     * 从给定的 Java 资源路径中提取文件名. 例如. {@code "mypath/myfile.txt" -&gt; "myfile.txt"}.
     * @param path 文件路径 (可能为 {@code null})
     * @return 提取的文件名, 如果没有，则返回 {@code null}
     * @since 2022.0.1
     */
    public static String getFilename(String path) {
        if (path == null) {
            return null;
        }

        int separatorIndex = path.lastIndexOf(StringPools.FOLDER_SEPARATOR);
        return (separatorIndex != -1) ? path.substring(separatorIndex + 1) : path;
    }

    /**
     * 从给定的 Java 资源路径中提取文件扩展名.
     * <p>
     * e.g. "mypath/myfile.txt" -&gt; "txt".
     * </p>
     * @param path 文件路径 (可能为 {@code null})
     * @return 文件扩展名, 如果没有，则返回 {@code null}
     * @since 2022.0.1
     */
    public static String getFilenameExtension(String path) {
        if (path == null) {
            return null;
        }

        int extIndex = path.lastIndexOf(CharPools.DOT);
        if (extIndex == -1) {
            return null;
        }

        int folderIndex = path.lastIndexOf(StringPools.FOLDER_SEPARATOR);
        if (folderIndex > extIndex) {
            return null;
        }

        return path.substring(extIndex + 1);
    }

    /**
     * 从给定的 Java 资源路径中去除文件扩展名.
     * <p>
     * e.g. "mypath/myfile.txt" -&gt; "mypath/myfile".
     * </p>
     * @param path 文件路径
     * @return 去除文件扩展名的路径
     * @since 2022.0.1
     */
    public static String stripFilenameExtension(String path) {
        int extIndex = path.lastIndexOf(CharPools.EXTENSION_SEPARATOR);
        if (extIndex == -1) {
            return path;
        }

        int folderIndex = path.lastIndexOf(StringPools.FOLDER_SEPARATOR);
        if (folderIndex > extIndex) {
            return path;
        }

        return path.substring(0, extIndex);
    }

    /**
     * 将指定的相对路径应用于给定的 Java 资源路径，假设标准 Java 文件夹分隔（即 "/" 分隔符）.
     * @param path 开始的路径（通常是完整的文件路径）
     * @param relativePath 相对路径 (相对于上面的完整文件路径)
     * @return 相对路径产生的完整文件路径
     * @since 2022.0.1
     */
    public static String applyRelativePath(String path, String relativePath) {
        int separatorIndex = path.lastIndexOf(StringPools.FOLDER_SEPARATOR);
        if (separatorIndex != -1) {
            String newPath = path.substring(0, separatorIndex);
            if (!relativePath.startsWith(StringPools.FOLDER_SEPARATOR)) {
                newPath += StringPools.FOLDER_SEPARATOR;
            }
            return newPath + relativePath;
        }
        else {
            return relativePath;
        }
    }

    /**
     * 规范化路径.通过清除 诸如 "path/.." 或着内部的一些点号.
     * <p>
     * 结果为了方便对路径进行比较. 对于其他用途，请注意 Windows 分隔符 ("\") 被简单的斜杠替换。
     * <p>
     * <strong>NOTE</strong> 在安全上下文中不应使用 cleanPath。 应该使用其他机制来防止路径遍历问题。
     * @param path 原始路径
     * @return 规范化路径
     * @since 2022.0.1
     */
    public static String cleanPath(String path) {
        if (isEmpty(path)) {
            return path;
        }
        String pathToUse = replace(path, StringPools.WINDOWS_FOLDER_SEPARATOR, StringPools.FOLDER_SEPARATOR);

        // Shortcut if there is no work to do
        if (pathToUse.indexOf('.') == -1) {
            return pathToUse;
        }

        // Strip prefix from path to analyze, to not treat it as part of the
        // first path element. This is necessary to correctly parse paths like
        // "file:core/../core/io/Resource.class", where the ".." should just
        // strip the first "core" directory while keeping the "file:" prefix.
        int prefixIndex = pathToUse.indexOf(':');
        String prefix = "";
        if (prefixIndex != -1) {
            prefix = pathToUse.substring(0, prefixIndex + 1);
            if (prefix.contains(StringPools.FOLDER_SEPARATOR)) {
                prefix = "";
            }
            else {
                pathToUse = pathToUse.substring(prefixIndex + 1);
            }
        }
        if (pathToUse.startsWith(StringPools.FOLDER_SEPARATOR)) {
            prefix = prefix + StringPools.FOLDER_SEPARATOR;
            pathToUse = pathToUse.substring(1);
        }

        String[] pathArray = delimitedListToStringArray(pathToUse, StringPools.FOLDER_SEPARATOR);
        Deque<String> pathElements = new ArrayDeque<>();
        int tops = 0;

        for (int i = pathArray.length - 1; i >= 0; i--) {
            String element = pathArray[i];
            if (StringPools.CURRENT_PATH.equals(element)) {
                // Points to current directory - drop it.
            }
            else if (StringPools.TOP_PATH.equals(element)) {
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
            pathElements.addFirst(StringPools.TOP_PATH);
        }
        // If nothing else left, at least explicitly point to current path.
        if (pathElements.size() == 1 && pathElements.getLast().isEmpty()
                && !prefix.endsWith(StringPools.FOLDER_SEPARATOR)) {
            pathElements.addFirst(StringPools.CURRENT_PATH);
        }

        return prefix + collectionToDelimitedString(pathElements, StringPools.FOLDER_SEPARATOR);
    }

    /**
     * 对路径进行规范化后进行比较.
     * @param path1 第一个路径
     * @param path2 第二个路径
     * @return 比较规范化后的路径是否相等
     * @since 2022.0.1
     */
    public static boolean pathEquals(String path1, String path2) {
        return cleanPath(path1).equals(cleanPath(path2));
    }

    // ---------------------------------------------------------------------
    // URI 解码
    // ---------------------------------------------------------------------

    /**
     * 解码给定的编码 URI 值。 基于以下规则：
     * <ul>
     * <li>字母数字字符 {@code "a"} 到 {@code "z"}, {@code "A"} 到 {@code "Z"}, 和 {@code "0"} 到
     * {@code "9"} 保持不变.</li>
     * <li>特殊字符 {@code "-"}, {@code "_"}, {@code "."}, 和 {@code "*"} 保持不变.</li>
     * <li>序列 "{@code %<i>xy</i>}" 被解释为字符的十六进制表示.</li>
     * </ul>
     * @param source 编码的 URI
     * @param charset 编码
     * @return 解码后的 URI
     * @throws IllegalArgumentException 当给定的 source 包含无效的编码序列时
     * @since 2022.0.1
     * @see java.net.URLDecoder#decode(String, String)
     */
    public static String uriDecode(String source, Charset charset) {
        int length = source.length();
        if (length == 0) {
            return source;
        }
        Validate.notNull(charset, "Charset must not be null");

        ByteArrayOutputStream baos = new ByteArrayOutputStream(length);
        boolean changed = false;
        for (int i = 0; i < length; i++) {
            int ch = source.charAt(i);
            if (ch == '%') {
                if (i + 2 < length) {
                    char hex1 = source.charAt(i + 1);
                    char hex2 = source.charAt(i + 2);
                    int u = Character.digit(hex1, 16);
                    int l = Character.digit(hex2, 16);
                    if (u == -1 || l == -1) {
                        throw new IllegalArgumentException("Invalid encoded sequence \"" + source.substring(i) + "\"");
                    }
                    baos.write((char) ((u << 4) + l));
                    i += 2;
                    changed = true;
                }
                else {
                    throw new IllegalArgumentException("Invalid encoded sequence \"" + source.substring(i) + "\"");
                }
            }
            else {
                baos.write(ch);
            }
        }
        return (changed ? StreamUtils.copyToString(baos, charset) : source);
    }

    // ---------------------------------------------------------------------
    // Locale TimeZone 处理
    // ---------------------------------------------------------------------

    /**
     * 将给定的 String 值解析为 {@link Locale}，接受 {@link Locale#toString} 格式以及 BCP 47 语言标签.
     * @param localeValue the locale value: 符合 {@code Locale's} 的 {@code toString()} 格式
     * ("en", "en_UK", etc), 也接受空格作为分隔符（作为下划线的替代）, 或 BCP 47 (e.g. "en-UK") 由 Java 7+ 上的
     * {@link Locale#forLanguageTag} 指定
     * @return 对应的 Locale 实例，如果没有则为 null
     * @throws IllegalArgumentException 如果 locale specification 无效
     * @since 2022.0.1
     * @see #parseLocaleString
     * @see Locale#forLanguageTag
     */
    public static Locale parseLocale(String localeValue) {
        String[] tokens = tokenizeLocaleSource(localeValue);
        if (tokens.length == 1) {
            validateLocalePart(localeValue);
            Locale resolved = Locale.forLanguageTag(localeValue);
            if (resolved.getLanguage().length() > 0) {
                return resolved;
            }
        }
        return parseLocaleTokens(localeValue, tokens);
    }

    /**
     * 将给定的字符串表示解析为 {@link Locale}. 对于很多解析场景来说，这是对 {@link Locale} 的 {@link Locale#toString
     * Locale's toString} 的逆操作， 从宽松的意义上来说。 此方法并非严格遵守语言环境设计； 它是专门为典型的 Spring 解析需求量身定制的。
     * <p>
     * <b> 注意：此委托不接受 BCP 47 语言标签格式。 请使用 {@link #parseLocale} 对这两种格式进行宽松的解析。</b>
     * @param localeString 语言环境字符串：遵循 {@code Locale's} 的 toString() 格式（"en", "en_UK"
     * 等），也接受空格作为分隔符（作为下划线的替代）。
     * @return 对应的 Locale 实例，如果没有则为 null
     * @throws IllegalArgumentException 如果语言环境规范无效
     * @since 2022.0.1
     */
    public static Locale parseLocaleString(String localeString) {
        return parseLocaleTokens(localeString, tokenizeLocaleSource(localeString));
    }

    private static String[] tokenizeLocaleSource(String localeSource) {
        return tokenizeToStringArray(localeSource, "_ ", false, false);
    }

    private static Locale parseLocaleTokens(String localeString, String[] tokens) {
        String language = (tokens.length > 0) ? tokens[0] : "";
        String country = (tokens.length > 1) ? tokens[1] : "";
        validateLocalePart(language);
        validateLocalePart(country);

        String variant = "";
        if (tokens.length > 2) {
            // There is definitely a variant, and it is everything after the country
            // code sans the separator between the country code and the variant.
            int endIndexOfCountryCode = localeString.indexOf(country, language.length()) + country.length();
            // Strip off any leading '_' and whitespace, what's left is the variant.
            variant = trimLeadingWhitespace(localeString.substring(endIndexOfCountryCode));
            if (variant.startsWith("_")) {
                variant = trimLeadingCharacter(variant, '_');
            }
        }

        if (variant.isEmpty() && country.startsWith("#")) {
            variant = country;
            country = "";
        }

        return (language.length() > 0) ? new Locale(language, country, variant) : null;
    }

    private static void validateLocalePart(String localePart) {
        for (int i = 0; i < localePart.length(); i++) {
            char ch = localePart.charAt(i);
            if (ch != ' ' && ch != '_' && ch != '-' && ch != '#' && !Character.isLetterOrDigit(ch)) {
                throw new IllegalArgumentException("Locale part \"" + localePart + "\" contains invalid characters");
            }
        }
    }

    /**
     * 将给定的 {@code timeZoneString} 值解析为 {@link TimeZone}.
     * @param timeZoneString time zone {@code String}, 符合
     * {@link TimeZone#getTimeZone(String)} 无效时抛出 {@link IllegalArgumentException} 异常
     * @return 对应的 {@link TimeZone} 实例
     * @throws IllegalArgumentException 无效的 time zone 规范
     * @since 2022.0.1
     */
    public static TimeZone parseTimeZoneString(String timeZoneString) {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
        if ("GMT".equals(timeZone.getID()) && !timeZoneString.startsWith("GMT")) {
            // We don't want that GMT fallback...
            throw new IllegalArgumentException("Invalid time zone specification '" + timeZoneString + "'");
        }
        return timeZone;
    }

    // ---------------------------------------------------------------------
    // String 数组处理
    // ---------------------------------------------------------------------

    /**
     * 将 {@link Collection} 转为数组.
     * <p>
     * {@code Collection} 必须只包含 {@code String} 元素.
     * @param collection 集合 (可能为 {@code null} 或空)
     * @return {@code String} 数组
     * @since 2022.0.1
     */
    public static String[] toStringArray(Collection<String> collection) {
        return (!CollectionUtils.isEmpty(collection) ? collection.toArray(EMPTY_STRING_ARRAY) : EMPTY_STRING_ARRAY);
    }

    /**
     * 将 {@link Enumeration} 转为数组
     * <p>
     * {@code Enumeration} 必须只包含 {@code String} 元素.
     * @param enumeration {@code Enumeration} (可能为 {@code null} 或空)
     * @return {@code String} 数组
     * @since 2022.0.1
     */
    public static String[] toStringArray(Enumeration<String> enumeration) {
        return (enumeration != null) ? toStringArray(Collections.list(enumeration)) : EMPTY_STRING_ARRAY;
    }

    /**
     * 将给定的字符串附加到给定的字符串数组，返回一个新数组.
     * @param array 要附加到的数组 (可以为 {@code null})
     * @param str 要附加的字符串
     * @return 新数组 (永不为 {@code null})
     * @since 2022.0.1
     */
    public static String[] addStringToArray(String[] array, String str) {
        if (ObjectUtils.isEmpty(array)) {
            return new String[] { str };
        }

        String[] newArr = new String[array.length + 1];
        System.arraycopy(array, 0, newArr, 0, array.length);
        newArr[array.length] = str;
        return newArr;
    }

    /**
     * 将给定的字符串数组连接成一个，重复的元素包含两次.
     * <p>
     * 保留原始数组中元素的顺序.
     * @param array1 第一个数组 (可以为 {@code null})
     * @param array2 第二个数组 (可以为 {@code null})
     * @return 新数组 (如果两个给定数组都为 null，则为 null)
     * @since 2022.0.1
     */
    public static String[] concatenateStringArrays(String[] array1, String[] array2) {
        if (ObjectUtils.isEmpty(array1)) {
            return array2;
        }
        if (ObjectUtils.isEmpty(array2)) {
            return array1;
        }

        String[] newArr = new String[array1.length + array2.length];
        System.arraycopy(array1, 0, newArr, 0, array1.length);
        System.arraycopy(array2, 0, newArr, array1.length, array2.length);
        return newArr;
    }

    /**
     * 对给定的字符串数组进行排序.
     * @param array 原始数组（可能为空）
     * @return 排序后的数组 (从不为 {@code null})
     * @since 2022.0.1
     */
    public static String[] sortStringArray(String[] array) {
        if (ObjectUtils.isEmpty(array)) {
            return array;
        }

        Arrays.sort(array);
        return array;
    }

    /**
     * 修剪给定字符串数组的元素，在每个非空元素上调用 {@code String.trim()}.
     * @param array 原始 {@code String} 数组 (可能为空)
     * @return 带有修剪元素的结果数组（大小相同）
     * @since 2022.0.1
     */
    public static String[] trimArrayElements(String[] array) {
        if (ObjectUtils.isEmpty(array)) {
            return array;
        }

        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            String element = array[i];
            result[i] = (element != null) ? element.trim() : null;
        }
        return result;
    }

    /**
     * 从给定的数组中删除重复的字符串.
     * @param array 字符串数组（可能为空）
     * @return 没有重复的数组，按自然排序顺序
     * @since 2022.0.1
     */
    public static String[] removeDuplicateStrings(String[] array) {
        if (ObjectUtils.isEmpty(array)) {
            return array;
        }

        Set<String> set = new LinkedHashSet<>(Arrays.asList(array));
        return toStringArray(set);
    }

    /**
     * 获取一个字符串数组并根据给定的分隔符拆分每个元素。 然后生成一个 {@code Properties} 实例，分隔符的左侧提供 key， 分隔符的右侧提供
     * value.
     * <p>
     * 在将它们添加到 {@code Properties} 之前将修剪 key 和 value.
     * @param array 要处理的数组
     * @param delimiter 使用（通常是等号）分割每个元素
     * @return 表示数组内容的 {@code Properties} 实例，如果要处理的数组为 null 或为空，则为 null
     * @since 2022.0.1
     */
    public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter) {
        return splitArrayElementsIntoProperties(array, delimiter, null);
    }

    /**
     * 获取一个字符串数组并根据给定的分隔符拆分每个元素。 然后生成一个 {@code Properties} 实例，分隔符的左侧提供 key， 分隔符的右侧提供
     * value.
     * <p>
     * 在将它们添加到 {@code Properties} 之前将修剪 key 和 value.
     * @param array 要处理的数组
     * @param delimiter 使用（通常是等号）分割每个元素
     * @param charsToDelete 在尝试拆分操作之前从每个元素中删除一个或多个字符（通常是引号符号），如果不删除，则为 {@code null}
     * @return 表示数组内容的 {@code Properties} 实例，如果要处理的数组为 null 或为空，则为 null
     * @since 2022.0.1
     */
    public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter, String charsToDelete) {

        if (ObjectUtils.isEmpty(array)) {
            return null;
        }

        Properties result = new Properties();
        for (String element : array) {
            if (charsToDelete != null) {
                element = deleteAny(element, charsToDelete);
            }
            String[] splittedElement = split(element, delimiter);
            if (splittedElement == null) {
                continue;
            }
            result.setProperty(splittedElement[0].trim(), splittedElement[1].trim());
        }
        return result;
    }

    // ---------------------------------------------------------------------
    // Token 标记处理
    // ---------------------------------------------------------------------

    /**
     * 通过 {@link StringTokenizer} 将给定的字符串 tokens 为字符串数组。
     * <p>
     * 修剪 tokens 并省略空 tokens.
     * <p>
     * 给定的分隔符 {@code delimiters} 可以由任意数量的分隔符组成. 这些字符中的每一个都可用于分隔 tokens. 定界符始终是单个字符；
     * 对于多字符分隔符，请考虑使用 {@link #delimitedListToStringArray}.
     * @param str 原始字符串 {@code String} (可能为 {@code null} 或空 )
     * @param delimiters 分隔符 (每个字符都被单独视为一个分隔符)
     * @return tokens 数组
     * @since 2022.0.1
     * @see java.util.StringTokenizer
     * @see String#trim()
     * @see #delimitedListToStringArray
     */
    public static String[] tokenizeToStringArray(String str, String delimiters) {
        return tokenizeToStringArray(str, delimiters, true, true);
    }

    /**
     * 通过 {@link StringTokenizer} 将给定的字符串 tokens 为字符串数组。
     * <p>
     * 修剪 tokens 并省略空 tokens.
     * <p>
     * 给定的分隔符 {@code delimiters} 可以由任意数量的分隔符组成. 这些字符中的每一个都可用于分隔 tokens. 定界符始终是单个字符；
     * 对于多字符分隔符，请考虑使用 {@link #delimitedListToStringArray}.
     * @param str 原始字符串 {@code String} (可能为 {@code null} 或空 )
     * @param delimiters 分隔符 (每个字符都被单独视为一个分隔符)
     * @param trimTokens 使用 {@link String#trim()} 对每个 token 进行修剪
     * @param ignoreEmptyTokens 忽略空的 tokens (仅适用于修剪后为空的令牌； StringTokenizer 首先不会将后续分隔符视为
     * token).
     * @return tokens 数组
     * @since 2022.0.1
     * @see java.util.StringTokenizer
     * @see String#trim()
     * @see #delimitedListToStringArray
     */
    public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens,
            boolean ignoreEmptyTokens) {

        if (str == null) {
            return EMPTY_STRING_ARRAY;
        }

        StringTokenizer st = new StringTokenizer(str, delimiters);
        List<String> tokens = new ArrayList<>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }
            if (!ignoreEmptyTokens || token.length() > 0) {
                tokens.add(token);
            }
        }
        return toStringArray(tokens);
    }

    // ---------------------------------------------------------------------
    // count
    // ---------------------------------------------------------------------

    /**
     * 计数子字符串 sub 在字符串 str 中的出现次数.
     * @param str 原始字符串
     * @param sub 子字符串
     * @return 次数
     * @since 2022.0.1
     */
    public static int countOccurrencesOf(String str, String sub) {
        if (isEmpty(str) || isEmpty(sub)) {
            return 0;
        }

        int count = 0;
        int pos = 0;
        int idx;
        while ((idx = str.indexOf(sub, pos)) != -1) {
            ++count;
            pos = idx + sub.length();
        }
        return count;
    }

    // ---------------------------------------------------------------------
    // UnderlineCase <---> CamelCase
    // ---------------------------------------------------------------------

    /**
     * 将驼峰式命名的字符串转换为下划线方式，又称SnakeCase、underScoreCase. 如果转换前的驼峰式命名的字符串为空，则返回空字符串。<br>
     * 规则为：
     * <ul>
     * <li>单字之间以下划线隔开</li>
     * <li>每个单字的首字母亦用小写字母</li>
     * </ul>
     * 例如：
     *
     * <pre>
     * HelloWorld=》hello_world
     * Hello_World=》hello_world
     * HelloWorld_test=》hello_world_test
     * </pre>
     * @param str 转换前的驼峰式命名的字符串，也可以为下划线形式
     * @return 转换后下划线方式命名的字符串
     * @since 2022.0.1
     */
    public static String toUnderlineCase(CharSequence str) {
        return toSymbolCase(str, CharPools.UNDERLINE);
    }

    /**
     * 将驼峰式命名的字符串转换为短横连接方式. 如果转换前的驼峰式命名的字符串为空，则返回空字符串。<br>
     * 规则为：
     * <ul>
     * <li>单字之间横线线隔开</li>
     * <li>每个单字的首字母亦用小写字母</li>
     * </ul>
     * 例如：
     *
     * <pre>
     * HelloWorld=》hello-world
     * Hello_World=》hello-world
     * HelloWorld_test=》hello-world-test
     * </pre>
     * @param str 转换前的驼峰式命名的字符串，也可以为下划线形式
     * @return 转换后下划线方式命名的字符串
     * @since 2022.0.1
     */
    public static String toKebabCase(CharSequence str) {
        return toSymbolCase(str, CharPools.DASHED);
    }

    /**
     * 将驼峰式命名的字符串转换为使用符号连接方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串.
     * @param str 转换前的驼峰式命名的字符串，也可以为符号连接形式
     * @param symbol 连接符
     * @return 转换后符号连接方式命名的字符串
     * @since 2022.0.1
     */
    public static String toSymbolCase(CharSequence str, char symbol) {
        if (str == null) {
            return null;
        }

        final int length = str.length();
        final StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < length; i++) {
            c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                final Character preChar = (i > 0) ? str.charAt(i - 1) : null;
                final Character nextChar = (i < str.length() - 1) ? str.charAt(i + 1) : null;

                if (null != preChar) {
                    if (symbol == preChar) {
                        // 前一个为分隔符
                        if (null == nextChar || Character.isLowerCase(nextChar)) {
                            // 普通首字母大写，如_Abb -> _abb
                            c = Character.toLowerCase(c);
                        }
                        // 后一个为大写，按照专有名词对待，如_AB -> _AB
                    }
                    else if (Character.isLowerCase(preChar)) {
                        // 前一个为小写
                        sb.append(symbol);
                        if (null == nextChar || Character.isLowerCase(nextChar) || CharUtils.isAsciiNumeric(nextChar)) {
                            // 普通首字母大写，如aBcc -> a_bcc
                            c = Character.toLowerCase(c);
                        }
                        // 后一个为大写，按照专有名词对待，如aBC -> a_BC
                    }
                    else {
                        // 前一个为大写
                        if (null == nextChar || Character.isLowerCase(nextChar)) {
                            // 普通首字母大写，如ABcc -> A_bcc
                            sb.append(symbol);
                            c = Character.toLowerCase(c);
                        }
                        // 后一个为大写，按照专有名词对待，如ABC -> ABC
                    }
                }
                else {
                    // 首字母，需要根据后一个判断是否转为小写
                    if (null == nextChar || Character.isLowerCase(nextChar)) {
                        // 普通首字母大写，如Abc -> abc
                        c = Character.toLowerCase(c);
                    }
                    // 后一个为大写，按照专有名词对待，如ABC -> ABC
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 将下划线方式命名的字符串转换为帕斯卡式. 规则为：
     * <ul>
     * <li>单字之间不以空格或任何连接符断开</li>
     * <li>第一个单字首字母采用大写字母</li>
     * <li>后续单字的首字母亦用大写字母</li>
     * </ul>
     * 如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。<br>
     * 例如：hello_world=》HelloWorld
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     * @since 2022.0.1
     */
    public static String toPascalCase(CharSequence name) {
        return StringUtils.uncapitalize(toCamelCase(name));
    }

    /**
     * 将下划线方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串. 规则为：
     * <ul>
     * <li>单字之间不以空格或任何连接符断开</li>
     * <li>第一个单字首字母采用小写字母</li>
     * <li>后续单字的首字母亦用大写字母</li>
     * </ul>
     * 例如：hello_world=》helloWorld
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     * @since 2022.0.1
     */
    public static String toCamelCase(CharSequence name) {
        return toCamelCase(name, CharPools.UNDERLINE);
    }

    /**
     * 将连接符方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串.
     * @param name 转换前的自定义方式命名的字符串
     * @param symbol 连接符
     * @return 转换后的驼峰式命名的字符串
     * @since 2022.0.1
     */
    public static String toCamelCase(CharSequence name, char symbol) {
        if (null == name) {
            return null;
        }

        final String name2 = name.toString();
        if (StringUtils.contains(name2, symbol)) {
            final int length = name2.length();
            final StringBuilder sb = new StringBuilder(length);
            boolean upperCase = false;
            for (int i = 0; i < length; i++) {
                char c = name2.charAt(i);

                if (c == symbol) {
                    upperCase = true;
                }
                else if (upperCase) {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                }
                else {
                    sb.append(Character.toLowerCase(c));
                }
            }
            return sb.toString();
        }
        else {
            return name2;
        }
    }

    // ---------------------------------------------------------------------
    // Lambda
    // ---------------------------------------------------------------------

    /**
     * Lamdba 表达式处理字符串.
     * @param arrays 数组
     * @param consumer lamdba 操作
     * @since 2022.0.1
     */
    public static void commaArrayWithOperation(Collection<String> arrays, Consumer<String> consumer) {
        arrays.forEach(consumer);
    }

    /**
     * Lamdba 表达式处理字符串.
     * @param array 数组
     * @param consumer lamdba 操作
     * @since 2022.0.1
     */
    public static void commaArrayWithOperation(String array, Consumer<String> consumer) {
        Set<String> result = commaDelimitedListToSet(array);
        commaArrayWithOperation(result, consumer);
    }

    // ---------------------------------------------------------------------
    // Default
    // ---------------------------------------------------------------------

    /**
     * <p>
     * 返回传入的 String，如果 String 为 {@code null}，则返回 {@code defaultStr} 的值.
     *
     * <pre>
     * StringUtils.defaultString(null, "NULL")  = "NULL"
     * StringUtils.defaultString("", "NULL")    = ""
     * StringUtils.defaultString("bat", "NULL") = "bat"
     * </pre>
     * @param str 传入的 String
     * @param defaultStr 默认返回的 String
     * @return 传入的字符串，如果为 null，则为默认值
     * @see Objects#toString(Object, String)
     */
    public static String defaultString(final Object str, final String defaultStr) {
        return Objects.toString(str, defaultStr);
    }

    // ---------------------------------------------------------------------
    // Other
    // ---------------------------------------------------------------------

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

    // ---------------------------------------------------------------------
    // Private
    // ---------------------------------------------------------------------

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
        return list.toArray(new String[list.size()]);
    }

}
