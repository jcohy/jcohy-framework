package com.framework.utils.lang;


import org.springframework.lang.Nullable;

/**
 * Copyright: Copyright (c) 2020 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description: String Utils
 *
 * @author jiac
 * @version 1.0.0 2020/12/17:12:08
 * @since 1.0.0
 */
public class StringUtils {

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
	 * @return {@code true} if the CharSequence is empty or null
	 */
	public static boolean isEmpty(@Nullable final CharSequence cs) {
		return cs == null || cs.length() == 0;
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
