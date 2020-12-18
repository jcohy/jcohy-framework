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
}
