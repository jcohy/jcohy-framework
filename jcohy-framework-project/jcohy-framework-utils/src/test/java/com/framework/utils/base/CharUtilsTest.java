package com.framework.utils.base;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharUtilsTest {

	private static final String IGNORED = "`10-=~!@#$%^&*()_+[]\\{}|;':\",./<>?'\u00c1\u00e1\n";

	private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
	private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	@Test
	public void testCharsIgnored() {
		for (char c : IGNORED.toCharArray()) {
			String str = String.valueOf(c);
			assertTrue(c == CharUtils.toLowerCase(c), str);
			assertTrue(c == CharUtils.toUpperCase(c), str);
			assertFalse(CharUtils.isLowerCase(c), str);
			assertFalse(CharUtils.isUpperCase(c),str);
		}
	}

	@Test
	public void testCharsLower() {
		for (char c : LOWER.toCharArray()) {
			String str = String.valueOf(c);
			assertTrue(c == CharUtils.toLowerCase(c),str);
			assertFalse(c == CharUtils.toUpperCase(c),str);
			assertTrue(CharUtils.isLowerCase(c),str);
			assertFalse(CharUtils.isUpperCase(c),str);
		}
	}

	@Test
	public void testCharsUpper() {
		for (char c : UPPER.toCharArray()) {
			String str = String.valueOf(c);
			assertFalse(c == CharUtils.toLowerCase(c),str);
			assertTrue(c == CharUtils.toUpperCase(c),str);
			assertFalse(CharUtils.isLowerCase(c),str);
			assertTrue(CharUtils.isUpperCase(c),str);
		}
	}

	@Test
	public void testGetAlphaIndex(){
		assertEquals(0,CharUtils.getAlphaIndex('A'));
		assertEquals(0,CharUtils.getAlphaIndex('a'));
		assertEquals(25,CharUtils.getAlphaIndex('z'));
		assertEquals(25,CharUtils.getAlphaIndex('Z'));
		assertTrue(CharUtils.getAlphaIndex(' ') > 26);
		assertTrue(CharUtils.getAlphaIndex('~') > 26);
	}
}