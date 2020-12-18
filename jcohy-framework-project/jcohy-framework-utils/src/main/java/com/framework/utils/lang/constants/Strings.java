package com.framework.utils.lang.constants;

import java.io.File;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;


/**
 * Copyright: Copyright (c) 2020 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description: String 字符串常量类
 *
 * @author jiac
 * @version 1.0.0 2020/12/18:15:39
 * @since 1.0.0
 */
public class Strings extends StringUtils {

	/**
	 * 常用字符串
	 */
	public static final String EMPTY = "";
	public static final String SPACE = " ";
	public static final String NULL = "null";
	public static final String FALSE = "false";
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	public static final String ZERO = "0";
	public static final String ONE = "1";
	public static final String TRUE = "true";
	public static final String NO = "no";
	public static final String YES = "yes";
	public static final String N = "n";
	public static final String Y = "y";
	public static final String OFF = "off";
	public static final String ON = "on";
	public static final String AND = "and";
	public static final String OR = "or";
	public static final String HTTPS = "https://";
	public static final String HTTP = "http://";
	public static final String GET = "get";
	public static final String POST = "post";
	public static final String DELETE = "delete";
	public static final String PUT = "put";
	public static final String UNKNOWN = "unknown";
	public static final String UNAUTHORIZED = "Unauthorized";
	public static final String FOLDER_SEPARATOR = "/";
	public static final String FILE_SEPARATOR = File.separator;

	/**
	 * jvm 常用属性
	 */
	public static final String USER_HOME = System.getProperty("user.dir");
	/**
	 * 换行符
	 * <p>On UNIX systems, it returns {@code "\n"}; on Microsoft
	 * Windows systems it returns {@code "\r\n"}.
	 */
	public static final String LINE_SEPARATOR =  System.lineSeparator();

	/**
	 * symbol 符号常量
	 */
	public static final String VBAR = "|";
	public static final String AMPERSAND = "&";
	public static final String LT = "<";
	public static final String GT = ">";
	public static final String CARET = "^";
	public static final String ASTERISK = "*";
	public static final String TILDA = "~";
	public static final String QUOTE = "\"";
	public static final String BACKSLASH = "\\";
	public static final String APOS = "'";
	public static final String RSQUO = "’";
	public static final String DOLLAR = "$";
	public static final String AT = "@";
	public static final String SLASH = "/";
	public static final String DOT = ".";
	public static final String DASH = "-";
	public static final String COMMA = ",";
	public static final String COLON = ":";
	public static final String HASH = "#";
	public static final String EQUALS = "=";
	public static final String PERCENT = "%";
	public static final String PLUS = "+";
	public static final String QUESTION_MARK = "?";
	public static final String EXCLAMATION_MARK = "!";
	public static final String SEMICOLON = ";";

	public static final String MIDDLE_BRACKET = "[]";
	public static final String START_MIDDLE_BRACKET = "[";
	public static final String END_MIDDLE_BRACKET = "]";

	public static final String BIG_BRACE = "{}";
	public static final String START_BIG_BRACE = "{";
	public static final String END_BIG_BRACE = "}";

	public static final String SMALL_BRACKET = "()";
	public static final String START_SMALL_BRACKET = "(";
	public static final String END_SMALL_BRACKET = ")";

	/**
	 * 常用转义符
	 */
	public static final String NEWLINE = "\n";
	public static final String RETURN = "\r";
	public static final String TAB = "\t";

	/**
	 * 常用编码
	 */
	public static final String UTF_8 = "UTF-8";
	public static final String GBK = "GBK";
	public static final String ISO_8859_1 = "ISO-8859-1";

	/**
	 * xml 转义
 	 */
	public static final String NBSP = "&#160;";
	/**
	 * 零宽空白
 	 */
	public static final String ZWSP = "&#8203;";


	//---------------------------------------------------------------------
	// check
	//---------------------------------------------------------------------
	/**
	 * <p>
	 * 检查 CharSequence 是否为空 ("") 或 null.
	 * </p>
	 *
	 * <pre>
	 * Strings.isEmpty(null)      = true
	 * Strings.isEmpty("")        = true
	 * Strings.isEmpty(" ")       = false
	 * Strings.isEmpty("bob")     = false
	 * Strings.isEmpty("  bob  ") = false
	 * </pre>
	 *
	 * @param cs CharSequence
	 * @return {@code true} CharSequence 为空 ("") 或 null.
	 */
	public static boolean isNotEmpty(@Nullable final CharSequence cs) {
		return isEmpty(cs);
	}

	/**
	 * 判断某字符串是否为空或长度为 0 或由空白符(whitespace) 构成
	 *
	 * <pre>
	 * Strings.isBlack(null)      = true
	 * Strings.isBlack("")        = true
	 * Strings.isBlack(" ")       = true
	 * Strings.isBlack("bob")     = false
	 * Strings.isBlack("  bob  ") = false
	 * </pre>
	 *
	 * @param str 给定 字符串
	 * @return {@code true} 字符串 为空 或 长度为 0 或 由空白符(whitespace) 构成
	 */
	public static boolean isBlack(@Nullable final String str) {
		return str == null || str.trim().isEmpty();
	}
}
