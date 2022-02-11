package com.framework.utils.base;


/**

 *
 * @since 3.0
 */
/**
 * Copyright: Copyright (c) 2022 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description: 代表 Java 规范的所有版本的枚举。主要是代表 <em>java.specification.version</em> 系统属性.
 *
 * @author jiac
 * @version 2022.0.1 2022/1/11:22:59
 * @since 2022.0.1
 */
public enum JavaVersion {

    /**
     * Android 报告的 Java 版本。 这不是官方的 Java 版本号。
     */
    JAVA_0_9(1.5f, "0.9"),

    /**
     * Java 1.1.
     */
    JAVA_1_1(1.1f, "1.1"),

    /**
     * Java 1.2.
     */
    JAVA_1_2(1.2f, "1.2"),

    /**
     * Java 1.3.
     */
    JAVA_1_3(1.3f, "1.3"),

    /**
     * Java 1.4.
     */
    JAVA_1_4(1.4f, "1.4"),

    /**
     * Java 1.5.
     */
    JAVA_1_5(1.5f, "1.5"),

    /**
     * Java 1.6.
     */
    JAVA_1_6(1.6f, "1.6"),

    /**
     * Java 1.7.
     */
    JAVA_1_7(1.7f, "1.7"),

    /**
     * Java 1.8.
     */
    JAVA_1_8(1.8f, "1.8"),

    /**
     * Java 9
     */
    JAVA_9(9.0f, "9"),

    /**
     * Java 10
     */
    JAVA_10(10.0f, "10"),

    /**
     * Java 11
     */
    JAVA_11(11.0f, "11"),

    /**
     * Java 12
     */
    JAVA_12(12.0f, "12"),

    /**
     * Java 13
     */
    JAVA_13(13.0f, "13"),

    /**
     * 最新的 Java 版本。
     */
    JAVA_RECENT(maxVersion(), Float.toString(maxVersion()));

    /**
     * The float value.
     */
    private final float value;

    /**
     * 版本号
     */
    private final String name;

    /**
     * 构造函数.
     *
     * @param value  the float value
     * @param name  the standard name, not null
     */
    JavaVersion(final float value, final String name) {
        this.value = value;
        this.name = name;
    }

    //-----------------------------------------------------------------------
    /**
     * <p> 当前版本是否大于等于指定版本 </p>
     *
     * <p>例如:<br>
     *  {@code myVersion.atLeast(JavaVersion.JAVA_1_4)}<p>
     *
     * @param requiredVersion  指定版本，不为 null
     * @return {@code true} 如果此版本大于或等于指定的版本
     */
    public boolean atLeast(final JavaVersion requiredVersion) {
        return this.value >= requiredVersion.value;
    }

    //-----------------------------------------------------------------------
    /**
     * <p> 当前版本是否小于等于指定版本 </p>
     *
     * <p>例如:<br>
     *  {@code myVersion.atMost(JavaVersion.JAVA_1_4)}<p>
     *
     * @param requiredVersion  指定版本，不为 null
     * @return {@code true} 如果此版本小于或等于指定的版本
     * @since 3.9
     */
    public boolean atMost(final JavaVersion requiredVersion) {
        return this.value <= requiredVersion.value;
    }

    /**
     * 将 Java 版本号转换为枚举常数
     *
     * @param nom Java 版本号
     * @return 相应的枚举常数；如果如 <b>null</b>，则版本未知
     */
    static JavaVersion getJavaVersion(final String nom) {
        return get(nom);
    }

    /**
     * 将 Java 版本号转换为枚举常数
     *
     * @param nom Java 版本号
     * @return 相应的枚举常数；如果如 <b>null</b>，则版本未知
     */
    static JavaVersion get(final String nom) {
        if (nom == null) {
            return null;
        } else if ("0.9".equals(nom)) {
            return JAVA_0_9;
        } else if ("1.1".equals(nom)) {
            return JAVA_1_1;
        } else if ("1.2".equals(nom)) {
            return JAVA_1_2;
        } else if ("1.3".equals(nom)) {
            return JAVA_1_3;
        } else if ("1.4".equals(nom)) {
            return JAVA_1_4;
        } else if ("1.5".equals(nom)) {
            return JAVA_1_5;
        } else if ("1.6".equals(nom)) {
            return JAVA_1_6;
        } else if ("1.7".equals(nom)) {
            return JAVA_1_7;
        } else if ("1.8".equals(nom)) {
            return JAVA_1_8;
        } else if ("9".equals(nom)) {
            return JAVA_9;
        } else if ("10".equals(nom)) {
            return JAVA_10;
        } else if ("11".equals(nom)) {
            return JAVA_11;
        } else if ("12".equals(nom)) {
            return JAVA_12;
        } else if ("13".equals(nom)) {
            return JAVA_13;
        }
        final float v = toFloatVersion(nom);
        // then we need to check decimals > .9
        if ((v - 1.) < 1.) {
            final int firstComma = Math.max(nom.indexOf('.'), nom.indexOf(','));
            final int end = Math.max(nom.length(), nom.indexOf(',', firstComma));
            if (Float.parseFloat(nom.substring(firstComma + 1, end)) > .9f) {
                return JAVA_RECENT;
            }
        } else if (v > 10) {
            return JAVA_RECENT;
        }
        return null;
    }

    //-----------------------------------------------------------------------
    /**
     * <p>标准名称.</p>
     *
     * <p>例如, {@code "1.5"}.</p>
     *
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * 从系统中获取 Java 版本，当  {@code java.specification.version} 系统属性未设置时，为 99.0
     *
     * @return {@code java.specification.version} 属性值或 99.0
     */
    private static float maxVersion() {
        final float v = toFloatVersion(System.getProperty("java.specification.version", "99.0"));
        if (v > 0) {
            return v;
        }
        return 99f;
    }

    /**
     * 将 String 解析为 float
     *
     * @param value value
     * @return 字符串表示的浮点值；如果无法解析给定的 {@code String}，则返回 -1。
     */
    private static float toFloatVersion(final String value) {
        final int defaultReturnValue = -1;
        if (value.contains(".")) {
            final String[] toParse = value.split("\\.");
            if (toParse.length >= 2) {
                return NumberUtils.toFloat(toParse[0] + '.' + toParse[1], defaultReturnValue);
            }
        } else {
            return NumberUtils.toFloat(value, defaultReturnValue);
        }
        return defaultReturnValue;
    }
}
