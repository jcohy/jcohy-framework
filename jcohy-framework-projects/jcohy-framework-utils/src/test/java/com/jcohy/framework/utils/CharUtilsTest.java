package com.jcohy.framework.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/3/22:11:21
 * @since 2022.0.1
 */
public class CharUtilsTest {

    @Test
    void compare() {
        assertThat(CharUtils.asciiAlphaEquals('A', 'a')).isFalse();
        assertThat(CharUtils.asciiAlphaEquals('a', 'A')).isFalse();
        assertThat(CharUtils.asciiAlphaEquals('A', 'A')).isTrue();
        assertThat(CharUtils.asciiAlphaEquals('a', 'a')).isTrue();

        assertThat(CharUtils.asciiAlphaEqualsIgnoreCase('A', 'a')).isTrue();
        assertThat(CharUtils.asciiAlphaEqualsIgnoreCase('a', 'A')).isTrue();
        assertThat(CharUtils.asciiAlphaEqualsIgnoreCase('A', 'A')).isTrue();
        assertThat(CharUtils.asciiAlphaEqualsIgnoreCase('b', 'A')).isFalse();

    }

}
