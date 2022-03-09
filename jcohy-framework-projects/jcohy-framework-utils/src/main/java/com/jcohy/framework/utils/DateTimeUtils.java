package com.jcohy.framework.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.jcohy.framework.utils.constant.StringPools;

/**
 * 描述: .
 *
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:14:31
 * @since 2022.0.1
 */
public final class DateTimeUtils {

    private DateTimeUtils() {
    }

    /**
     * 年月日时分秒.
     */
    public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 年月日.
     */
    public static final String PATTERN_DATE = "yyyy-MM-dd";

    /**
     * 时分秒.
     */
    public static final String PATTERN_TIME = "HH:mm:ss";

    /**
     * 年月日时分秒.
     */
    public static final String PATTERN_DATETIME_MINI = "yyyyMMddHHmmss";

    /**
     * 日期格式化.
     */
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern(DateTimeUtils.PATTERN_DATETIME);

    /**
     * 日格式化.
     */
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(DateTimeUtils.PATTERN_DATE);

    /**
     * 时分格式化.
     */
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern(DateTimeUtils.PATTERN_TIME);

    // ========================================================== 获取当前日期
    // ==========================================================
    /**
     * 日期的简化模式：2021-09-15.
     *
     * 获取今日日期.
     * @return string 只返回年月日
     */
    public static String simpleToday() {
        return format(nowLocalDateTime(), PATTERN_DATE);
    }

    /**
     * 日期的简化模式，指定分隔符.
     *
     * DateTimeUtils.simpleToday("/") == 2021/09/15;
     * @param delimiter delimiter
     * @return string 只返回年月日
     */
    public static String simpleToday(String delimiter) {
        return format(LocalDateTime.now(), PATTERN_DATE.replace(StringPools.DASH, delimiter));
    }

    /**
     * 返回今日日期.
     *
     * DateTimeUtils.today() == 2021-09-15 12:58:39
     * @return /
     */
    public static String today() {
        return format(LocalDateTime.now(), PATTERN_DATETIME);
    }

    /**
     * 获取今天的时间.
     * @return 时间
     */
    public static String time() {
        return format(nowLocalDateTime(), PATTERN_DATETIME_MINI);
    }

    /**
     * 获取当前日期(jdk 8). Wed Sep 15 12:59:27 CST 2021
     * @return 当前日期
     */
    public static Date nowDate() {
        return Date.from(DateTimeUtils.toInstant(DateTimeUtils.nowLocalDateTime()));
    }

    /**
     * 获取当前日期.
     * @return 当前日期
     */
    public static LocalDateTime nowLocalDateTime() {
        return LocalDateTime.now();
    }

    // ===============================

    public static String format(TemporalAccessor temporal, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(temporal);
    }

    /**
     * 时间转 instant.
     * @param dateTime 时间
     * @return instant
     */
    public static Instant toInstant(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    /**
     * localDateTime 转换成毫秒数.
     * @return long
     */
    public static long toMilliseconds() {
        return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * localDateTime 转换成毫秒数.
     * @param localDateTime localDateTime
     * @return long
     */
    public static long toMilliseconds(final LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 将字符串转换为时间.
     * @param dateStr 时间字符串
     * @param pattern 表达式
     * @return 时间
     */
    public static Date parse(String dateStr, String pattern) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, format);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
