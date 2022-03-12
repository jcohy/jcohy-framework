package com.jcohy.framework.utils.unit;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

import com.jcohy.framework.utils.FileUtils;
import com.jcohy.framework.utils.StringUtils;
import com.jcohy.framework.utils.constant.StringPools;

/**
 * 描述: 数据大小，以字节为单位.
 *
 * <p>
 * 此类是不可变和线程安全的
 * </p>
 *
 * <table border="1">
 * <caption>数据大小</caption>
 * <tr>
 * <th>术语</th>
 * <th>Data Size</th>
 * <th>Size in Bytes</th>
 * </tr>
 * <tr>
 * <td>byte</td>
 * <td>1B</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td>kilobyte</td>
 * <td>1KB</td>
 * <td>1,024</td>
 * </tr>
 * <tr>
 * <td>megabyte</td>
 * <td>1MB</td>
 * <td>1,048,576</td>
 * </tr>
 * <tr>
 * <td>gigabyte</td>
 * <td>1GB</td>
 * <td>1,073,741,824</td>
 * </tr>
 * <tr>
 * <td>terabyte</td>
 * <td>1TB</td>
 * <td>1,099,511,627,776</td>
 * </tr>
 * <tr>
 * <td>petabyte</td>
 * <td>1PB</td>
 * <td>1,125,899,906,842,624</td>
 * </tr>
 * </table>
 * long 类型最大只能表示拍字节数的大小，如需表示更大的类型，可以参考 {@link FileUtils} 类中的相关方法
 * <p>
 *     Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 * @author jiac
 * @version 2022.0.1 3/10/22:11:59
 * @since 2022.0.1
 * @see DataUnit
 * @see FileUtils
 */
public final class DataSize implements Comparable<DataSize>, Serializable {

    /**
     * 解析正则.
     *
     * 匹配 0 个或 1 个 +/- 开头后跟数字,结尾匹配两位字母.
     */
    private static final Pattern PATTERN = Pattern.compile("^([+\\-]?\\d+)([a-zA-Z]{0,2})$");

    /**
     * 千字节.
     */
    private static final long BYTES_PER_KB = 1024;

    /**
     * 兆字节.
     */
    private static final long BYTES_PER_MB = BYTES_PER_KB * 1024;

    /**
     * 千兆字节.
     */
    private static final long BYTES_PER_GB = BYTES_PER_MB * 1024;

    /**
     * 太字节.
     */
    private static final long BYTES_PER_TB = BYTES_PER_GB * 1024;

    /**
     * 拍字节.
     */
    private static final long BYTES_PER_PB = BYTES_PER_TB * 1024;

    private final long bytes;

    private DataSize(long bytes) {
        this.bytes = bytes;
    }

    /**
     * 获取表示指定字节数的 {@link DataSize}.
     * @param bytes 字节数，正数或负数
     * @return {@link DataSize}
     */
    public static DataSize ofBytes(long bytes) {
        return new DataSize(bytes);
    }

    /**
     * 获取表示指定千字节数的 {@link DataSize}.
     * @param kilobytes 千字节, 正数或负数
     * @return {@link DataSize}
     */
    public static DataSize ofKilobytes(long kilobytes) {
        return new DataSize(Math.multiplyExact(kilobytes, BYTES_PER_KB));
    }

    /**
     * 获取表示指定兆字节数的 {@link DataSize}.
     * @param megabytes 兆字节, 正数或负数
     * @return {@link DataSize}
     */
    public static DataSize ofMegabytes(long megabytes) {
        return new DataSize(Math.multiplyExact(megabytes, BYTES_PER_MB));
    }

    /**
     * 获取表示指定千兆字节数的 {@link DataSize}.
     * @param gigabytes 千兆字节, 正数或负数
     * @return {@link DataSize}
     */
    public static DataSize ofGigabytes(long gigabytes) {
        return new DataSize(Math.multiplyExact(gigabytes, BYTES_PER_GB));
    }

    /**
     * 获取表示指定太字节数的 {@link DataSize}.
     * @param terabytes 太字节, 正数或负数
     * @return {@link DataSize}
     */
    public static DataSize ofTerabytes(long terabytes) {
        return new DataSize(Math.multiplyExact(terabytes, BYTES_PER_TB));
    }

    /**
     * 获取表示指定拍字节数的 {@link DataSize}.
     * @param petabytes 拍字节, 正数或负数
     * @return {@link DataSize}
     */
    public static DataSize ofPetabyte(long petabytes) {
        return new DataSize(Math.multiplyExact(petabytes, BYTES_PER_PB));
    }

    /**
     * 获取表示指定 {@link DataUnit} 中数量的 {@link DataSize}.
     * @param amount 大小, 以单位衡量, 正数或负数
     * @param unit 单位
     * @return 对应的 {@link DataSize}
     */
    public static DataSize of(long amount, DataUnit unit) {
        return new DataSize(Math.multiplyExact(amount, unit.size().toBytes()));
    }

    /**
     * 获取字节数的字符串表示形式 {@link DataSize}.
     * @param bytes 字节数，正数或负数
     * @return {@link DataSize}
     */
    public static String formatBytes(long bytes) {
        return ofBytes(bytes).toBytes() + " bytes";
    }

    /**
     * 获取大小的字符串表示形式 {@link DataSize}.
     * @param bytes 字节数，正数或负数
     * @return {@link DataSize}
     */
    public static String formatKiloBytes(long bytes) {
        return ofBytes(bytes).toKilobytes() + " KB";
    }

    /**
     * 获取大小的字符串表示形式 {@link DataSize}.
     * @param bytes 字节数，正数或负数
     * @return {@link DataSize}
     */
    public static String formatMegaBytes(long bytes) {
        return ofBytes(bytes).toMegabytes() + " MB";
    }

