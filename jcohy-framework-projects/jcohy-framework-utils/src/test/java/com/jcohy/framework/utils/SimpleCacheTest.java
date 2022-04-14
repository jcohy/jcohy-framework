package com.jcohy.framework.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/3/22:17:44
 * @since 2022.0.1
 */
public class SimpleCacheTest {

    @BeforeEach
    public void putTest() {
        final SimpleCache<String, String> cache = new SimpleCache<>();
        new Thread(() -> cache.put("key1", "value1"));
        new Thread(() -> cache.get("key1"));
        new Thread(() -> cache.put("key2", "value2"));
        new Thread(() -> cache.get("key2"));
        new Thread(() -> cache.put("key3", "value3"));
        new Thread(() -> cache.get("key3"));
        new Thread(() -> cache.put("key4", "value4"));
        new Thread(() -> cache.get("key4"));
        new Thread(() -> cache.get("key5", () -> "value5"));

        cache.get("key5", () -> "value5");
    }

    @Test
    public void getTest() {
        final SimpleCache<String, String> cache = new SimpleCache<>();
        cache.put("key1", "value1");
        cache.get("key1");
        cache.put("key2", "value2");
        cache.get("key2");
        cache.put("key3", "value3");
        cache.get("key3");
        cache.put("key4", "value4");
        cache.get("key4");
        cache.get("key5", () -> "value5");

        Assertions.assertEquals("value1", cache.get("key1"));
        Assertions.assertEquals("value2", cache.get("key2"));
        Assertions.assertEquals("value3", cache.get("key3"));
        Assertions.assertEquals("value4", cache.get("key4"));
        Assertions.assertEquals("value5", cache.get("key5"));
        Assertions.assertEquals("value6", cache.get("key6", () -> "value6"));
    }

    // @Test
    // public void getConcurrencyTest(){
    // final SimpleCache<String, String> cache = new SimpleCache<>();
    // final ConcurrencyTester tester = new ConcurrencyTester(9000);
    // tester.test(()-> cache.get("aaa", ()-> {
    // Thread.sleep(1000);
    // return "aaaValue";
    // }));
    //
    // Assertions.assertTrue(tester.getInterval() > 0);
    // Assertions.assertEquals("aaaValue", cache.get("aaa"));
    // }

}
