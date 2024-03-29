package com.jcohy.framework.utils.unit;

import java.math.BigInteger;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/10/22:15:44
 * @since 2022.0.1
 */
public class DataSizeTest {

    @Test
    void ofBytesToBytes() {
        assertThat(DataSize.ofBytes(1024).toBytes()).isEqualTo(1024);
    }

    @Test
    void ofBytesToKilobytes() {
        assertThat(DataSize.ofBytes(1024).toKilobytes()).isEqualTo(1);
    }

    @Test
    void ofKilobytesToKilobytes() {
        assertThat(DataSize.ofKilobytes(1024).toKilobytes()).isEqualTo(1024);
    }

    @Test
    void ofKilobytesToMegabytes() {
        assertThat(DataSize.ofKilobytes(1024).toMegabytes()).isEqualTo(1);
    }

    @Test
    void ofMegabytesToMegabytes() {
        assertThat(DataSize.ofMegabytes(1024).toMegabytes()).isEqualTo(1024);
    }

    @Test
    void ofMegabytesToGigabytes() {
        assertThat(DataSize.ofMegabytes(1024).toGigabytes()).isEqualTo(1);
    }

    @Test
    void ofGigabytesToGigabytes() {
        assertThat(DataSize.ofGigabytes(4096).toGigabytes()).isEqualTo(4096);
    }

    @Test
    void ofGigabytesToTerabytes() {
        assertThat(DataSize.ofGigabytes(4096).toTerabytes()).isEqualTo(4);
    }

    @Test
    void ofTerabytesToTerabytes() {
        assertThat(DataSize.ofTerabytes(1024).toTerabytes()).isEqualTo(1024);
    }

    @Test
    void ofTerabytesToPetabytes() {
        assertThat(DataSize.ofTerabytes(1024).toPetabyte()).isEqualTo(1);
    }

    @Test
    void ofPetabytesToPetabytes() {
        assertThat(DataSize.ofPetabyte(1024).toPetabyte()).isEqualTo(1024);
    }

    @Test
    void ofWithByteUnit() {
        assertThat(DataSize.of(10, DataUnit.BYTES)).isEqualTo(DataSize.ofBytes(10));
    }

    @Test
    void ofWithKiloByteUnit() {
        assertThat(DataSize.of(20, DataUnit.KILOBYTES)).isEqualTo(DataSize.ofKilobytes(20));
    }

    @Test
    void ofWithMegaByteUnit() {
        assertThat(DataSize.of(30, DataUnit.MEGABYTES)).isEqualTo(DataSize.ofMegabytes(30));
    }

    @Test
    void ofWithGigaByteUnit() {
        assertThat(DataSize.of(40, DataUnit.GIGABYTES)).isEqualTo(DataSize.ofGigabytes(40));
    }

    @Test
    void ofWithTeraByteUnit() {
        assertThat(DataSize.of(50, DataUnit.TERABYTES)).isEqualTo(DataSize.ofTerabytes(50));
    }

    @Test
    void ofWithPetaByteUnit() {
        assertThat(DataSize.of(60, DataUnit.PETABYTES)).isEqualTo(DataSize.ofPetabyte(60));
    }

    @Test
    void formatWithBytes() {
        assertThat(DataSize.formatBytes(1024)).isEqualTo("1024 bytes");
    }

    @Test
    void formatWithKiloBytes() {
        assertThat(DataSize.formatKiloBytes(1024)).isEqualTo("1 KB");
    }

    @Test
    void formatWithMegaBytes() {
        assertThat(DataSize.formatMegaBytes(1048576)).isEqualTo("1 MB");

    }

    @Test
    void formatWithGigaBytes() {
        assertThat(DataSize.formatGigaBytes(1073741824)).isEqualTo("1 GB");
    }

    @Test
    void formatWithTeraBytes() {
        assertThat(DataSize.formatTeraBytes(DataSize.ofTerabytes(1).toBytes())).isEqualTo("1 TB");
    }

    @Test
    void formatWithPetaBytes() {
        assertThat(DataSize.formatPetaBytes(DataSize.ofPetabyte(1).toBytes())).isEqualTo("1 PB");
        assertThat(FileUtils.byteCountToDisplaySize(Long.MAX_VALUE)).isEqualTo("7 EB");
    }

