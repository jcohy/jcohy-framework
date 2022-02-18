package com.jcohy.framework.utils;

/**
 * 描述: 字符串常量池.
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2/18/22:15:30
 * @since 2022.0.1
 */
public interface StringPools {

	/**
	 * EMPTY.
	 */
	String EMPTY = "";

	/**
	 * NULL.
	 */
	String NULL = "null";

	/**
	 * FALSE.
	 */
	String FALSE = "false";

	/**
	 * TRUE.
	 */
	String TRUE = "true";

	/**
	 * NO.
	 */
	String NO = "no";

	/**
	 * YES.
	 */
	String YES = "yes";

	/**
	 * N.
	 */
	String N = "n";

	/**
	 * Y.
	 */
	String Y = "y";

	/**
	 * OFF.
	 */
	String OFF = "off";

	/**
	 * ON.
	 */
	String ON = "on";

	/**
	 * AND.
	 */
	String AND = "and";

	/**
	 * OR.
	 */
	String OR = "or";

	/**
	 * AMPERSAND.
	 */
	String AMPERSAND = "&";

	/**
	 * DOLLAR.
	 */
	String DOLLAR = "$";

	/**
	 * AT.
	 */
	String AT = "@";

	/**
	 * ASTERISK.
	 */
	String ASTERISK = "*";

	/**
	 * SLASH.
	 */
	String SLASH = "/";

	/**
	 * DOUBLE_SLASH.
	 */
	String DOUBLE_SLASH = "#//";

	/**
	 * DOT.
	 */
	String DOT = ".";

	/**
	 * DASH.
	 */
	String DASH = "-";

	/**
	 * COMMA.
	 */
	String COMMA = ",";

	/**
	 * COLON.
	 */
	String COLON = ":";

	/**
	 * HASH.
	 */
	String HASH = "#";

	/**
	 * EQUALS.
	 */
	String EQUALS = "=";

	/**
	 * PERCENT.
	 */
	String PERCENT = "%";

	/**
	 * PIPE.
	 */
	String PIPE = "|";

	/**
	 * PLUS.
	 */
	String PLUS = "+";

	/**
	 * QUESTION_MARK.
	 */
	String QUESTION_MARK = "?";

	/**
	 * EXCLAMATION_MARK.
	 */
	String EXCLAMATION_MARK = "!";

	/**
	 * HAT.
	 */
	String HAT = "^";

	/**
	 * SEMICOLON.
	 */
	String SEMICOLON = ";";

	/**
	 * SINGLE_QUOTE.
	 */
	String SINGLE_QUOTE = "'";

	/**
	 * BACKTICK.
	 */
	String BACKTICK = "`";

	/**
	 * SPACE.
	 */
	String SPACE = " ";

	/**
	 * TILDA.
	 */
	String TILDA = "~";

	/**
	 * LEFT_CHEV.
	 */
	String LEFT_CHEV = "<";

	/**
	 * RIGHT_CHEV.
	 */
	String RIGHT_CHEV = ">";

	/**
	 * EMPTY_JSON.
	 */
	String EMPTY_JSON = "{}";

	/**
	 * LEFT_BRACE.
	 */
	String LEFT_BRACE = "{";

	/**
	 * RIGHT_BRACE.
	 */
	String RIGHT_BRACE = "}";

	/**
	 * LEFT_BRACKET.
	 */
	String LEFT_BRACKET = "(";

	/**
	 * RIGHT_BRACKET.
	 */
	String RIGHT_BRACKET = ")";

	/**
	 * LEFT_SQ_BRACKET.
	 */
	String LEFT_SQ_BRACKET = "[";

	/**
	 * RIGHT_SQ_BRACKET.
	 */
	String RIGHT_SQ_BRACKET = "]";

	/**
	 * LF.
	 */
	String LF = "\n";

	/**
	 * QUOTE.
	 */
	String QUOTE = "\"";

	/**
	 * CR.
	 */
	String CR = "\r";

	/**
	 * TAB.
	 */
	String TAB = "\t";

	/**
	 * UTF_8.
	 */
	String UTF_8 = "UTF-8";

	/**
	 * GBK.
	 */
	String GBK = "GBK";

	/**
	 * ISO_8859_1.
	 */
	String ISO_8859_1 = "ISO-8859-1";

	/**
	 * UNKNOWN.
	 */
	String UNKNOWN = "unknown";

	/**
	 * 下划线.
	 */
	String UNDERSCORE = "_";

	/**
	 * 1.
	 */
	String ONE = "1";

	/**
	 * 0.
	 */
	String ZERO = "0";

	/**
	 * -1.
	 */
	String MINUS_ONE = "-1";

	/**
	 * 接口应描述一种类型，从而必须拥有方法.
	 * @return string
	 */
	default String getEmpty() {
		return EMPTY;
	}

}
