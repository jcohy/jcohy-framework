package com.framework.utils.base;


import com.google.common.base.Ascii;
import org.checkerframework.checker.nullness.qual.Nullable;


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
	 * 检查 CharSequence 是否为空 ("") 或 null.
	 *
	 * <pre>
	 * Strings.isEmpty(null)      = true
	 * Strings.isEmpty("")        = true
	 * Strings.isEmpty(" ")       = false
	 * Strings.isEmpty("bob")     = false
	 * Strings.isEmpty("  bob  ") = false
	 * </pre>
	 *
	 * @param str CharSequence
	 * @return {@code true} if the CharSequence is empty or null
	 */
	public static boolean isEmpty(@Nullable final String str) {
		return (str == null || str.isEmpty());
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

	/**
	 * 与 {@link StringUtils#isEmpty(String)} 相反
	 * @param str 给定 字符串
	 * @return {@code false} 字符串 为空 或 长度为 0 或 由空白符(whitespace) 构成
	 */
	public static boolean isNotEmpty(@Nullable final String str){
		return !isEmpty(str);
	}

	/**
	 * 与 {@link StringUtils#isBlack(String)} 相反
	 * @param str 给定 字符串
	 * @return {@code false} 字符串 为空 或 长度为 0 或 由空白符(whitespace) 构成
	 */
	public static boolean isNotBlack(@Nullable final String str){
		return !isBlack(str);
	}

	/**
	 * 判断 {@code s1} 和 {@code s2} 是否相等，忽略大小写。
	 *
	 * <p>此方法比 {@link String#equalsIgnoreCase} 快的多。如果已知至少一个参数仅包含 ASCII 字符，则应优先使用此方法。
	 *
	 * <p> 但是请注意，此方法的行为并不总是与以下表达式相同：
	 *
	 * <ul>
	 *   <li>{@code string.toUpperCase().equals("UPPER CASE ASCII")}
	 *   <li>{@code string.toLowerCase().equals("lower case ascii")}
	 * </ul>
	 *
	 * <p> 由于某些非 ASCII 字符的大小写折叠（在  {@link String#equalsIgnoreCase} 中不会发生）。但是，在几乎所有使用 ASCII 字符串的情况下，
	 * 作者都可能希望此方法提供的行为，而不是 {@code toUpperCase()} 和 {@code toLowerCase()} 的微妙且有时令人惊讶的行为。
	 *
	 * @param s1 字符串1
	 * @param s2 字符串2
	 * @return {@code true} 如果两个字符序列相等
	 */
	public static boolean equalsIgnoreCase(CharSequence s1, CharSequence s2){
		return Ascii.equalsIgnoreCase(s1,s2);
	}

	//---------------------------------------------------------------------
	// trim
	//---------------------------------------------------------------------
	/**
	 * 去除给定 {@code String} 前面和后面的空白字符
	 * @param str 给定 字符串
	 * @return 去除空白符后的 {@code String}
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimWhitespace(String str){
		if(isEmpty(str)){
			return str;
		}

		int beginIndex = 0;
		int endIndex = str.length()-1;

		while(beginIndex <= endIndex && Character.isWhitespace(str.charAt(beginIndex))){
			beginIndex++;
		}

		while (endIndex > beginIndex && Character.isWhitespace(str.charAt(endIndex))){
			endIndex --;
		}
		return str.substring(beginIndex,endIndex+1);
	}

	//---------------------------------------------------------------------
	// transform
	//---------------------------------------------------------------------
	/**
	 * 返回输入字符序列的副本，其中所有大写 ASCII 字符均已转换为小写。 所有其他字符均被复制而没有修改。
	 * @param cs 给定字符序列
	 * @return 转换后的结果
	 */
	public static String toLowerCase(final CharSequence cs){
		return Ascii.toLowerCase(cs);
	}

	/**
	 * 返回输入字符串的副本，其中所有大写 ASCII 字符均已转换为小写。 所有其他字符均被复制而没有修改。
	 * @param str 给定字符串
	 * @return 转换后的结果
	 */
	public static String toLowerCase(final String str){
		return Ascii.toLowerCase(str);
	}

	/**
	 * 返回输入字符序列的副本，其中所有小写 ASCII 字符均已转换为大写。 所有其他字符均被复制而没有修改。
	 * @param cs 给定字符序列
	 * @return 转换后的结果
	 */
	public static String toUpperCase(final CharSequence cs){
		return Ascii.toUpperCase(cs);
	}

	/**
	 * 返回输入字符串的副本，其中所有小写 ASCII 字符均已转换为大写。 所有其他字符均被复制而没有修改。
	 * @param str 给定字符串
	 * @return 转换后的结果
	 */
	public static String toUpperCase(final String str){
		return Ascii.toUpperCase(str);
	}

	//---------------------------------------------------------------------
	// truncate
	//---------------------------------------------------------------------
	/**
	 * 将给定的字符序列截断为给定的最大长度。如果序列的长度大于 {@code maxLength}，则返回的字符串的长度为 {@code maxLength} 个字符，
	 * 并以给定的  {@code truncationIndicator} 结尾。否则，序列将以字符串形式返回，且内容不变。
	 * <p>Examples:
	 *
	 * <pre>{@code
	 * StringUtils.truncate("foobar", 7, "..."); // returns "foobar"
	 * StringUtils.truncate("foobar", 5, "..."); // returns "fo..."
	 * }</pre>
	 *
	 * <p><b>注意:</b> 此方法可以用于某些非 ASCII 文本，但与任意 Unicode 文本一起使用不安全。它主要用于与已知可以安全使用的文本（例如，全 ASCII 文本）和简单的调试文本一起使用。
	 * 使用此方法时，请考虑以下事项：
	 *
	 * <ul>
	 *   <li>它可能会拆分 surrogate pairs
	 *   <li>它可以分割字符并组合字符
	 *   <li>它不考虑单词边界
	 *   <li>如果要截断以显示给用户，则必须考虑其他因素
	 *   <li>适当的截断指示符可能取决于语言环境
	 *   <li>在截断指示器中使用非 ASCII 字符是安全的
	 * </ul>
	 *
	 * @throws IllegalArgumentException 如果 {@code maxLength} 小于 {@code truncationIndicator} 的长度
	 *
	 * @param seq 给定字符序列
	 * @param maxLength 最大长度
	 * @param truncationIndicator 截断指示器
	 * @return 返回结果
	 */
	public static String truncate(CharSequence seq, int maxLength, String truncationIndicator){
		return Ascii.truncate(seq,maxLength,truncationIndicator);
	}
}
