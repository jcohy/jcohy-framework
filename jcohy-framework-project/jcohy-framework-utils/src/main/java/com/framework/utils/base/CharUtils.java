package com.framework.utils.base;

import com.google.common.base.Ascii;

/**
 * Copyright: Copyright (c) 2020 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 1.0.0 2020/12/29:10:45
 * @since 1.0.0
 */
public class CharUtils {

	/** A bit mask which selects the bit encoding ASCII character case. */
	private static final char CASE_MASK = 0x20;

	//---------------------------------------------------------------------
	// predicate
	//---------------------------------------------------------------------
	/**
	 * 判断给定字符 {@code c} 是否为  {@code 'a'} and {@code 'z'}  之间的 26 个小写 ASCII 字母字符之一。 所有其他（包括非 ASCII 字符）都返回  {@code false}.
	 * @param c 给定字符
	 * @return {@code true} 是小写字符
	 */
	public static boolean isLowerCase(char c){
		return Ascii.isLowerCase(c);
	}

	/**
	 * 判断给定字符 {@code c} 是否为  {@code 'A'} and {@code 'Z'}  之间的 26 个大写 ASCII 字母字符之一。 所有其他（包括非 ASCII 字符）都返回  {@code false}.
	 * @param c 给定字符
	 * @return {@code true} 是大写字符
	 */
	public static boolean isUpperCase(char c){
		return Ascii.isUpperCase(c);
	}


	//---------------------------------------------------------------------
	// transform
	//---------------------------------------------------------------------
	/**
	 * 如果参数是 {@linkplain #isUpperCase(char)} 大写 ASCII 字符，则返回等效的小写字符。否则返回参数。
	 * @param c c
	 * @return 返回等效的小写字符
	 */
	public static char toLowerCase(char c){
		return Ascii.toLowerCase(c);
	}

	/**
	 * 如果参数是 {@linkplain #isLowerCase(char)}  小写 ASCII 字符，则返回等效的大写字符。否则返回参数。
	 * @param c c
	 * @return 返回等效的大写字符
	 */
	public static char toUpperCase(char c){
		return Ascii.toUpperCase(c);
	}

	/**
	 * 无论大小写如何，均返回字符 {@code c} 的非负索引值。 即，'a'/'A' 返回 0，而 'z'/'Z' 返回25。
	 * 非字母字符返回的值等于或大于26。
	 * @param c 给定字符
	 * @return 返回索引值
	 */
	public static int getAlphaIndex(char c){
		return (char) ((c | CASE_MASK) - 'a');
	}
}
