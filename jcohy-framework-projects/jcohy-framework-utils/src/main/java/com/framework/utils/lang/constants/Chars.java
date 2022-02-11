package com.framework.utils.lang.constants;

/**
 * Copyright: Copyright (c) 2022 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 2022.0.1 2022/1/18:16:44
 * @since 2022.0.1
 */
public class Chars {

	/** Carriage Return. */
	public static final char CR = '\r';

	/** Double Quote. */
	public static final char DQUOTE = '\"';

	/** Equals '='. */
	public static final char EQ = '=';

	/** Line Feed. */
	public static final char LF = '\n';

	/** NUL. */
	public static final char NUL = 0;

	/** Single Quote [']. */
	public static final char QUOTE = '\'';

	/** Space. */
	public static final char SPACE = ' ';

	/** Tab. */
	public static final char TAB = '\t';

	/**
	 * 将数字转换为大写的十六进制字符或无效的空字符。
	 *
	 * @param digit 数字 0 - 15
	 * @return 该数字的十六进制字符；如果无效，则为 '\0'
	 */
	public static char getUpperCaseHex(final int digit) {
		if (digit < 0 || digit >= 16) {
			return '\0';
		}
		return digit < 10 ? getNumericalDigit(digit) : getUpperCaseAlphaDigit(digit);
	}

	/**
	 * 将数字转换为小写的十六进制字符或无效的空字符。
	 *
	 * @param digit 数字 0 - 15
	 * @return 该数字的十六进制字符；如果无效，则为 '\0'
	 */
	public static char getLowerCaseHex(final int digit) {
		if (digit < 0 || digit >= 16) {
			return '\0';
		}
		return digit < 10 ? getNumericalDigit(digit) : getLowerCaseAlphaDigit(digit);
	}

	private static char getNumericalDigit(final int digit) {
		return (char) ('0' + digit);
	}

	private static char getUpperCaseAlphaDigit(final int digit) {
		return (char) ('A' + digit - 10);
	}

	private static char getLowerCaseAlphaDigit(final int digit) {
		return (char) ('a' + digit - 10);
	}
}