    @Test
    void formatWithBigIntegerSize() {
        final BigInteger b1023 = BigInteger.valueOf(1023);
        final BigInteger b1025 = BigInteger.valueOf(1025);
        final BigInteger KB1 = BigInteger.valueOf(1024);
        final BigInteger MB1 = KB1.multiply(KB1);
        final BigInteger GB1 = MB1.multiply(KB1);
        final BigInteger GB2 = GB1.add(GB1);
        final BigInteger TB1 = GB1.multiply(KB1);
        final BigInteger PB1 = TB1.multiply(KB1);
        final BigInteger EB1 = PB1.multiply(KB1);

        assertThat(DataSize.format(BigInteger.ZERO)).isEqualTo("0 bytes");
        assertThat(DataSize.format(BigInteger.ONE)).isEqualTo("1 bytes");
        assertThat(DataSize.format(b1023)).isEqualTo("1023 bytes");
        assertThat(DataSize.format(KB1)).isEqualTo("1 KB");
        assertThat(DataSize.format(b1025)).isEqualTo("1 KB");
        assertThat(DataSize.format(MB1.subtract(BigInteger.ONE))).isEqualTo("1023 KB");
        assertThat(DataSize.format(MB1)).isEqualTo("1 MB");
        assertThat(DataSize.format(MB1.add(BigInteger.ONE))).isEqualTo("1 MB");
        assertThat(DataSize.format(GB1.subtract(BigInteger.ONE))).isEqualTo("1023 MB");
        assertThat(DataSize.format(GB1)).isEqualTo("1 GB");
        assertThat(DataSize.format(GB1.add(BigInteger.ONE))).isEqualTo("1 GB");
        assertThat(DataSize.format(GB2)).isEqualTo("2 GB");
        assertThat(DataSize.format(GB2.subtract(BigInteger.ONE))).isEqualTo("1 GB");
        assertThat(DataSize.format(TB1)).isEqualTo("1 TB");
        assertThat(DataSize.format(PB1)).isEqualTo("1 PB");
        assertThat(DataSize.format(EB1)).isEqualTo("1 EB");
        assertThat(DataSize.format(Long.MAX_VALUE)).isEqualTo("7 EB");

        assertThat(DataSize.format(BigInteger.valueOf(Character.MAX_VALUE))).isEqualTo("63 KB");
        assertThat(DataSize.format(BigInteger.valueOf(Short.MAX_VALUE))).isEqualTo("31 KB");
        assertThat(DataSize.format(BigInteger.valueOf(Integer.MAX_VALUE))).isEqualTo("1 GB");
    }

    @Test
    void formatWithLongSize() {
        assertThat(DataSize.format(0)).isEqualTo("0 bytes");
        assertThat(DataSize.format(1)).isEqualTo("1 bytes");
        assertThat(DataSize.format(1023)).isEqualTo("1023 bytes");
        assertThat(DataSize.format(1024)).isEqualTo("1 KB");
        assertThat(DataSize.format(1025)).isEqualTo("1 KB");
        assertThat(DataSize.format(1024 * 1023)).isEqualTo("1023 KB");
        assertThat(DataSize.format(1024 * 1024)).isEqualTo("1 MB");
        assertThat(DataSize.format(1024 * 1025)).isEqualTo("1 MB");
        assertThat(DataSize.format(1024 * 1024 * 1023)).isEqualTo("1023 MB");
        assertThat(DataSize.format(1024 * 1024 * 1024)).isEqualTo("1 GB");
        assertThat(DataSize.format(1024 * 1024 * 1025)).isEqualTo("1 GB");
        assertThat(DataSize.format(1024L * 1024 * 1024 * 2)).isEqualTo("2 GB");
        assertThat(DataSize.format(1024L * 1024 * 1024 * 2 - 1)).isEqualTo("1 GB");
        assertThat(DataSize.format(1024L * 1024 * 1024 * 1024)).isEqualTo("1 TB");
        assertThat(DataSize.format(1024L * 1024 * 1024 * 1024 * 1024)).isEqualTo("1 PB");
        assertThat(DataSize.format(1024L * 1024 * 1024 * 1024 * 1024 * 1024)).isEqualTo("1 EB");
        assertThat(DataSize.format(Long.MAX_VALUE)).isEqualTo("7 EB");

        assertThat(DataSize.format(BigInteger.valueOf(Character.MAX_VALUE))).isEqualTo("63 KB");
        assertThat(DataSize.format(BigInteger.valueOf(Short.MAX_VALUE))).isEqualTo("31 KB");
        assertThat(DataSize.format(BigInteger.valueOf(Integer.MAX_VALUE))).isEqualTo("1 GB");
    }

    @Test
    void parseWithDefaultUnitUsesBytes() {
        assertThat(DataSize.parse("1024")).isEqualTo(DataSize.ofKilobytes(1));
    }

    @Test
    void parseNegativeNumberWithDefaultUnitUsesBytes() {
        assertThat(DataSize.parse("-1")).isEqualTo(DataSize.ofBytes(-1));
    }

    @Test
    void parseWithNullDefaultUnitUsesBytes() {
        assertThat(DataSize.parse("1024", null)).isEqualTo(DataSize.ofKilobytes(1));
    }

