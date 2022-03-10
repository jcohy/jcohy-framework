package com.jcohy.framework.utils.unit;

/**
 * 描述: 标准 {@link DataSize} 的单位.
 *
 * <p>
 * 此类中的单位前缀为二进制前缀（Binary Prefix），代表着 2次幂，如下
 * </p>
 * <table border="1">
 * <caption>数据单位</caption>
 * <tr>
 * <th>Constant</th>
 * <th>Data Size</th>
 * <th>Power&nbsp;of&nbsp;2</th>
 * <th>Size in Bytes</th>
 * </tr>
 * <tr>
 * <td>{@link #BYTES}</td>
 * <td>1B</td>
 * <td>2^0</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td>{@link #KILOBYTES}</td>
 * <td>1KB</td>
 * <td>2^10</td>
 * <td>1,024</td>
 * </tr>
 * <tr>
 * <td>{@link #MEGABYTES}</td>
 * <td>1MB</td>
 * <td>2^20</td>
 * <td>1,048,576</td>
 * </tr>
 * <tr>
 * <td>{@link #GIGABYTES}</td>
 * <td>1GB</td>
 * <td>2^30</td>
 * <td>1,073,741,824</td>
 * </tr>
 * <tr>
 * <td>{@link #TERABYTES}</td>
 * <td>1TB</td>
 * <td>2^40</td>
 * <td>1,099,511,627,776</td>
 * </tr>
 * </table>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * @author jiac
 * @version 2022.0.1 3/10/22:11:57
 * @since 2022.0.1
 */
public enum DataUnit {

    /**
     * Bytes, 后缀为 {@code B}.
     */
    BYTES("B", DataSize.ofBytes(1)),

    /**
     * Kilobytes, 后缀为 {@code KB}.
     */
    KILOBYTES("KB", DataSize.ofKilobytes(1)),

    /**
     * Megabytes, 后缀为 {@code MB}.
     */
    MEGABYTES("MB", DataSize.ofMegabytes(1)),

    /**
     * Gigabytes, 后缀为 {@code GB}.
     */
    GIGABYTES("GB", DataSize.ofGigabytes(1)),

    /**
     * Terabytes, 后缀为 {@code TB}.
     */
    TERABYTES("TB", DataSize.ofTerabytes(1)),

    /**
     * Petabytes, 后缀为 {@code PB}.
     */
    PETABYTES("PB", DataSize.ofPetabyte(1));

    private final String suffix;

    private final DataSize size;

    DataUnit(String suffix, DataSize size) {
        this.suffix = suffix;
        this.size = size;
    }

    DataSize size() {
        return this.size;
    }

    /**
     * 返回与指定后缀匹配的 {@link DataUnit}.
     * @param suffix 标准后缀之一
     * @return 与指定后缀匹配的 {@link DataUnit}
     * @throws IllegalArgumentException 如果后缀与任何此枚举常量的后缀不匹配
     */
    public static DataUnit fromSuffix(String suffix) {
        for (DataUnit candidate : values()) {
            if (candidate.suffix.equals(suffix)) {
                return candidate;
            }
        }
        throw new IllegalArgumentException("Unknown data unit suffix '" + suffix + "'");
    }

}
