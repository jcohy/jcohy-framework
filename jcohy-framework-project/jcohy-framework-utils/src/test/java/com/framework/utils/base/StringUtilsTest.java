package com.framework.utils.base;

import com.google.common.base.Ascii;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StringUtilsTest {

	private static final String IGNORED = "`10-=~!@#$%^&*()_+[]\\{}|;':\",./<>?'\u00c1\u00e1\n";

	private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
	private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	@Test
	void isEmpty() {

	}

	@Test
	void isBlack() {
	}

	@Test
	void isNotEmpty() {
	}

	@Test
	void isNotBlack() {
	}

	@Test
	public void testEqualsIgnoreCase() {
		assertTrue(StringUtils.equalsIgnoreCase("", ""));
		assertFalse(StringUtils.equalsIgnoreCase("", "x"));
		assertFalse(StringUtils.equalsIgnoreCase("x", ""));
		assertTrue(StringUtils.equalsIgnoreCase(LOWER, UPPER));
		assertTrue(StringUtils.equalsIgnoreCase(UPPER, LOWER));
		// 创建新字符串进行比较。
		assertTrue(StringUtils.equalsIgnoreCase(new String(IGNORED), new String(IGNORED)));
		// 与: "\u00c1".equalsIgnoreCase("\u00e1") == true 相比较
		assertFalse(StringUtils.equalsIgnoreCase("\u00c1", "\u00e1"));
		// 测试超出字母范围的字符 ('A'-1 vs 'a'-1, 'Z'+1 vs 'z'+1)
		assertFalse(StringUtils.equalsIgnoreCase("@", "`"));
		assertFalse(StringUtils.equalsIgnoreCase("[", "{"));

		// Unicode point {@code 00df} 是 sharp-S (ß) 的小写形式，其大写字母是 "SS".
		assertEquals("PASSWORD", "pa\u00dfword".toUpperCase()); // [*]
		assertFalse("pa\u00dfword".equalsIgnoreCase("PASSWORD")); // [*]
		assertFalse(StringUtils.equalsIgnoreCase("pa\u00dfword", "PASSWORD"));
	}

	@Test
	void trimWhitespace() {
	}

	@Test
	void testToLowerCase() {
		assertEquals(LOWER,StringUtils.toLowerCase(UPPER));
		assertSame(LOWER,StringUtils.toLowerCase(UPPER));
		assertEquals(IGNORED, StringUtils.toLowerCase(IGNORED));
		assertEquals("foobar", StringUtils.toLowerCase("fOobaR"));
	}

	@Test
	void testToUpperCase(){
		assertEquals(UPPER, StringUtils.toUpperCase(LOWER));
		assertSame(UPPER, StringUtils.toUpperCase(UPPER));
		assertEquals(IGNORED, StringUtils.toUpperCase(IGNORED));
		assertEquals("FOOBAR", StringUtils.toUpperCase("FoOBAr"));
	}


	@Test
	void testTruncate() {
		assertEquals("foobar",StringUtils.truncate("foobar",10,"..."));
		assertEquals("fo...", StringUtils.truncate("foobar", 5, "..."));
		assertEquals("foobar", StringUtils.truncate("foobar", 6, "..."));
		assertEquals("...", StringUtils.truncate("foobar", 3, "..."));
		assertEquals("foobar", StringUtils.truncate("foobar", 10, "…"));
		assertEquals("foo…", StringUtils.truncate("foobar", 4, "…"));
		assertEquals("fo--", StringUtils.truncate("foobar", 4, "--"));
		assertEquals("foobar", StringUtils.truncate("foobar", 6, "…"));
		assertEquals("foob…", StringUtils.truncate("foobar", 5, "…"));
		assertEquals("foo", StringUtils.truncate("foobar", 3, ""));
		assertEquals("", StringUtils.truncate("", 5, ""));
		assertEquals("", StringUtils.truncate("", 5, "..."));
		assertEquals("", StringUtils.truncate("", 0, ""));
	}

	@Test
	public void testTruncateIllegalArguments() {
		String truncated = null;
		try {
			truncated = StringUtils.truncate("foobar", 2, "...");
			fail();
		} catch (IllegalArgumentException expected) {
		}

		try {
			truncated = StringUtils.truncate("foobar", 8, "1234567890");
			fail();
		} catch (IllegalArgumentException expected) {
		}

		try {
			truncated = StringUtils.truncate("foobar", -1, "...");
			fail();
		} catch (IllegalArgumentException expected) {
		}

		try {
			truncated = StringUtils.truncate("foobar", -1, "");
			fail();
		} catch (IllegalArgumentException expected) {
		}
	}
}