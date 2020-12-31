package com.framework.utils.base;


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

	//---------------------------------------------------------------------
	// check 检查校验
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

	//---------------------------------------------------------------------
	// trim 去除空格
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
