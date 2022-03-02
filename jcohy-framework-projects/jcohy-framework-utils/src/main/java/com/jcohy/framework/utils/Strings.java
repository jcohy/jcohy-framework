// package com.jcohy.framework.utils;
//
// import java.util.List;
//
// import javax.annotation.Nullable;
//
// import org.apache.commons.lang3.ArrayUtils;
// import org.apache.commons.lang3.StringUtils;
//
/// **
// * 描述: 对 {@link org.apache.commons.lang3.StringUtils} 的扩展.
// *
// * <p>
// * Copyright © 2022
// * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
// *
// * @author jiac
// * @version 2022.0.1 2/28/22:01:10
// * @since 2022.0.1
// */
// public class Strings extends StringUtils {
//
// public Strings() {
// }
//
// // Format message
// //-----------------------------------------------------------------------
// /**
// * 同 log 格式的 format 规则.
// * <p>
// * use: format("my name is {}, and i like {}!", "jiac", "Java").
// * @param message 需要转换的字符串.
// * @param args 需要替换的变量.
// * @return 转换后的字符串.
// * @since 2022.0.1
// */
// public static String format(String message,Object... args) {
// // message 为 null 返回空字符串
// if (isEmpty(message)) {
// return StringPools.EMPTY;
// }
// // 参数为 null 或者为空
// if (ArrayUtils.isEmpty(args)) {
// return message;
// }
//
// StringBuilder sb = new StringBuilder((int) (message.length() * 1.5));
// int cursor = 0;
// int index = 0;
// int argsLength = args.length;
// for (int start, end; (start = message.indexOf('{', cursor)) != -1 && (end =
// message.indexOf('}', start)) != -1
// && index < argsLength;) {
// sb.append(message, cursor, start);
// sb.append(args[index]);
// cursor = end + 1;
// index++;
// }
// sb.append(message.substring(cursor));
// return sb.toString();
// }
//
// /**
// * 同 log 格式的 format 规则
// * <p>
// * use: format("my name is %d, and i like %d!", "jiac", "Java").
// * @param message 需要转换的字符串
// * @param arguments 需要替换的变量
// * @return 转换后的字符串
// * @since 2022.0.1
// */
// public static String formatStr(@org.springframework.lang.Nullable String message,
// @org.springframework.lang.Nullable Object... arguments) {
// // message 为 null 返回空字符串
// if (message == null) {
// return StringPools.EMPTY;
// }
// // 参数为 null 或者为空
// if (arguments == null || arguments.length == 0) {
// return message;
// }
// return String.format(message, arguments);
// }
//
// /**
// * 格式化执行时间，单位为 ms 和 s，保留三位小数.
// * @param nanos 纳秒
// * @return 格式化后的时间
// * @since 2022.0.1
// */
// public static String formatTime(long nanos) {
// if (nanos < 1) {
// return "0ms";
// }
// double millis = (double) nanos / (1000 * 1000);
// // 不够 1 ms，最小单位为 ms
// if (millis > 1000) {
// return String.format("%.3fs", millis / 1000);
// }
// else {
// return String.format("%.3fms", millis);
// }
// }
//
// //
// ----------------------------------------------------------------------------------------------
// // Split by char 通过 char 类型拆分
//
// /**
// * 切分字符串路径，仅支持 Unix 分界符：/.
// * @param str 被切分的字符串
// * @return 切分后的集合
// * @since 2022.0.1
// */
// public static List<String> splitPath(String str) {
// return splitPath(str, 0);
// }
//
// /**
// * 切分字符串路径，仅支持unix分界符：/.
// * @param str 被切分的字符串
// * @param limit 限制分片数
// * @return 切分后的集合
// * @since 2022.0.1
// */
// public static List<String> splitPath(String str, int limit) {
// return split(str, StringPools.SLASH, limit, true, true);
// }
// }
