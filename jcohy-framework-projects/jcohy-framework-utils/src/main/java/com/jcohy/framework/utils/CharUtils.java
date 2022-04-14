package com.jcohy.framework.utils;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/3/22:11:24
 * @since 2022.0.1
 */
public class CharUtils extends org.apache.commons.lang3.CharUtils {

    /**
     * 字母字符进行比较,区分大小写.
     * @param x 第一个字母
     * @param y 第二个字母
     * @return {@code true} x 和 y 相等
     */
    public static boolean asciiAlphaEquals(final char x, final char y) {
        return asciiAlphaEquals(x, y, false);
    }

    /**
     * 字母字符进行比较,不区分大小写.
     * @param x 第一个字母
     * @param y 第二个字母
     * @return {@code true} x 和 y 相等
     */
    public static boolean asciiAlphaEqualsIgnoreCase(final char x, final char y) {
        return asciiAlphaEquals(x, y, true);
    }

    /**
     * 字母字符进行比较.
     * @param x 第一个字母
     * @param y 第二个字母
     * @param ignoreCase 是否区分大小写
     * @return {@code true} x 和 y 相等
     */
    public static boolean asciiAlphaEquals(final char x, final char y, boolean ignoreCase) {
        if (!isAsciiAlpha(x)) {
            return false;
        }

        if (!isAsciiAlpha(y)) {
            return false;
        }
        if (ignoreCase) {
            return Math.abs(compare(x, y)) == 32 || x == y;
        }
        return x == y;
    }

}
