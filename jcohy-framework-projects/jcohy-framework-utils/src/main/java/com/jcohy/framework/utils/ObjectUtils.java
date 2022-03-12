package com.jcohy.framework.utils;

import java.util.StringJoiner;

/**
 * 描述: .
 * <p>
 *     Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 * @author jiac
 * @version 2022.0.1 3/2/22:15:02
 * @since 2022.0.1
 */
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {

    private static final String NULL_STRING = "null";

    private static final String EMPTY_STRING = "";

    private static final String ARRAY_START = "{";

    private static final String ARRAY_END = "}";

    private static final String EMPTY_ARRAY = ARRAY_START + ARRAY_END;

    private static final String ARRAY_ELEMENT_SEPARATOR = ", ";

    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    /**
     * 返回指定对象的字符串表示形式. Return a String representation of the specified Object.
     * <p>
     * 如果是数组，则内容为字符串表示形式。 如果 obj 为 null，则返回 {@code "null"} 字符串。
     * @param obj 为其构建字符串表示的对象
     * @return obj 的字符串表示形式
     * @since 2022.0.1
     */
    public static String nullSafeToString(Object obj) {
        if (obj == null) {
            return NULL_STRING;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Object[]) {
            return nullSafeToString((Object[]) obj);
        }
        if (obj instanceof boolean[]) {
            return nullSafeToString((boolean[]) obj);
        }
        if (obj instanceof byte[]) {
            return nullSafeToString((byte[]) obj);
        }
        if (obj instanceof char[]) {
            return nullSafeToString((char[]) obj);
        }
        if (obj instanceof double[]) {
            return nullSafeToString((double[]) obj);
        }
        if (obj instanceof float[]) {
            return nullSafeToString((float[]) obj);
        }
        if (obj instanceof int[]) {
            return nullSafeToString((int[]) obj);
        }
        if (obj instanceof long[]) {
            return nullSafeToString((long[]) obj);
        }
        if (obj instanceof short[]) {
            return nullSafeToString((short[]) obj);
        }
        String str = obj.toString();
        return (str != null) ? str : EMPTY_STRING;
    }

    /**
     * 返回指定数组内容的字符串表示形式.
     * <p>
     * 数组元素的 String 表现形式由花括号 ({@code "{}"}) 括起来，相邻元素之间用 {@code ", "} 分隔（逗号之后有一个空格）。 如果数组为
     * {@code null}，则返回 {@code "null"} 字符串。
     * @param array 为其构建字符串表示的数组
     * @return 数组的字符串表示
     * @since 2022.0.1
     */
    public static String nullSafeToString(Object[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (Object o : array) {
            stringJoiner.add(String.valueOf(o));
        }
        return stringJoiner.toString();
    }

    /**
     * 返回指定数组内容的字符串表示形式.
     * <p>
     * 数组元素的 String 表现形式由花括号 ({@code "{}"}) 括起来，相邻元素之间用 {@code ", "} 分隔（逗号之后有一个空格）。 如果数组为
     * {@code null}，则返回 {@code "null"} 字符串。
     * @param array 为其构建字符串表示的数组
     * @return 数组的字符串表示
     * @since 2022.0.1
     */
    public static String nullSafeToString(boolean[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (boolean b : array) {
            stringJoiner.add(String.valueOf(b));
        }
        return stringJoiner.toString();
    }

    /**
     * 返回指定数组内容的字符串表示形式.
     * <p>
     * 数组元素的 String 表现形式由花括号 ({@code "{}"}) 括起来，相邻元素之间用 {@code ", "} 分隔（逗号之后有一个空格）。 如果数组为
     * {@code null}，则返回 {@code "null"} 字符串。
     * @param array 为其构建字符串表示的数组
     * @return 数组的字符串表示
     * @since 2022.0.1
     */
    public static String nullSafeToString(byte[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (byte b : array) {
            stringJoiner.add(String.valueOf(b));
        }
        return stringJoiner.toString();
    }

    /**
     * 返回指定数组内容的字符串表示形式.
     * <p>
     * 数组元素的 String 表现形式由花括号 ({@code "{}"}) 括起来，相邻元素之间用 {@code ", "} 分隔（逗号之后有一个空格）。 如果数组为
     * {@code null}，则返回 {@code "null"} 字符串。
     * @param array 为其构建字符串表示的数组
     * @return 数组的字符串表示
     * @since 2022.0.1
     */
    public static String nullSafeToString(char[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (char c : array) {
            stringJoiner.add('\'' + String.valueOf(c) + '\'');
        }
        return stringJoiner.toString();
    }

    /**
     * 返回指定数组内容的字符串表示形式.
     * <p>
     * 数组元素的 String 表现形式由花括号 ({@code "{}"}) 括起来，相邻元素之间用 {@code ", "} 分隔（逗号之后有一个空格）。 如果数组为
     * {@code null}，则返回 {@code "null"} 字符串。
     * @param array 为其构建字符串表示的数组
     * @return 数组的字符串表示
     * @since 2022.0.1
     */
    public static String nullSafeToString(double[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (double d : array) {
            stringJoiner.add(String.valueOf(d));
        }
        return stringJoiner.toString();
    }

    /**
     * 返回指定数组内容的字符串表示形式.
     * <p>
     * 数组元素的 String 表现形式由花括号 ({@code "{}"}) 括起来，相邻元素之间用 {@code ", "} 分隔（逗号之后有一个空格）。 如果数组为
     * {@code null}，则返回 {@code "null"} 字符串。
     * @param array 为其构建字符串表示的数组
     * @return 数组的字符串表示
     * @since 2022.0.1
     */
    public static String nullSafeToString(float[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (float f : array) {
            stringJoiner.add(String.valueOf(f));
        }
        return stringJoiner.toString();
    }

    /**
     * 返回指定数组内容的字符串表示形式.
     * <p>
     * 数组元素的 String 表现形式由花括号 ({@code "{}"}) 括起来，相邻元素之间用 {@code ", "} 分隔（逗号之后有一个空格）。 如果数组为
     * {@code null}，则返回 {@code "null"} 字符串。
     * @param array 为其构建字符串表示的数组
     * @return 数组的字符串表示
     * @since 2022.0.1
     */
    public static String nullSafeToString(int[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (int i : array) {
            stringJoiner.add(String.valueOf(i));
        }
        return stringJoiner.toString();
    }

    /**
     * 返回指定数组内容的字符串表示形式.
     * <p>
     * 数组元素的 String 表现形式由花括号 ({@code "{}"}) 括起来，相邻元素之间用 {@code ", "} 分隔（逗号之后有一个空格）。 如果数组为
     * {@code null}，则返回 {@code "null"} 字符串。
     * @param array 为其构建字符串表示的数组
     * @return 数组的字符串表示
     * @since 2022.0.1
     */
    public static String nullSafeToString(long[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (long l : array) {
            stringJoiner.add(String.valueOf(l));
        }
        return stringJoiner.toString();
    }

    /**
     * 返回指定数组内容的字符串表示形式.
     * <p>
     * 数组元素的 String 表现形式由花括号 ({@code "{}"}) 括起来，相邻元素之间用 {@code ", "} 分隔（逗号之后有一个空格）。 如果数组为
     * {@code null}，则返回 {@code "null"} 字符串。
     * @param array 为其构建字符串表示的数组
     * @return 数组的字符串表示
     * @since 2022.0.1
     */
    public static String nullSafeToString(short[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringJoiner stringJoiner = new StringJoiner(ARRAY_ELEMENT_SEPARATOR, ARRAY_START, ARRAY_END);
        for (short s : array) {
            stringJoiner.add(String.valueOf(s));
        }
        return stringJoiner.toString();
    }

}
