package com.framework.utils.base;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import com.google.common.base.Ascii;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.checkerframework.checker.nullness.qual.Nullable;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.logging.Level.WARNING;


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

	private static final String[] EMPTY_STRING_ARRAY = {};

	//---------------------------------------------------------------------
	// check test 检查校验测试
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

	public static boolean validSurrogatePairAt(CharSequence string, int index) {
		return index >= 0
				&& index <= (string.length() - 2)
				&& Character.isHighSurrogate(string.charAt(index))
				&& Character.isLowSurrogate(string.charAt(index + 1));
	}

	/**
	 * 测试给定的 {@code String }字符串是否以给定的 {@code prefix} 前缀 开头，忽略大小写
	 * @param str 原始字符串
	 * @param prefix 要匹配的前缀
	 * @return {@code true} 以 {@code String} 以 {@code prefix} 开头
	 */
	public static boolean startsWithIgnoreCase(@Nullable String str, @Nullable String prefix) {
		return (str != null && prefix != null && str.length() >= prefix.length() &&
				str.regionMatches(true, 0, prefix, 0, prefix.length()));
	}

	/**
	 * 测试给定的 {@code String }字符串是否以给定的 {@code suffix} 后缀 开头，忽略大小写
	 * @param str  原始字符串
	 * @param suffix 要匹配的后缀
	 * @return {@code true} 以 {@code String} 以 {@code suffix} 结尾
	 * @see java.lang.String#endsWith
	 */
	public static boolean endsWithIgnoreCase(@Nullable String str, @Nullable String suffix) {
		return (str != null && suffix != null && str.length() >= suffix.length() &&
				str.regionMatches(true, str.length() - suffix.length(), suffix, 0, suffix.length()));
	}

	/**
	 * 测试指定字符串是否与指定索引处的子串相匹配
	 * @param str 原始给定字符串
	 * @param index 索引
	 * @param substring 子串
	 * @return {@code true} 匹配
	 */
	public static boolean substringMatch(CharSequence str,int index,CharSequence substring){
		if(index + substring.length() > str.length()){
			return false;
		}

		for(int i = 0; i < substring.length(); i++){
			if(str.charAt(index+i) != substring.charAt(i)){
				return false;
			}
		}
		return true;
	}



	//---------------------------------------------------------------------
	// calculation 计算
	//---------------------------------------------------------------------
	/**
	 * 计算子串在给定字符串中出现的次数
	 * @param str 给定字符串
	 * @param sub 子串
	 * @return 出现的次数
	 */
	public static int countOccurrencesOf(String str,String sub){
		if(isEmpty(str) || isEmpty(sub)){
			return 0;
		}

		int count = 0;
		int pos = 0;
		int idx;
		while((idx = str.indexOf(sub,pos)) != -1){
			++ count;
			pos = idx + sub.length();
		}
		return count;
	}


	//---------------------------------------------------------------------
	// trim 去除空格，指定字符
	//---------------------------------------------------------------------
	/**
	 * 删除给定 {@code String} 前面和后面的空白字符
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

	/**
	 * 删除字符串中的所有空白字符
	 * @param str 给定字符串
	 * @return {@code string} 删除后的字符串
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimAllWhitespace(String str){
		if(isEmpty(str)){
			return str;
		}

		int len = str.length();
		StringBuilder sb = new StringBuilder(len);
		for(int i = 0; i < len; i++){
			char c = str.charAt(i);
			if(!Character.isWhitespace(c)){
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 删除字符串前面的空白字符
	 * @param str 原始字符串
	 * @return {@code string} 删除后的字符串
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimLeadingWhitespace(String str){
		if(isEmpty(str)){
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}

	/**
	 * 删除字符串后面的空白字符
	 * @param str 原始字符串
	 * @return {@code string} 删除后的字符串
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimTrailingWhitespace(String str){
		if(isEmpty(str)){
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 删除字符串中以 {@code leadingCharacter} 开头的字符
	 * @param str 原始字符串
	 * @param leadingCharacter 要删除的字符
	 * @return 删除后的字符串
	 */
	public static String trimLeadingCharacter(String str, char leadingCharacter){
		if (isEmpty(str)) {
			return str;
		}

		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && sb.charAt(0) == leadingCharacter) {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}

	/**
	 * 删除字符串中以 {@code leadingCharacter} 结尾的字符
	 * @param str 原始字符串
	 * @param trailingCharacter 要删除的字符
	 * @return 删除后的字符串
	 */
	public static String trimTrailingCharacter(String str, char trailingCharacter){
		if (isEmpty(str)) {
			return str;
		}

		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && sb.charAt(sb.length() - 1) == trailingCharacter) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}


	//---------------------------------------------------------------------
	// transform 转换
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

	/**
	 * 首字母大写
	 * @param str 给定字符串
	 * @return {@code String} 首字母大写
	 */
	public static String toFirstUpperCase(String str){
		return changeFirstCharacterCase(str, true);
	}

	/**
	 * 首字母小写
	 * @param str 给定字符串
	 * @return {@code String} 首字母小写
	 */
	public static String toFirstLowerCase(String str){
		return changeFirstCharacterCase(str, false);
	}

	private static String changeFirstCharacterCase(String str, boolean capitalize) {
		if (isEmpty(str)) {
			return str;
		}

		char baseChar = str.charAt(0);
		char updatedChar;
		if (capitalize) {
			updatedChar = Character.toUpperCase(baseChar);
		}
		else {
			updatedChar = Character.toLowerCase(baseChar);
		}
		if (baseChar == updatedChar) {
			return str;
		}

		char[] chars = str.toCharArray();
		chars[0] = updatedChar;
		return new String(chars, 0, chars.length);
	}

	/**
	 * 如果给定的字符串不为 {@code null}，则返回字符串。否则，返回空字符串
	 *
	 * @param string 给定的字符串
	 * @return {@code string} 如果不为 null; {@code ""} 如果为 null
	 */
	public static String nullToEmpty(@Nullable String string) {
		return StringUtils.nullToEmpty(string);
	}

	/**
	 * 如果给定的字符串不为 nonempty，则返回字符串。否则，返回 {@code null}
	 *
	 * @param string 给定的字符串
	 * @return {@code string} 如果不为 nonempty; {@code null} 如果为 empty 或 null
	 */
	public static @Nullable String emptyToNull(@Nullable String string) {
		return StringUtils.emptyToNull(string);
	}



	//---------------------------------------------------------------------
	// truncate 截断
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
	 *   <li>它可能会拆分 Unicode 代理对（surrogate pair）
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

	/**
	 * 返回 {@code cs1} 和 {@code cs2} 最长的字符前缀。也即
	 * {@code a.toString().startsWith(prefix) && b.toString().startsWith(prefix)}
	 *
	 * <p>请注意不要拆分  Unicode 代理对（surrogate pair）
	 *
	 * @param cs1 字符序列1
	 * @param cs2 字符序列2
	 * @return 最长字符前缀,如果没有共同的前缀，则返回空字符串
	 */
	public static String commonPrefix(CharSequence cs1,CharSequence cs2){
		return Strings.commonPrefix(cs1,cs2);
	}

	/**
	 * 返回 {@code cs1} 和 {@code cs2} 最长的字符后缀。也即
	 * {@code a.toString().endsWith(prefix) && b.toString().endsWith(prefix)}
	 *
	 * <p>请注意不要拆分  Unicode 代理对（surrogate pair）
	 *
	 * @param cs1 字符序列1
	 * @param cs2 字符序列2
	 * @return 最长字符后缀,如果没有共同的后缀，则返回空字符串
	 */
	public static String commonSuffix(CharSequence cs1,CharSequence cs2){
		return Strings.commonSuffix(cs1,cs2);
	}



	//---------------------------------------------------------------------
	// format 格式化
	//---------------------------------------------------------------------
	/**
	 * 返回给定的  {@code template} 字符串，其中每次出现的  {@code "%s"}  被 {@code args} 中的相应参数值替换；或者，如果占位符和参数计数不匹配，
	 * 则返回该字符串的尽力而为形式。在正常情况下不会抛出异常。
	 *
	 * <p><b>Note:</b> 对于大多数字符串格式需求，请使用 {@link String#format String.format},
	 * {@link java.io.PrintWriter#format PrintWriter.format}, 这些支持全部 <a
	 * href="https://docs.oracle.com/javase/9/docs/api/java/util/Formatter.html#syntax">format
	 * specifiers</a>, 并且在错误是抛出 {@link java.util.IllegalFormatException} 异常来提醒你.
	 *
	 * <p>在某些情况下，例如输出调试信息或构造用于另一个未经检查的异常的消息，字符串格式化期间的异常除了取代你试图提供的真实信息外，几乎没有什么用处。这些就是这种方法适用的情况；
	 * 相反，它会生成带有所有提供的参数值的尽力而为字符串。例如，出于上述两个原因，{@link Preconditions} 类的方法实现使用此格式化程序。
	 * <p><b>Warning:</b> 仅识别 {@code "%s"} 占位符
	 *
	 *
	 * @param template 包含零个或多个 {@code "%s"} 占位符序列的字符串。 将 {@code null} 视为四个字符的字符串 {@code "null"}。
	 * @param args 要替换到消息模板中的参数。第一个参数替换模板中首次出现的  {@code "%s"}。依此类推。  {@code null}  参数将转换为四个字符的字符串 {@code "null"}; 考虑使用 {@code Arrays.toString()}作为数组参数
	 * 非 null 值使用 {@link Object#toString()} 转换为字符串。
	 * @return 返回格式化后的字符串
	 */
	public static String lenientFormat(
			@Nullable String template, @Nullable Object @Nullable ... args) {
		// null -> "null"
		template = String.valueOf(template);

		if (args == null) {
			args = new Object[] {"(Object[])null"};
		} else {
			for (int i = 0; i < args.length; i++) {
				args[i] = lenientToString(args[i]);
			}
		}

		// start substituting the arguments into the '%s' placeholders
		StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
		int templateStart = 0;
		int i = 0;
		while (i < args.length) {
			int placeholderStart = template.indexOf("%s", templateStart);
			if (placeholderStart == -1) {
				break;
			}
			builder.append(template, templateStart, placeholderStart);
			builder.append(args[i++]);
			templateStart = placeholderStart + 2;
		}
		builder.append(template, templateStart, template.length());

		// if we run out of placeholders, append the extra args in square braces
		if (i < args.length) {
			builder.append(" [");
			builder.append(args[i++]);
			while (i < args.length) {
				builder.append(", ");
				builder.append(args[i++]);
			}
			builder.append(']');
		}

		return builder.toString();
	}

	@Nullable
	public static String quote(@Nullable String str) {
		return (str != null ? "'" + str + "'" : null);
	}

	/**
	 * 删除以 . 作为分隔符的限定名称. 例如,
	 * "this.name.is.qualified", 返回 "qualified".
	 * @param qualifiedName 限定名称
	 * @return 删除 . 分割符限定后的名称
	 */
	public static String unqualify(String qualifiedName) {
		return unqualify(qualifiedName, '.');
	}

	/**
	 * 删除使用分隔符限制的字符串
	 * 如果使用 ':' 分隔符。"this:name:is:qualified" 返回 "qualified"
	 * @param qualifiedName 限定名称
	 * @param separator 分隔符
	 * @return 删除分隔符限定后的名称
	 */
	public static String unqualify(String qualifiedName, char separator) {
		return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
	}

	//---------------------------------------------------------------------
	// padding 填充
	//---------------------------------------------------------------------
	/**
	 * 返回一个长度至少为 {@code minLength} 的字符串，该字符串由  {@code string}  组成。如果长度不够，则使用 {@code padChar} 前缀填充
	 *
	 * <ul>
	 *   <li>{@code padStart("7", 3, '0')} returns {@code "007"}
	 *   <li>{@code padStart("2010", 3, '0')} returns {@code "2010"}
	 * </ul>
	 *
	 * <p>请参阅 {@link java.util.Formatter} 以获取更多的格式化功能
	 * @param string 显示在结果末尾的字符串
	 * @param minLength 结果字符串的最小长度。可以为零或负数，在这种情况下，始终返回输入字符串。
	 * @param padChar 在结果的开头插入字符，直到达到最小长度
	 * @return 填充字符串
	 */
	public static String padStart(String string, int minLength, char padChar) {
		checkNotNull(string);
		if (string.length() >= minLength) {
			return string;
		}
		return String.valueOf(padChar).repeat(minLength - string.length())
				+ string;
	}

	/**
	 * 返回一个长度至少为 {@code minLength} 的字符串，该字符串由  {@code string}  组成。如果长度不够，则使用 {@code padChar} 字符追加填充
	 *
	 * <ul>
	 *   <li>{@code padEnd("4.", 5, '0')} returns {@code "4.000"}
	 *   <li>{@code padEnd("2010", 3, '!')} returns {@code "2010"}
	 * </ul>
	 *
	 * <p>请参阅 {@link java.util.Formatter} 以获取更多的格式化功能
	 * @param string 显示在结果开头的字符串
	 * @param minLength 结果字符串的最小长度。可以为零或负数，在这种情况下，始终返回输入字符串。
	 * @param padChar 追加到结果末尾的字符，直到达到最小长度
	 * @return 填充字符串
	 */
	public static String padEnd(String string, int minLength, char padChar) {
		checkNotNull(string);
		if (string.length() >= minLength) {
			return string;
		}
		return string
				+ String.valueOf(padChar).repeat(minLength - string.length());
	}


	//---------------------------------------------------------------------
	// repeat 重复
	//---------------------------------------------------------------------
	/**
	 * 将指定字符串 {@code string} 重复 {@code count} 次返回。
	 *
	 * 例如, {@code repeat("hey", 3)} 将返回 {@code "heyheyhey"}.
	 *
	 * @param string 任何非空字符串
	 * @param count 重复的次数； 非负整数
	 * @return 将 {@code string} 重复 {@code count} 次数的字符串 (如果 {@code count}  为零，则为空字符串)
	 * @throws IllegalArgumentException 如果 {@code count} 非法
	 */
	public static String repeat(String string, int count) {
		checkNotNull(string);

		if (count <= 1) {
			checkArgument(count >= 0, "invalid count: %s", count);
			return (count == 0) ? "" : string;
		}

		// IF YOU MODIFY THE CODE HERE, you must update StringsRepeatBenchmark
		final int len = string.length();
		final long longSize = (long) len * (long) count;
		final int size = (int) longSize;
		if (size != longSize) {
			throw new ArrayIndexOutOfBoundsException("Required array size too large: " + longSize);
		}

		final char[] array = new char[size];
		string.getChars(0, len, array, 0);
		int n;
		for (n = len; n < size - n; n <<= 1) {
			System.arraycopy(array, 0, array, n, n);
		}
		System.arraycopy(array, 0, array, n, size - n);
		return new String(array);
	}


	//---------------------------------------------------------------------
	// replace 替换
	//---------------------------------------------------------------------
	/**
	 * 将一个字符串 {@code inString } 中出现的所有子字符串 {@code oldPattern } 替换为另一个子字符串 {@code newPattern }。
	 * @param inString {@code String} 原始字符串
	 * @param oldPattern {@code String} 替换的字符串
	 * @param newPattern {@code String} 要插入的字符串
	 * @return a {@code String} 返回替换后的字符串
	 */
	public static String replace(String inString, String oldPattern, @Nullable String newPattern) {
		if (isEmpty(inString) || isEmpty(oldPattern) || newPattern == null) {
			return inString;
		}

		int index = inString.indexOf(oldPattern);
		if( index == -1){
			return inString;
		}

		int capacity = inString.length();
		if(newPattern.length() > oldPattern.length()){
			capacity += 16;
		}

		StringBuilder sb = new StringBuilder(capacity);

		// 旧字符串中的 position
		int pos = 0;
		int patLen = oldPattern.length();
		while (index >= 0) {
			sb.append(inString, pos, index);
			sb.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}

		// append any characters to the right of a match
		sb.append(inString, pos, inString.length());
		return sb.toString();
	}

	/**
	 * 删除所有的子串
	 * @param inString 原始字符串
	 * @param pattern 待删除的子串
	 * @return 删除子串后的字符串
	 */
	public static String delete(String inString, String pattern) {
		return replace(inString, pattern, "");
	}

	/**
	 * 删除给定字符串中的任何字符
	 * @param inString 原始字符串
	 * @param charsToDelete 一组要删除的字符。例如. "az\n" 将会 'a's, 'z's 和换行.
	 * @return 结果字符串
	 */
	public static String deleteAny(String inString, @Nullable String charsToDelete){
		if (isEmpty(inString) || isEmpty(charsToDelete)) {
			return inString;
		}

		int lastCharIndex = 0;
		char[] result = new char[inString.length()];
		for (int i = 0; i < inString.length(); i++) {
			char c = inString.charAt(i);
			if (charsToDelete.indexOf(c) == -1) {
				result[lastCharIndex++] = c;
			}
		}
		if (lastCharIndex == inString.length()) {
			return inString;
		}
		return new String(result, 0, lastCharIndex);
	}



	//---------------------------------------------------------------------
	// 使用 String 数组的便捷方法
	//---------------------------------------------------------------------
	/**
	 * 将 {@link Collection} 转换为 String 数组
	 * <p>集合只能包含 String 元素。</p>
	 *
	 * @param collection 要复制的 {@code Collection}（可能为  {@code null} 或 为空）
	 * @return the resulting {@code String} array
	 */
	public static String[] toStringArray(@Nullable Collection<String> collection) {
		return (!CollectionUtils.isEmpty(collection) ? collection.toArray(EMPTY_STRING_ARRAY) : EMPTY_STRING_ARRAY);
	}

	/**
	 * 将给定的 {@link Enumeration} 复制到 {@code String} 数组中。
	 * <p> 枚举必须仅包含 {@code String} 元素。
	 * @param enumeration 要复制的 {@code Enumeration}（可能为  {@code null} 或 为空）
	 * @return the resulting {@code String} array
	 */
	public static String[] toStringArray(@Nullable Enumeration<String> enumeration) {
		return (enumeration != null ? toStringArray(Collections.list(enumeration)) : EMPTY_STRING_ARRAY);
	}

	/**
	 * 将指定字符串添加到指定字符串数组中
	 * @param array 指定字符串数组 (可以为  {@code null})
	 * @param str 指定字符串
	 * @return 新的数组 (不可能为 {@code null})
	 */
	public static String[] addStringToArray(@Nullable String[] array,String str){
		if(ObjectUtils.isEmpty(array)){
			return new String[]{str};
		}

		String[] newArr = new String[array.length + 1];
		System.arraycopy(array,0,newArr,0,array.length);
		newArr[array.length] = str;
		return newArr;
	}


	/**
	 * 将给定的 {@code String} 数组连接为一个，其中重叠的数组元素包含两次。
	 * 保留原始数组中元素的顺序。
	 * @param array1 第一个数组（可以为 {@code null}）
	 * @param array2 第二个数组（可以为 {@code null}）
	 * @return 新数组（如果两个给定数组均为 {@code null}，则为 {@code null}）
	 */
	@Nullable
	public static String[] concatenateStringArrays(@Nullable String[] array1, @Nullable String[] array2) {
		if (ObjectUtils.isEmpty(array1)) {
			return array2;
		}
		if (ObjectUtils.isEmpty(array2)) {
			return array1;
		}

		String[] newArr = new String[array1.length + array2.length];
		System.arraycopy(array1, 0, newArr, 0, array1.length);
		System.arraycopy(array2, 0, newArr, array1.length, array2.length);
		return newArr;
	}

	/**
	 * 对 {@code String} 数组进行排序
	 * @param array 原始数组 (potentially empty)
	 * @return 排序后的数组 (不为 {@code null})
	 */
	public static String[] sortStringArray(String[] array) {
		if (ObjectUtils.isEmpty(array)) {
			return array;
		}

		Arrays.sort(array);
		return array;
	}

	/**
	 * 对 String 数组中每个 non-null 元素执行 {@code String.trim()}
	 * @param array 原始的 {@code String} 数组
	 * @return 结果数组
	 */
	public static String[] trimArrayElements(String[] array) {
		if (ObjectUtils.isEmpty(array)) {
			return array;
		}

		String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			String element = array[i];
			result[i] = (element != null ? element.trim() : null);
		}
		return result;
	}

	/**
	 * 删除重复的字符串
	 * @param array 原始数组
	 * @return 没有重复的元素
	 */
	public static String[] removeDuplicateStrings(String[] array) {
		if (ObjectUtils.isEmpty(array)) {
			return array;
		}

		Set<String> set = new LinkedHashSet<>(Arrays.asList(array));
		return toStringArray(set);
	}

	/**
	 * 拆分字符串，从第一次出现 {@code delimiter} 开始，结果中不包含 {@code delimiter}
	 *
	 * @param toSplit 要分割的字符串（可能为 {@code null} 或 为空）
	 * @param delimiter 分隔符 (可能为 {@code null} 或 为空)
	 * @return 一个两个元素的数组，其中索引 0 在分隔符之前，索引 1 在分隔符之后（两个元素都不包括分隔符）;
	 * 如果在给定的 {@code String} 中找不到分隔符，则返回 {@code null}
	 */
	@Nullable
	public static String[] split(@Nullable String toSplit, @Nullable String delimiter) {
		if (isEmpty(toSplit) || isEmpty(delimiter)) {
			return null;
		}
		int offset = toSplit.indexOf(delimiter);
		if (offset < 0) {
			return null;
		}

		String beforeDelimiter = toSplit.substring(0, offset);
		String afterDelimiter = toSplit.substring(offset + delimiter.length());
		return new String[] {beforeDelimiter, afterDelimiter};
	}

	/**
	 * 将字符串按照给定的分隔符进行分割。然后生成一个 {@code Properties} 实例。其中，分隔符左侧作为 key，右边作为 value
	 * A {@code Properties} instance is then generated, with the left of the delimiter
	 * providing the key, and the right of the delimiter providing the value.
	 * <p>在将 key 和 value 添加到 {@code Properties} 前会先对其进行 trim .
	 * @param array 要处理的数组
	 * @param delimiter 分隔符 (通常使用等号)
	 * @return 一个表示数组内容的  {@code Properties} 实例；如果要处理的数组为 {@code null} 或为空，则为 {@code null}
	 */
	@Nullable
	public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter) {
		return splitArrayElementsIntoProperties(array, delimiter, null);
	}

	/**
	 * 将字符串按照给定的分隔符进行分割。然后生成一个 {@code Properties} 实例。其中，分隔符左侧作为 key，右边作为 value
	 * A {@code Properties} instance is then generated, with the left of the delimiter
	 * providing the key, and the right of the delimiter providing the value.
	 * <p>在将 key 和 value 添加到 {@code Properties} 前会先对其进行 trim .
	 * @param array 要处理的数组
	 * @param delimiter 分隔符 (通常使用等号)
	 * @param charsToDelete 一个或多个字符，在尝试进行分割操作之前，应从每个元素中删除（通常是引号）；如果不应该进行删除，则为  {@code null}
	 * @return 一个表示数组内容的  {@code Properties} 实例；如果要处理的数组为 {@code null} 或为空，则为 {@code null}
	 */
	@Nullable
	public static Properties splitArrayElementsIntoProperties(
			String[] array, String delimiter, @Nullable String charsToDelete) {

		if (ObjectUtils.isEmpty(array)) {
			return null;
		}

		Properties result = new Properties();
		for (String element : array) {
			if (charsToDelete != null) {
				element = deleteAny(element, charsToDelete);
			}
			String[] splittedElement = split(element, delimiter);
			if (splittedElement == null) {
				continue;
			}
			result.setProperty(splittedElement[0].trim(), splittedElement[1].trim());
		}
		return result;
	}

	/**
	 * 通过 {@link StringTokenizer} 将 {@code String} 标记为 {@code String} 数组
	 *
	 * <p>Trims tokens 和 忽略空的 tokens.
	 * <p> {@code delimiters} 分隔符字符串可以包含任意数量的分隔字符. 这些字符中的每一个都可以用于分隔标记.
	 * 分隔符始终是单个字符，对于多字符分隔符 请考虑使用 {@link #delimitedListToStringArray}.
	 * @param str 要标记的 {@code String}(可能为 {@code null} 或 empty)
	 * @param delimiters 分隔符，组合为一个 {@code String} (每个字符都被单独视为分隔符)
	 * @return tokens 数组
	 * @see java.util.StringTokenizer
	 * @see String#trim()
	 * @see #delimitedListToStringArray
	 */
	public static String[] tokenizeToStringArray(@Nullable String str, String delimiters) {
		return tokenizeToStringArray(str, delimiters, true, true);
	}

	/**
	 * 通过 {@link StringTokenizer} 将 {@code String} 标记为 {@code String} 数组
	 * <p> {@code delimiters} 分隔符字符串可以包含任意数量的分隔字符. 这些字符中的每一个都可以用于分隔标记.
	 * 分隔符始终是单个字符，对于多字符分隔符 请考虑使用 {@link #delimitedListToStringArray}.
	 * @param str 要标记的 {@code String}(可能为 {@code null} 或 empty)
	 * @param delimiters 分隔符，组合为一个 {@code String} (每个字符都被单独视为分隔符)
	 * @param trimTokens 删除空格 {@link String#trim()}
	 * @param ignoreEmptyTokens 从结果数组中省略空 tokens （仅适用于 trim 后为空的标记； StringTokenizer 首先不会将后续的分隔符视为 tokens）。
	 * @return tokens 数组
	 * @see java.util.StringTokenizer
	 * @see String#trim()
	 * @see #delimitedListToStringArray
	 */
	public static String[] tokenizeToStringArray(
			@Nullable String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

		if (str == null) {
			return EMPTY_STRING_ARRAY;
		}

		StringTokenizer st = new StringTokenizer(str, delimiters);
		List<String> tokens = new ArrayList<>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || token.length() > 0) {
				tokens.add(token);
			}
		}
		return toStringArray(tokens);
	}

	/**
	 * 字符串 {@code String} 是一个分隔符分割的列表，将其转换为 {@code String}  数组
	 * <p>一个 {@code delimiter} 可能包含多个字符,但与 {@link #tokenizeToStringArray} 相比，它仍将被视为单个分隔符字符串，而不是一堆单独的分隔符.
	 * @param str 输入字符串 {@code String} (可能为 {@code null} 或 empty)
	 * @param delimiter 元素之间的分隔符（这是单个分隔符，而不是一堆单独的分隔符）
	 * @return 列表中的 tokens 数组
	 * @see #tokenizeToStringArray
	 */
	public static String[] delimitedListToStringArray(@Nullable String str, @Nullable String delimiter) {
		return delimitedListToStringArray(str, delimiter, null);
	}

	/**
	 * 字符串 {@code String} 是一个分隔符分割的列表，将其转换为 {@code String}  数组
	 * <p>一个 {@code delimiter} 可能包含多个字符,但与 {@link #tokenizeToStringArray} 相比，它仍将被视为单个分隔符字符串，而不是一堆单独的分隔符.
	 * @param str 输入字符串 {@code String} (可能为 {@code null} 或 empty)
	 * @param delimiter 元素之间的分隔符（这是单个分隔符，而不是一堆单独的分隔符）
	 * @param charsToDelete 一组要删除的字符； 对于删除不需要的换行符很有用：例如 "\r\n\f" 将删除字符串中的所有新行和换行符
	 * @return 列表中的 tokens 数组
	 * @see #tokenizeToStringArray
	 */
	public static String[] delimitedListToStringArray(
			@Nullable String str, @Nullable String delimiter, @Nullable String charsToDelete) {

		if (str == null) {
			return EMPTY_STRING_ARRAY;
		}
		if (delimiter == null) {
			return new String[] {str};
		}

		List<String> result = new ArrayList<>();
		if (delimiter.isEmpty()) {
			for (int i = 0; i < str.length(); i++) {
				result.add(deleteAny(str.substring(i, i + 1), charsToDelete));
			}
		}
		else {
			int pos = 0;
			int delPos;
			while ((delPos = str.indexOf(delimiter, pos)) != -1) {
				result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
				pos = delPos + delimiter.length();
			}
			if (str.length() > 0 && pos <= str.length()) {
				// Add rest of String, but not in case of empty input.
				result.add(deleteAny(str.substring(pos), charsToDelete));
			}
		}
		return toStringArray(result);
	}

	/**
	 * 将以逗号分隔的字符串列表 (例如，CSV 文件中的每一行) 转换为 String 数组
	 * @param str 输入字符串 {@code String}  (可能为 {@code null} 或 empty)
	 * @return 字符串数组，输入为空的情况下为空数组
	 */
	public static String[] commaDelimitedListToStringArray(@Nullable String str) {
		return delimitedListToStringArray(str, ",");
	}

	/**
	 * 将以逗号分隔的字符串列表 (例如，CSV 文件中的每一行) 转换为集合。
	 * <p>注意，此方法不会重复。并且返回集中的元素将保留 {@link LinkedHashSet} 中的原始顺序。
	 * @param str 输入字符串 {@code String}  (可能为 {@code null} 或 empty)
	 * @return 字符串集合
	 * @see #removeDuplicateStrings(String[])
	 */
	public static Set<String> commaDelimitedListToSet(@Nullable String str) {
		String[] tokens = commaDelimitedListToStringArray(str);
		return new LinkedHashSet<>(Arrays.asList(tokens));
	}

	/**
	 * 将 {@link Collection} 转换为指定分隔符形式的字符串，
	 * <p>这对  {@code toString()} 方法很有用
	 * @param coll 待转换的 {@code Collection} (可能为 {@code null} 或 empty)
	 * @param delim 分隔符 (通常为  ",")
	 * @param prefix  每个元素的开头 {@code String}
	 * @param suffix 每个元素的结尾 {@code String}
	 * @return 分隔后的字符串 {@code String}
	 */
	public static String collectionToDelimitedString(
			@Nullable Collection<?> coll, String delim, String prefix, String suffix) {

		if (CollectionUtils.isEmpty(coll)) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		Iterator<?> it = coll.iterator();
		while (it.hasNext()) {
			sb.append(prefix).append(it.next()).append(suffix);
			if (it.hasNext()) {
				sb.append(delim);
			}
		}
		return sb.toString();
	}

	/**
	 * 将 {@link Collection} 转换为指定分隔符形式的字符串，
	 * <p>这对  {@code toString()} 方法很有用
	 * @param coll 待转换的 {@code Collection} (可能为 {@code null} 或 empty)
	 * @param delim 分隔符 (通常为  ",")
	 * @return 分隔后的字符串 {@code String}
	 */
	public static String collectionToDelimitedString(@Nullable Collection<?> coll, String delim) {
		return collectionToDelimitedString(coll, delim, "", "");
	}

	/**
	 * 将 {@link Collection} 转换为指定分隔符形式的字符串，
	 * <p>这对  {@code toString()} 方法很有用
	 * @param coll 待转换的 {@code Collection} (可能为 {@code null} 或 empty)
	 * @return 分隔后的字符串 {@code String}
	 */
	public static String collectionToCommaDelimitedString(@Nullable Collection<?> coll) {
		return collectionToDelimitedString(coll, ",");
	}

	/**
	 * 将 {@code String} 数组转换为指定分隔符形式的 String (e.g. CSV).。
	 * @param arr 要转化的数组 (可能为 {@code null} 或 empty)
	 * @param delim 分隔符 (通常为 ",")
	 * @return 分隔后的字符串 {@code String}
	 */
	public static String arrayToDelimitedString(@Nullable Object[] arr, String delim) {
		if (ObjectUtils.isEmpty(arr)) {
			return "";
		}
		if (arr.length == 1) {
			return ObjectUtils.nullSafeToString(arr[0]);
		}

		StringJoiner sj = new StringJoiner(delim);
		for (Object o : arr) {
			sj.add(String.valueOf(o));
		}
		return sj.toString();
	}

	/**
	 * 将 {@code String} 数组转换为指定分隔符形式的 String (e.g. CSV).。
	 * @param arr 要转化的数组 (可能为 {@code null} 或 empty)
	 * @return 分隔后的字符串 {@code String}
	 */
	public static String arrayToCommaDelimitedString(@Nullable Object[] arr) {
		return arrayToDelimitedString(arr, ",");
	}

	//---------------------------------------------------------------------
	// private 私有方法
	//---------------------------------------------------------------------
	private static String lenientToString(@Nullable Object o) {
		if (o == null) {
			return "null";
		}
		try {
			return o.toString();
		} catch (Exception e) {
			// Default toString() behavior - see Object.toString()
			String objectToString =
					o.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(o));
			// Logger is created inline with fixed name to avoid forcing Proguard to create another class.
			Logger.getLogger("com.google.common.base.Strings")
					.log(WARNING, "Exception during lenientFormat for " + objectToString, e);
			return "<" + objectToString + " threw " + e.getClass().getName() + ">";
		}
	}
}
