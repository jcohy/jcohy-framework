//package com.framework.utils.base;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//class StringUtilsTest {
//
//
//	private static final String IGNORED = "`10-=~!@#$%^&*()_+[]\\{}|;':\",./<>?'\u00c1\u00e1\n";
//
//	private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
//	private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//
//	@Test
//	void isEmpty() {
//	}
//
//	@Test
//	void isBlack() {
//	}
//
//	@Test
//	void isNotEmpty() {
//	}
//
//	@Test
//	void isNotBlack() {
//	}
//
//	@Test
//	void equalsIgnoreCase() {
//		assertTrue(StringUtils.equalsIgnoreCase("", ""));
//		assertFalse(StringUtils.equalsIgnoreCase("", "x"));
//		assertFalse(StringUtils.equalsIgnoreCase("x", ""));
//		assertTrue(StringUtils.equalsIgnoreCase(LOWER, UPPER));
//		assertTrue(StringUtils.equalsIgnoreCase(UPPER, LOWER));
//		// 创建新字符串进行比较。
//		assertTrue(StringUtils.equalsIgnoreCase(new String(IGNORED), new String(IGNORED)));
//		// 与: "\u00c1".equalsIgnoreCase("\u00e1") == true 相比较
//		assertFalse(StringUtils.equalsIgnoreCase("\u00c1", "\u00e1"));
//		// 测试超出字母范围的字符 ('A'-1 vs 'a'-1, 'Z'+1 vs 'z'+1)
//		assertFalse(StringUtils.equalsIgnoreCase("@", "`"));
//		assertFalse(StringUtils.equalsIgnoreCase("[", "{"));
//
//		// Unicode point {@code 00df} 是 sharp-S (ß) 的小写形式，其大写字母是 "SS".
//		assertEquals("PASSWORD", "pa\u00dfword".toUpperCase()); // [*]
//		assertFalse("pa\u00dfword".equalsIgnoreCase("PASSWORD")); // [*]
//		assertFalse(StringUtils.equalsIgnoreCase("pa\u00dfword", "PASSWORD"));
//	}
//
//	@Test
//	void trimWhitespace() {
//	}
//
//	@Test
//	void toLowerCase() {
//		assertEquals(LOWER,StringUtils.toLowerCase(UPPER));
//		assertSame(LOWER,StringUtils.toLowerCase(UPPER));
//		assertEquals(IGNORED, StringUtils.toLowerCase(IGNORED));
//		assertEquals("foobar", StringUtils.toLowerCase("fOobaR"));
//	}
//
//	@Test
//	void toUpperCase() {
//		assertEquals(UPPER, StringUtils.toUpperCase(LOWER));
//		assertSame(UPPER, StringUtils.toUpperCase(UPPER));
//		assertEquals(IGNORED, StringUtils.toUpperCase(IGNORED));
//		assertEquals("FOOBAR", StringUtils.toUpperCase("FoOBAr"));
//	}
//
//	@Test
//	void nullToEmpty() {
//		assertEquals("", StringUtils.nullToEmpty(null));
//		assertEquals("", StringUtils.nullToEmpty(""));
//		assertEquals("a", StringUtils.nullToEmpty("a"));
//	}
//
//	@Test
//	void emptyToNull() {
//		assertNull(StringUtils.emptyToNull(null));
//		assertNull(StringUtils.emptyToNull(""));
//		assertEquals("a", StringUtils.emptyToNull("a"));
//	}
//
//	@Test
//	void truncate() {
//		assertEquals("foobar",StringUtils.truncate("foobar",10,"..."));
//		assertEquals("fo...", StringUtils.truncate("foobar", 5, "..."));
//		assertEquals("foobar", StringUtils.truncate("foobar", 6, "..."));
//		assertEquals("...", StringUtils.truncate("foobar", 3, "..."));
//		assertEquals("foobar", StringUtils.truncate("foobar", 10, "…"));
//		assertEquals("foo…", StringUtils.truncate("foobar", 4, "…"));
//		assertEquals("fo--", StringUtils.truncate("foobar", 4, "--"));
//		assertEquals("foobar", StringUtils.truncate("foobar", 6, "…"));
//		assertEquals("foob…", StringUtils.truncate("foobar", 5, "…"));
//		assertEquals("foo", StringUtils.truncate("foobar", 3, ""));
//		assertEquals("", StringUtils.truncate("", 5, ""));
//		assertEquals("", StringUtils.truncate("", 5, "..."));
//		assertEquals("", StringUtils.truncate("", 0, ""));
//	}
//
//	@Test
//	public void testTruncateIllegalArguments() {
//		String truncated = null;
//		try {
//			truncated = StringUtils.truncate("foobar", 2, "...");
//			fail();
//		} catch (IllegalArgumentException expected) {
//		}
//
//		try {
//			truncated = StringUtils.truncate("foobar", 8, "1234567890");
//			fail();
//		} catch (IllegalArgumentException expected) {
//		}
//
//		try {
//			truncated = StringUtils.truncate("foobar", -1, "...");
//			fail();
//		} catch (IllegalArgumentException expected) {
//		}
//
//		try {
//			truncated = StringUtils.truncate("foobar", -1, "");
//			fail();
//		} catch (IllegalArgumentException expected) {
//		}
//	}
//
//	@Test
//	void commonPrefix() {
//		assertEquals("", StringUtils.commonPrefix("", ""));
//		assertEquals("", StringUtils.commonPrefix("abc", ""));
//		assertEquals("", StringUtils.commonPrefix("", "abc"));
//		assertEquals("", StringUtils.commonPrefix("abcde", "xyz"));
//		assertEquals("", StringUtils.commonPrefix("xyz", "abcde"));
//		assertEquals("", StringUtils.commonPrefix("xyz", "abcxyz"));
//		assertEquals("a", StringUtils.commonPrefix("abc", "aaaaa"));
//		assertEquals("aa", StringUtils.commonPrefix("aa", "aaaaa"));
//		assertEquals("abc", StringUtils.commonPrefix(new StringBuffer("abcdef"), "abcxyz"));
//
//		// Identical valid surrogate pairs.
//		assertEquals(
//				"abc\uD8AB\uDCAB", StringUtils.commonPrefix("abc\uD8AB\uDCABdef", "abc\uD8AB\uDCABxyz"));
//		// Differing valid surrogate pairs.
//		assertEquals("abc", StringUtils.commonPrefix("abc\uD8AB\uDCABdef", "abc\uD8AB\uDCACxyz"));
//		// One invalid pair.
//		assertEquals("abc", StringUtils.commonPrefix("abc\uD8AB\uDCABdef", "abc\uD8AB\uD8ABxyz"));
//		// Two identical invalid pairs.
//		assertEquals(
//				"abc\uD8AB\uD8AC", StringUtils.commonPrefix("abc\uD8AB\uD8ACdef", "abc\uD8AB\uD8ACxyz"));
//		// Two differing invalid pairs.
//		assertEquals("abc\uD8AB", StringUtils.commonPrefix("abc\uD8AB\uD8ABdef", "abc\uD8AB\uD8ACxyz"));
//		// One orphan high surrogate.
//		assertEquals("", StringUtils.commonPrefix("\uD8AB\uDCAB", "\uD8AB"));
//		// Two orphan high surrogates.
//		assertEquals("\uD8AB", StringUtils.commonPrefix("\uD8AB", "\uD8AB"));
//	}
//
//	@Test
//	void commonSuffix() {
//		assertEquals("", StringUtils.commonSuffix("", ""));
//		assertEquals("", StringUtils.commonSuffix("abc", ""));
//		assertEquals("", StringUtils.commonSuffix("", "abc"));
//		assertEquals("", StringUtils.commonSuffix("abcde", "xyz"));
//		assertEquals("", StringUtils.commonSuffix("xyz", "abcde"));
//		assertEquals("", StringUtils.commonSuffix("xyz", "xyzabc"));
//		assertEquals("c", StringUtils.commonSuffix("abc", "ccccc"));
//		assertEquals("aa", StringUtils.commonSuffix("aa", "aaaaa"));
//		assertEquals("abc", StringUtils.commonSuffix(new StringBuffer("xyzabc"), "xxxabc"));
//
//		// Identical valid surrogate pairs.
//		assertEquals(
//				"\uD8AB\uDCABdef", StringUtils.commonSuffix("abc\uD8AB\uDCABdef", "xyz\uD8AB\uDCABdef"));
//		// Differing valid surrogate pairs.
//		assertEquals("def", StringUtils.commonSuffix("abc\uD8AB\uDCABdef", "abc\uD8AC\uDCABdef"));
//		// One invalid pair.
//		assertEquals("def", StringUtils.commonSuffix("abc\uD8AB\uDCABdef", "xyz\uDCAB\uDCABdef"));
//		// Two identical invalid pairs.
//		assertEquals(
//				"\uD8AB\uD8ABdef", StringUtils.commonSuffix("abc\uD8AB\uD8ABdef", "xyz\uD8AB\uD8ABdef"));
//		// Two differing invalid pairs.
//		assertEquals("\uDCABdef", StringUtils.commonSuffix("abc\uDCAB\uDCABdef", "abc\uDCAC\uDCABdef"));
//		// One orphan low surrogate.
//		assertEquals("", StringUtils.commonSuffix("x\uD8AB\uDCAB", "\uDCAB"));
//		// Two orphan low surrogates.
//		assertEquals("\uDCAB", StringUtils.commonSuffix("\uDCAB", "\uDCAB"));
//	}
//
//	@Test
//	void lenientFormat() {
//		assertEquals("%s", StringUtils.lenientFormat("%s"));
//		assertEquals("5", StringUtils.lenientFormat("%s", 5));
//		assertEquals("foo [5]", StringUtils.lenientFormat("foo", 5));
//		assertEquals("foo [5, 6, 7]", StringUtils.lenientFormat("foo", 5, 6, 7));
//		assertEquals("%s 1 2", StringUtils.lenientFormat("%s %s %s", "%s", 1, 2));
//		assertEquals(" [5, 6]", StringUtils.lenientFormat("", 5, 6));
//		assertEquals("123", StringUtils.lenientFormat("%s%s%s", 1, 2, 3));
//		assertEquals("1%s%s", StringUtils.lenientFormat("%s%s%s", 1));
//		assertEquals("5 + 6 = 11", StringUtils.lenientFormat("%s + 6 = 11", 5));
//		assertEquals("5 + 6 = 11", StringUtils.lenientFormat("5 + %s = 11", 6));
//		assertEquals("5 + 6 = 11", StringUtils.lenientFormat("5 + 6 = %s", 11));
//		assertEquals("5 + 6 = 11", StringUtils.lenientFormat("%s + %s = %s", 5, 6, 11));
//		assertEquals("null [null, null]", StringUtils.lenientFormat("%s", null, null, null));
//		assertEquals("null [5, 6]", StringUtils.lenientFormat(null, 5, 6));
//		assertEquals("null", StringUtils.lenientFormat("%s", (Object) null));
//		assertEquals("(Object[])null", StringUtils.lenientFormat("%s", (Object[]) null));
//	}
//
//	@Test
//	void padStart() {
//		// noPadding
//		assertSame("", StringUtils.padStart("", 0, '-'));
//		assertSame("x", StringUtils.padStart("x", 0, '-'));
//		assertSame("x", StringUtils.padStart("x", 1, '-'));
//		assertSame("xx", StringUtils.padStart("xx", 0, '-'));
//		assertSame("xx", StringUtils.padStart("xx", 2, '-'));
//
//		// somePadding
//		assertEquals("-", StringUtils.padStart("", 1, '-'));
//		assertEquals("--", StringUtils.padStart("", 2, '-'));
//		assertEquals("-x", StringUtils.padStart("x", 2, '-'));
//		assertEquals("--x", StringUtils.padStart("x", 3, '-'));
//		assertEquals("-xx", StringUtils.padStart("xx", 3, '-'));
//
//		// negativeMinLength
//		assertSame("x", StringUtils.padStart("x", -1, '-'));
//	}
//
//	@Test
//	public void padStart_null() {
//		try {
//			StringUtils.padStart(null, 5, '0');
//			fail();
//		} catch (NullPointerException expected) {
//		}
//	}
//
//	@Test
//	void padEnd() {
//		// noPadding
//		assertSame("", StringUtils.padEnd("", 0, '-'));
//		assertSame("x", StringUtils.padEnd("x", 0, '-'));
//		assertSame("x", StringUtils.padEnd("x", 1, '-'));
//		assertSame("xx", StringUtils.padEnd("xx", 0, '-'));
//		assertSame("xx", StringUtils.padEnd("xx", 2, '-'));
//
//		// somePadding
//		assertEquals("-", StringUtils.padEnd("", 1, '-'));
//		assertEquals("--", StringUtils.padEnd("", 2, '-'));
//		assertEquals("x-", StringUtils.padEnd("x", 2, '-'));
//		assertEquals("x--", StringUtils.padEnd("x", 3, '-'));
//		assertEquals("xx-", StringUtils.padEnd("xx", 3, '-'));
//
//		// negativeMinLength
//		assertSame("x", StringUtils.padEnd("x", -1, '-'));
//	}
//
//	@Test
//	public void padEnd_null() {
//		try {
//			StringUtils.padEnd(null, 5, '0');
//			fail();
//		} catch (NullPointerException expected) {
//		}
//	}
//
//	@Test
//	void repeat() {
//		String input = "20";
//		assertEquals("", StringUtils.repeat(input, 0));
//		assertEquals("20", StringUtils.repeat(input, 1));
//		assertEquals("2020", StringUtils.repeat(input, 2));
//		assertEquals("202020", StringUtils.repeat(input, 3));
//
//		assertEquals("", StringUtils.repeat("", 4));
//
//		for (int i = 0; i < 100; ++i) {
//			assertEquals(2 * i, StringUtils.repeat(input, i).length());
//		}
//
//		try {
//			StringUtils.repeat("x", -1);
//			fail();
//		} catch (IllegalArgumentException expected) {
//		}
//		try {
//			// Massive string
//			StringUtils.repeat("12345678", (1 << 30) + 3);
//			fail();
//		} catch (ArrayIndexOutOfBoundsException expected) {
//		}
//	}
//
//	@Test
//	public void testRepeat_null() {
//		try {
//			StringUtils.repeat(null, 5);
//			fail();
//		} catch (NullPointerException expected) {
//		}
//	}
//
//	@Test
//	void validSurrogatePairAt() {
//		assertTrue(StringUtils.validSurrogatePairAt("\uD8AB\uDCAB", 0));
//		assertTrue(StringUtils.validSurrogatePairAt("abc\uD8AB\uDCAB", 3));
//		assertTrue(StringUtils.validSurrogatePairAt("abc\uD8AB\uDCABxyz", 3));
//		assertFalse(StringUtils.validSurrogatePairAt("\uD8AB\uD8AB", 0));
//		assertFalse(StringUtils.validSurrogatePairAt("\uDCAB\uDCAB", 0));
//		assertFalse(StringUtils.validSurrogatePairAt("\uD8AB\uDCAB", -1));
//		assertFalse(StringUtils.validSurrogatePairAt("\uD8AB\uDCAB", 1));
//		assertFalse(StringUtils.validSurrogatePairAt("\uD8AB\uDCAB", -2));
//		assertFalse(StringUtils.validSurrogatePairAt("\uD8AB\uDCAB", 2));
//		assertFalse(StringUtils.validSurrogatePairAt("x\uDCAB", 0));
//		assertFalse(StringUtils.validSurrogatePairAt("\uD8ABx", 0));
//	}
//
//	@Test
//	void trimAllWhitespace() {
//	}
//
//	@Test
//	void trimLeadingWhitespace() {
//	}
//
//	@Test
//	void trimTrailingWhitespace() {
//	}
//
//	@Test
//	void trimLeadingCharacter() {
//	}
//
//	@Test
//	void trimTrailingCharacter() {
//	}
//}