package com.jcohy.framework.utils;

import java.util.regex.Pattern;

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

        assertThat(StringUtils.isAnyBlank(null, "bob")).isTrue();
        assertThat(StringUtils.isAnyBlank("", " bob")).isTrue();
        assertThat(StringUtils.isAnyBlank(" ", "bob ")).isTrue();
        assertThat(StringUtils.isAnyBlank(null, "bob", "alice")).isTrue();
        assertThat(StringUtils.isAnyBlank("bob", " alice")).isFalse();
        assertThat(StringUtils.isAnyBlank(" bob ", "alice")).isFalse();

        assertThat(StringUtils.isNoneBlank((CharSequence) null)).isFalse();
        assertThat(StringUtils.isNoneBlank("")).isFalse();
        assertThat(StringUtils.isNoneBlank(" ")).isFalse();
        assertThat(StringUtils.isNoneBlank("", " ")).isFalse();
        assertThat(StringUtils.isNoneBlank(" ", "", null)).isFalse();
        assertThat(StringUtils.isNoneBlank(" bob ", null)).isFalse();
        assertThat(StringUtils.isNoneBlank(" bob ", " ")).isFalse();
        assertThat(StringUtils.isNoneBlank(" bob ", "")).isFalse();
        assertThat(StringUtils.isNoneBlank("bob", "alice")).isTrue();

        assertThat(StringUtils.isAllBlank((CharSequence) null)).isTrue();
        assertThat(StringUtils.isAllBlank("", null)).isTrue();
        assertThat(StringUtils.isAllBlank(" ")).isTrue();
        assertThat(StringUtils.isAllBlank(" ", "", null)).isTrue();
        assertThat(StringUtils.isAllBlank("bob", "alice")).isFalse();
        assertThat(StringUtils.isAllBlank(" bob ", "")).isFalse();
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

		assertThat(StringUtils.isAnyEmpty((String)null)).isTrue();
		assertThat(StringUtils.isAnyEmpty((String[])null)).isFalse();
		assertThat(StringUtils.isAnyEmpty(null, "foo")).isTrue();
        assertThat(StringUtils.isAnyEmpty("", " bar")).isTrue();
        assertThat(StringUtils.isAnyEmpty("bob", "")).isTrue();
		assertThat(StringUtils.isAnyEmpty(" bob ", null)).isTrue();

		assertThat(StringUtils.isAnyEmpty(null, "bob", "alice")).isTrue();
        assertThat(StringUtils.isAnyEmpty("bob", " alice")).isFalse();
        assertThat(StringUtils.isAnyEmpty(" bob ", "alice")).isFalse();

        assertThat(StringUtils.isNoneEmpty((CharSequence) null)).isFalse();
        assertThat(StringUtils.isNoneEmpty("")).isFalse();
        assertThat(StringUtils.isNoneEmpty(" ")).isTrue();
        assertThat(StringUtils.isNoneEmpty("", " ")).isFalse();
        assertThat(StringUtils.isNoneEmpty(" ", "", null)).isFalse();
        assertThat(StringUtils.isNoneEmpty(" bob ", null)).isFalse();
        assertThat(StringUtils.isNoneEmpty(" bob ", " ")).isTrue();
        assertThat(StringUtils.isNoneEmpty(" bob ", "")).isFalse();
        assertThat(StringUtils.isNoneEmpty("bob", "alice")).isTrue();

        assertThat(StringUtils.isAllEmpty((CharSequence) null)).isTrue();
        assertThat(StringUtils.isAllEmpty("", null)).isTrue();
        assertThat(StringUtils.isAllEmpty(" ")).isFalse();
        assertThat(StringUtils.isAllEmpty(" ", "", null)).isFalse();
        assertThat(StringUtils.isAllEmpty("bob", "alice")).isFalse();
        assertThat(StringUtils.isAllEmpty(" bob ", "")).isFalse();
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

        assertThat(StringUtils.format(message, "Bob", "Java")).isEqualTo(value);
        assertThat(StringUtils.formatStr(message2, "Bob", "Java")).isEqualTo(value);

        assertThat(StringUtils.formatTime(400)).isEqualTo("0.000ms");
        assertThat(StringUtils.formatTime(11000000)).isEqualTo("11.000ms");
        assertThat(StringUtils.formatTime(2000000100)).isEqualTo("2.000s");

    }

    @Test
    public void testCharSplit() {
        // 通过 char 类型拆分
        String str1 = "/User/jcohy/idea";
        assertThat(StringUtils.splitPath(str1)).hasSize(3).contains("User", "jcohy", "idea");
        assertThat(StringUtils.splitPath(str1, 2)).hasSize(2).contains("User", "jcohy/idea");
        assertThat(StringUtils.splitPathToList(str1)).hasSize(3).contains("User", "jcohy", "idea");
        assertThat(StringUtils.splitPathToList(str1, 2)).hasSize(2).contains("User", "jcohy/idea");

        String str2 = "Hello World,  ,  I , am , Iron Man.";
        assertThat(StringUtils.split(str2, ',')).hasSize(4).doesNotContain("  I ");
        assertThat(StringUtils.split(str2, ',')).hasSize(5).contains("").doesNotContain("  I ");
    }

    @Test
    public void testStringSplit() {
        // 通过 char 类型拆分
        String str2 = "Hello World,  ,  I , am , Iron Man.";
        assertThat(StringUtils.split(str2, StringPools.COMMA)).hasSize(4).doesNotContain("  I ");
        assertThat(StringUtils.split(str2, StringPools.COMMA)).hasSize(5).contains("")
                .doesNotContain("  I ");
        assertThat(StringUtils.split(str2, StringPools.COMMA, 3)).hasSize(3).doesNotContain("  I ");
        assertThat(StringUtils.split(str2, StringPools.COMMA, 3)).hasSize(3).contains("")
                .doesNotContain("  I ");

        String str3 = "Hello World : ,,  I , am :, Iron Man.";
        assertThat(StringUtils.split(str3)).hasSize(4).contains("Hello World :", "I", "am :", "Iron Man.");
        assertThat(StringUtils.split(str3, StringPools.COLON)).hasSize(3).contains("Hello World ", " ,,  I , am ",
                ", Iron Man.");
    }

    @Test
    public void testSplitByLength() {

        // Whitespace
        String str = " Hello World, I am,  Iron Man. ";
        StringUtils.split(str, 1).forEach(System.out::println);
        assertThat(StringUtils.split(str, 1)).hasSize(1).contains("Hello World, I am,  Iron Man.");

        // 通过正则拆分
        Pattern pattern = Pattern.compile("Hello");
        assertThat(StringUtils.splitByReg(str, pattern, 2, true, true)).hasSize(1).contains("World, I am,  Iron Man.");

        // 通过长度拆分
        assertThat(StringUtils.splitByLength(str, 8)).hasSize(4).contains(" Hello W", "orld, I ", "am,  Iro",
                "n Man. ");
    }

    @Test
    public void testIndexOf() {
        String str = "sadadfgfgd";
        assertThat(StringUtils.indexOf(str, 'a', 2, 10)).isEqualTo(3);
        assertThat(StringUtils.indexOf(str, 'd', 3)).isEqualTo(4);
        assertThat(StringUtils.indexOf(str, 'f')).isEqualTo(5);

        String str1 = "asdfEe";
        assertThat(StringUtils.indexOf(str1, "e", 0)).isEqualTo(4);

        assertThat(StringUtils.indexOfIgnoreCase("", "", 0)).isEqualTo(0);
        assertThat(StringUtils.indexOfIgnoreCase("aabaabaa", "A", 0)).isEqualTo(0);
        assertThat(StringUtils.indexOfIgnoreCase("aabaabaa", "B", 0)).isEqualTo(2);
        assertThat(StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 0)).isEqualTo(1);
        assertThat(StringUtils.indexOfIgnoreCase("aabaabaa", "B", 3)).isEqualTo(5);
        assertThat(StringUtils.indexOfIgnoreCase("aabaabaa", "B", 9)).isEqualTo(-1);
        assertThat(StringUtils.indexOfIgnoreCase("aabaabaa", "B", -1)).isEqualTo(2);
        assertThat(StringUtils.indexOfIgnoreCase("aabaabaa", "", 2)).isEqualTo(2);
        assertThat(StringUtils.indexOfIgnoreCase("abc", "", 9)).isEqualTo(-1);

    }

    @Test
    public void testEquals() {
        assertThat(StringUtils.equals(null, null)).isTrue();
        assertThat(StringUtils.equals(null, "abc")).isFalse();
        assertThat(StringUtils.equals("abc", "null")).isFalse();
        assertThat(StringUtils.equals("abc", "abc")).isTrue();
        assertThat(StringUtils.equals("abc", "ABC")).isFalse();

        assertThat(StringUtils.equalsIgnoreCase(null, null)).isTrue();
        assertThat(StringUtils.equalsIgnoreCase(null, "abc")).isFalse();
        assertThat(StringUtils.equalsIgnoreCase("abc", "null")).isFalse();
        assertThat(StringUtils.equalsIgnoreCase("abc", "abc")).isTrue();
        assertThat(StringUtils.equalsIgnoreCase("abc", "ABC")).isTrue();

        assertThat(StringUtils.isSubEquals("ubidexiaobang", 5, "aaeXiaosssbang", 3, 4, true)).isTrue();
        assertThat(StringUtils.isSubEquals("ubidexiaobang", 5, "aaeXiaosssbang", 3, 4, false)).isFalse();
        assertThat(StringUtils.isSubEquals("ubidexiaobang", 5, "aaeXiaosssbang", 3, 5, true)).isFalse();
        assertThat(StringUtils.isSubEquals("ubidexiaobang", 5, "", 3, 5, true)).isFalse();
    }

    @Test
    public void testRemove() {
        String str = "Abcdefg";

        // Prefix
        assertThat(StringUtils.removeStart(null, "abc")).isEmpty();
        assertThat(StringUtils.removeStart(str, null)).isEmpty();
        assertThat(StringUtils.removeStart(str, "")).isEmpty();
        assertThat(StringUtils.removeStart(str, "Abc")).isEqualTo("defg");
        assertThat(StringUtils.removeStart(str, "efg")).isEqualTo(str);

        // Suffix
        assertThat(StringUtils.removeEnd(null, "efg")).isEmpty();
        assertThat(StringUtils.removeEnd(str, null)).isEmpty();
        assertThat(StringUtils.removeEnd(str, "")).isEmpty();
        assertThat(StringUtils.removeEnd(str, "gfd")).isEqualTo(str);
        assertThat(StringUtils.removeEnd(str, "efg")).isEqualTo("Abcd");
        assertThat(StringUtils.removeEnd(str, "EFG")).isEqualTo(str);

        assertThat(StringUtils.removeEndIgnoreCase(str, "EFG")).isEqualTo("Abcd");
        assertThat(StringUtils.removeEndIgnoreCase(str, "efg")).isEqualTo("Abcd");

        assertThat(StringUtils.removeEndAndLowerFirst(str, "efg")).isNotEqualTo("Abcd");
        assertThat(StringUtils.removeEndAndLowerFirst(str, "efg")).isEqualTo("abcd");

    }

	@Test
	public void testSubstring() {
		// substring
		String str = "Abcdefg";
		assertThat(StringUtils.substring(null, 2)).isNull();
		assertThat(StringUtils.substring(str, 2)).isEqualTo("Ab");
		assertThat(StringUtils.substring(str, -1)).isEqualTo("Abcdef");
		assertThat(StringUtils.substring(str, -2)).isEqualTo("Abcde");

		assertThat(StringUtils.substringBefore(null, "")).isNull();
		assertThat(StringUtils.substringBefore("", "a")).isEmpty();
		assertThat(StringUtils.substringBefore("abc", null)).isEqualTo("abc");
		assertThat(StringUtils.substringBefore("abc", "a")).isEmpty();
		assertThat(StringUtils.substringBefore("abcba", "b")).isEqualTo("a");
		assertThat(StringUtils.substringBefore("abc", "c")).isEqualTo("ab");
		assertThat(StringUtils.substringBefore("abc", "d")).isEqualTo("abc");
		assertThat(StringUtils.substringBefore("abc", "")).isEmpty();
		assertThat(StringUtils.substringBeforeLast("abcba", "b")).isEqualTo("abc");

		// After
		assertThat(StringUtils.substringAfter(null, 2)).isEmpty();
		assertThat(StringUtils.substringAfter(str, 2)).isEqualTo("cdefg");
		assertThat(StringUtils.substringAfter(str, -1)).isEqualTo("g");
		assertThat(StringUtils.substringAfter(str, -2)).isEqualTo("fg");

		assertThat(StringUtils.substringAfter(null, "a")).isNull();
		assertThat(StringUtils.substringAfter("", "a")).isEmpty();
		assertThat(StringUtils.substringAfter("null", null)).isEmpty();

		assertThat(StringUtils.substringAfterLast("abcba", "b")).isEqualTo("a");

		// Between
		assertThat(StringUtils.substringBetween("wx[b]yz", "[", "]")).isEqualTo("b");
		assertThat(StringUtils.substringBetween(null, "*", "*")).isNull();
		assertThat(StringUtils.substringBetween("*", null, "*")).isNull();
		assertThat(StringUtils.substringBetween("*", "*", null)).isNull();
		assertThat(StringUtils.substringBetween("", "", "")).isEmpty();
		assertThat(StringUtils.substringBetween("", "", "]")).isNull();
		assertThat(StringUtils.substringBetween("", "[", "]")).isNull();
		assertThat(StringUtils.substringBetween("yabcz", "", "")).isEmpty();
		assertThat(StringUtils.substringBetween("yabcz", "y", "z")).isEqualTo("abc");
		assertThat(StringUtils.substringBetween("yabczyabcz", "y", "z")).isEqualTo("abc");

		assertThat(StringUtils.substringBetween(null, "*")).isNull();
		assertThat(StringUtils.substringBetween("", "")).isEmpty();
		assertThat(StringUtils.substringBetween("", "tag")).isNull();
		assertThat(StringUtils.substringBetween("tagabctag", null)).isNull();
		assertThat(StringUtils.substringBetween("tagabctag", "")).isEmpty();
		assertThat(StringUtils.substringBetween("tagabctag", "tag")).isEqualTo("abc");
	}
    @Test
    public void testRepeat() {
        assertThat(StringUtils.repeat('a', 0)).isEmpty();
        assertThat(StringUtils.repeat('a', 1)).isEqualTo("a");
        assertThat(StringUtils.repeat('a', 3)).isEqualTo("aaa");

        assertThat(StringUtils.repeat("ab", 0)).isEmpty();
        assertThat(StringUtils.repeat("ab", 1)).isEqualTo("ab");
        assertThat(StringUtils.repeat("ab", 3)).isEqualTo("ababab");

        assertThat(StringUtils.repeat("ab", "/", 5)).isEqualTo("ab/ab/ab/ab/ab");
    }

    @Test
    public void testContains() {
        assertThat(StringUtils.containsAny(null, "*")).isFalse();
        assertThat(StringUtils.containsAny("", "*")).isFalse();
        assertThat(StringUtils.containsAny("*", "")).isFalse();
        assertThat(StringUtils.containsAny("zzabyycdxx", "za")).isTrue();
        assertThat(StringUtils.containsAny("zzabyycdxx", "by")).isTrue();
        assertThat(StringUtils.containsAny("zzabyycdxx", "zy")).isTrue();
        assertThat(StringUtils.containsAny("zzabyycdxx", "\tx")).isTrue();
        assertThat(StringUtils.containsAny("zzabyycdxx", "$.#yF")).isTrue();
        assertThat(StringUtils.containsAny("aba", "z")).isFalse();
    }

}
