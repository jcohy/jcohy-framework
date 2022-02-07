package com.framework.utils.base;

/**
 * Copyright: Copyright (c) 2021 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 1.0.0 2021/1/11:23:41
 * @since 1.0.0
 */
public class NumberUtils {

    /**
     * <p>将  {@code String} 类型转换为 {@code float}, 如果转换失败则返回默认值 {@code defaultValue} </p>
     *
     * <p>如果 {@code str} 为 {@code null}, 则返回默认值 {@code defaultValue} </p>
     *
     * <pre>
     *   NumberUtils.toFloat(null, 1.1f)   = 1.0f
     *   NumberUtils.toFloat("", 1.1f)     = 1.1f
     *   NumberUtils.toFloat("1.5", 0.0f)  = 1.5f
     * </pre>
     *
     * @param str 待转换的 String，可能为 {@code null}
     * @param defaultValue 默认值
     * @return string 转为 float, 如果失败则为默认值
     */
    public static float toFloat(final String str, final float defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(str);
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }
}