    /**
     * 获取大小的字符串表示形式 {@link DataSize}.
     * @param bytes 字节数，正数或负数
     * @return {@link DataSize}
     */
    public static String formatGigaBytes(long bytes) {
        return ofBytes(bytes).toGigabytes() + " GB";
    }

    /**
     * 获取大小的字符串表示形式 {@link DataSize}.
     * @param bytes 字节数，正数或负数
     * @return {@link DataSize}
     */
    public static String formatTeraBytes(long bytes) {
        return ofBytes(bytes).toTerabytes() + " TB";
    }

    /**
     * 获取大小的字符串表示形式 {@link DataSize}.
     * @param bytes 字节数，正数或负数
     * @return {@link DataSize}
     */
    public static String formatPetaBytes(long bytes) {
        return ofBytes(bytes).toPetabyte() + " PB";
    }

    /**
     * 获取可读性的文件大小表示. 如果大小超过 1GB，则返回 GB，即向下四舍五入到最接近 GB 的边界。MB，KB 同理。
     * <p>
     * 注意使用此方法的精度。
     * </p>
     * @param size 大小
     * @return 返回可读性值
     */
    public static String format(final long size) {
        return FileUtils.byteCountToDisplaySize(size);
    }

    /**
     * 获取可读性的文件大小表示. 如果大小超过 1GB，则返回 GB，即向下四舍五入到最接近 GB 的边界。MB，KB 同理。
     * <p>
     * 注意使用此方法的精度。
     * </p>
     * @param size 大小
     * @return 返回可读性值
     */
    public static String format(final BigInteger size) {
        return FileUtils.byteCountToDisplaySize(size);
    }

    /**
     * 如果未指定单位，则使用 {@link DataUnit#BYTES} 从文本中获取 {@link DataSize}.
     * <p>
     * 示例: <pre>
     * "12KB" -- 解析为 "12 kilobytes"
     * "5MB"  -- 解析为 "5 megabytes"
     * "20"   -- 解析为 "20 bytes"
     * </pre>
     * @param text 要解析的文本
     * @return {@link DataSize}
     * @see #parse(CharSequence, DataUnit)
     */
    public static DataSize parse(CharSequence text) {
        return parse(text, null);
    }

    /**
     * 如果未指定单位，则使用 {@link DataUnit#BYTES} 从文本中获取 {@link DataSize}.
     * <p>
     * 字符串以数字开头，支持 {@linkplain DataUnit suffixes} 匹配的后缀
     * </p>
     * <p>
     * 示例: <pre>
     * "12KB" -- 解析为 "12 kilobytes"
     * "5MB"  -- 解析为 "5 megabytes"
     * "20"   -- 解析为 "20 kilobytes" (当 {@code defaultUnit} 为 {@link DataUnit#KILOBYTES})
     * </pre>
     * @param text 要解析的文本
     * @param defaultUnit 默认单位
     * @return {@link DataSize}
     */
    public static DataSize parse(CharSequence text, DataUnit defaultUnit) {
        try {
            Matcher matcher = PATTERN.matcher(text);
            Assert.state(matcher.matches(), "Does not match data size pattern");
            DataUnit unit = determineDataUnit(matcher.group(2), defaultUnit);
            long amount = Long.parseLong(matcher.group(1));
            return DataSize.of(amount, unit);
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("'" + text + "' is not a valid data size", ex);
        }
    }

    private static DataUnit determineDataUnit(String suffix, DataUnit defaultUnit) {
        DataUnit defaultUnitToUse = (defaultUnit != null) ? defaultUnit : DataUnit.BYTES;
        return (StringUtils.isNotEmpty(suffix) ? DataUnit.fromSuffix(suffix) : defaultUnitToUse);
    }

    /**
     * 获取给定字符串单位.
     * @param text 给定字符串
     * @return 返回给定字符串的单位，如果为 {@code null}，则返回 {@link StringPools#EMPTY}
     */
    public static String dataUnit(CharSequence text) {
        if (StringUtils.isNotEmpty(text)) {
            Matcher matcher = PATTERN.matcher(text);
            return (matcher.matches()) ? matcher.group(2) : StringUtils.EMPTY;
        }
        return StringPools.EMPTY;
    }

    /**
     * 检查此大小是否为负数，不包括零.
     * @return {@code true} 大小小于 0
     */
    public boolean isNegative() {
        return this.bytes < 0;
    }

    /**
     * 返回此实例中的字节数.
     * @return 字节数
     */
    public long toBytes() {
        return this.bytes;
    }

    /**
     * 返回此实例中的千字节数.
     * @return 千字节数
     */
    public long toKilobytes() {
        return this.bytes / BYTES_PER_KB;
    }

    /**
     * 返回此实例中的兆字节数.
     * @return 兆字节数
     */
    public long toMegabytes() {
        return this.bytes / BYTES_PER_MB;
    }

    /**
     * 返回此实例中的千兆字节数.
     * @return 千兆字节数
     */
    public long toGigabytes() {
        return this.bytes / BYTES_PER_GB;
    }

    /**
     * 返回此实例中的太字节数.
     * @return 太字节数
     */
    public long toTerabytes() {
        return this.bytes / BYTES_PER_TB;
    }

    /**
     * 返回此实例中的拍字节数.
     * @return 拍字节数
     */
    public long toPetabyte() {
        return this.bytes / BYTES_PER_PB;
    }

    @Override
    public int compareTo(DataSize other) {
        return Long.compare(this.bytes, other.bytes);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        DataSize otherSize = (DataSize) other;
        return (this.bytes == otherSize.bytes);
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.bytes);
    }

    @Override
    public String toString() {
        return String.format("%dB", this.bytes);
    }

}
