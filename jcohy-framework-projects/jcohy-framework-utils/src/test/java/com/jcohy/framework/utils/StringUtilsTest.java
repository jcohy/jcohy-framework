package com.jcohy.framework.utils;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2/18/22:15:43
 * @since 2022.0.1
 */
public class StringUtilsTest {


	@Test
	public void testBlank() {
		assertThat(StringUtils.isBlank(null)).isTrue();
		assertThat(StringUtils.isBlank("")).isTrue();
		assertThat(StringUtils.isBlank(" ")).isTrue();
		assertThat(StringUtils.isBlank("12345")).isFalse();
		assertThat(StringUtils.isBlank(" 12345 ")).isFalse();

		assertThat(StringUtils.isNotBlank(null)).isFalse();
		assertThat(StringUtils.isNotBlank("")).isFalse();
		assertThat(StringUtils.isNotBlank(" ")).isFalse();
		assertThat(StringUtils.isNotBlank("bob")).isTrue();
		assertThat(StringUtils.isNotBlank(" bob ")).isTrue();

		assertThat(StringUtils.isAnyBlank(null,"bob")).isTrue();
		assertThat(StringUtils.isAnyBlank(""," bob")).isTrue();
		assertThat(StringUtils.isAnyBlank(" ","bob ")).isTrue();
		assertThat(StringUtils.isAnyBlank(null,"bob","alice")).isTrue();
		assertThat(StringUtils.isAnyBlank("bob"," alice")).isFalse();
		assertThat(StringUtils.isAnyBlank(" bob ","alice")).isFalse();

		assertThat(StringUtils.isNoneBlank((CharSequence) null)).isFalse();
		assertThat(StringUtils.isNoneBlank("")).isFalse();
		assertThat(StringUtils.isNoneBlank(" ")).isFalse();
		assertThat(StringUtils.isNoneBlank(""," ")).isFalse();
		assertThat(StringUtils.isNoneBlank(" ","",null)).isFalse();
		assertThat(StringUtils.isNoneBlank(" bob ",null)).isFalse();
		assertThat(StringUtils.isNoneBlank(" bob "," ")).isFalse();
		assertThat(StringUtils.isNoneBlank(" bob ","")).isFalse();
		assertThat(StringUtils.isNoneBlank("bob","alice")).isTrue();

		assertThat(StringUtils.isAllBlank((CharSequence) null)).isTrue();
		assertThat(StringUtils.isAllBlank("",null)).isTrue();
		assertThat(StringUtils.isAllBlank(" ")).isTrue();
		assertThat(StringUtils.isAllBlank(" ","",null)).isTrue();
		assertThat(StringUtils.isAllBlank("bob","alice")).isFalse();
		assertThat(StringUtils.isAllBlank(" bob ","")).isFalse();
	}

	@Test
	public void testEmpty() {
		assertThat(StringUtils.isEmpty(null)).isTrue();
		assertThat(StringUtils.isEmpty("")).isTrue();
		assertThat(StringUtils.isEmpty(" ")).isFalse();
		assertThat(StringUtils.isEmpty("12345")).isFalse();
		assertThat(StringUtils.isEmpty(" 12345 ")).isFalse();

		assertThat(StringUtils.isNotEmpty(null)).isFalse();
		assertThat(StringUtils.isNotEmpty("")).isFalse();
		assertThat(StringUtils.isNotEmpty(" ")).isTrue();
		assertThat(StringUtils.isNotEmpty("bob")).isTrue();
		assertThat(StringUtils.isNotEmpty(" bob ")).isTrue();

		assertThat(StringUtils.isAnyEmpty(null,"bob")).isTrue();
		assertThat(StringUtils.isAnyEmpty(""," bob")).isTrue();
		assertThat(StringUtils.isAnyEmpty(" ","bob ")).isFalse();
		assertThat(StringUtils.isAnyEmpty(null,"bob","alice")).isTrue();
		assertThat(StringUtils.isAnyEmpty("bob"," alice")).isFalse();
		assertThat(StringUtils.isAnyEmpty(" bob ","alice")).isFalse();

		assertThat(StringUtils.isNoneEmpty((CharSequence) null)).isFalse();
		assertThat(StringUtils.isNoneEmpty("")).isFalse();
		assertThat(StringUtils.isNoneEmpty(" ")).isTrue();
		assertThat(StringUtils.isNoneEmpty(""," ")).isFalse();
		assertThat(StringUtils.isNoneEmpty(" ","",null)).isFalse();
		assertThat(StringUtils.isNoneEmpty(" bob ",null)).isFalse();
		assertThat(StringUtils.isNoneEmpty(" bob "," ")).isTrue();
		assertThat(StringUtils.isNoneEmpty(" bob ","")).isFalse();
		assertThat(StringUtils.isNoneEmpty("bob","alice")).isTrue();

		assertThat(StringUtils.isAllEmpty((CharSequence) null)).isTrue();
		assertThat(StringUtils.isAllEmpty("",null)).isTrue();
		assertThat(StringUtils.isAllEmpty(" ")).isFalse();
		assertThat(StringUtils.isAllEmpty(" ","",null)).isFalse();
		assertThat(StringUtils.isAllEmpty("bob","alice")).isFalse();
		assertThat(StringUtils.isAllEmpty(" bob ","")).isFalse();
	}

	@Test
	public void testNumeric() {
		assertThat(StringUtils.isNumeric("bob")).isFalse();
		assertThat(StringUtils.isNumeric(" bob1 23")).isFalse();
		assertThat(StringUtils.isNumeric(" 123")).isFalse();
		assertThat(StringUtils.isNumeric("123 ")).isFalse();
		assertThat(StringUtils.isNumeric(" 123 ")).isFalse();
		assertThat(StringUtils.isNumeric("123")).isTrue();
	}

	@Test
	public void testFormat() {
		String value = "I am Bob, I like Java";
		String message = "I am {}, I like {}";
		String message2 = "I am %s, I like %s";

		assertThat(StringUtils.format(message,"Bob","Java")).isEqualTo(value);
		assertThat(StringUtils.formatStr(message2,"Bob","Java")).isEqualTo(value);


		assertThat(StringUtils.formatTime(400)).isEqualTo("0.000ms");
		assertThat(StringUtils.formatTime(11000000)).isEqualTo("11.000ms");
		assertThat(StringUtils.formatTime(2000000100)).isEqualTo("2.000s");

	}
}