    @Test
    void parseNegativeNumberWithNullDefaultUnitUsesBytes() {
        assertThat(DataSize.parse("-1024", null)).isEqualTo(DataSize.ofKilobytes(-1));
    }

    @Test
    void parseWithCustomDefaultUnit() {
        assertThat(DataSize.parse("1", DataUnit.KILOBYTES)).isEqualTo(DataSize.ofKilobytes(1));
    }

    @Test
    void parseNegativeNumberWithCustomDefaultUnit() {
        assertThat(DataSize.parse("-1", DataUnit.KILOBYTES)).isEqualTo(DataSize.ofKilobytes(-1));
    }

    @Test
    void parseWithBytes() {
        assertThat(DataSize.parse("1024B")).isEqualTo(DataSize.ofKilobytes(1));
    }

    @Test
    void parseWithNegativeBytes() {
        assertThat(DataSize.parse("-1024B")).isEqualTo(DataSize.ofKilobytes(-1));
    }

    @Test
    void parseWithPositiveBytes() {
        assertThat(DataSize.parse("+1024B")).isEqualTo(DataSize.ofKilobytes(1));
    }

    @Test
    void parseWithKiloBytes() {
        assertThat(DataSize.parse("1KB")).isEqualTo(DataSize.ofBytes(1024));
    }

    @Test
    void parseWithNegativeKiloBytes() {
        assertThat(DataSize.parse("-1KB")).isEqualTo(DataSize.ofBytes(-1024));
    }

    @Test
    void parseWithMegaBytes() {
        assertThat(DataSize.parse("4MB")).isEqualTo(DataSize.ofMegabytes(4));
    }

    @Test
    void parseWithNegativeMegaBytes() {
        assertThat(DataSize.parse("-4MB")).isEqualTo(DataSize.ofMegabytes(-4));
    }

    @Test
    void parseWithGigaBytes() {
        assertThat(DataSize.parse("1GB")).isEqualTo(DataSize.ofMegabytes(1024));
    }

    @Test
    void parseWithNegativeGigaBytes() {
        assertThat(DataSize.parse("-1GB")).isEqualTo(DataSize.ofMegabytes(-1024));
    }

    @Test
    void parseWithTeraBytes() {
        assertThat(DataSize.parse("1TB")).isEqualTo(DataSize.ofGigabytes(1024));
    }

    @Test
    void parseWithNegativeTeraBytes() {
        assertThat(DataSize.parse("-1TB")).isEqualTo(DataSize.ofGigabytes(-1024));
    }

    @Test
    void parseWithPeraBytes() {
        assertThat(DataSize.parse("1PB")).isEqualTo(DataSize.ofTerabytes(1024));
    }

    @Test
    void parseWithNegativePeraBytes() {
        assertThat(DataSize.parse("-1PB")).isEqualTo(DataSize.ofTerabytes(-1024));
    }

    @Test
    void dataUnit() {
        assertThat(DataSize.dataUnit("1BB")).isEqualTo("BB");
        assertThat(DataSize.dataUnit("1B")).isEqualTo("B");
        assertThat(DataSize.dataUnit("1KB")).isEqualTo("KB");
        assertThat(DataSize.dataUnit("1MB")).isEqualTo("MB");
        assertThat(DataSize.dataUnit("1GB")).isEqualTo("GB");
        assertThat(DataSize.dataUnit("1TB")).isEqualTo("TB");
        assertThat(DataSize.dataUnit("1PB")).isEqualTo("PB");
    }

    @Test
    void dataUnitWithNegative() {
        assertThat(DataSize.dataUnit("1")).isEmpty();
        assertThat(DataSize.dataUnit(null)).isEmpty();
        assertThat(DataSize.dataUnit("1KMF")).isEmpty();
        assertThat(DataSize.dataUnit("--1MB")).isEmpty();
    }

    @Test
    void isNegativeWithPositive() {
        assertThat(DataSize.ofBytes(50).isNegative()).isFalse();
    }

    @Test
    void isNegativeWithZero() {
        assertThat(DataSize.ofBytes(0).isNegative()).isFalse();
    }

    @Test
    void isNegativeWithNegative() {
        assertThat(DataSize.ofBytes(-1).isNegative()).isTrue();
    }

    @Test
    void toStringWithUnit() {
        assertThat(DataSize.ofKilobytes(1).toString()).isEqualTo("1024B");
        assertThat(DataSize.ofMegabytes(1).toString()).isEqualTo("1048576B");

    }

    @Test
    void toStringWithNegativeBytes() {
        assertThat(DataSize.ofKilobytes(-1).toString()).isEqualTo("-1024B");
    }

    @Test
    void parseWithUnsupportedUnit() {
        assertThatIllegalArgumentException().isThrownBy(() -> DataSize.parse("3ZB"))
                .withMessage("'3ZB' is not a valid data size");
    }

}
