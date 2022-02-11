package com.framework.utils.base;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright: Copyright (c) 2022 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description: 快捷操作 Map 相关工具
 *
 * @author jiac
 * @version 2022.0.1 2022/1/11:22:45
 * @since 2022.0.1
 */
public class Maps {

    enum MapType{
        /**
         * 生成Map的类型
         */
        HashMap,
        ConcurrentHashMap,
        TreeMap
    }

    public static <K,V> HashMap<K,V> hashMap(Object... values){
        return (HashMap<K, V>) map(MapType.HashMap,values);
    }

    public static <K, V> ConcurrentHashMap<K, V> concurrentHashMap(Object... keyValues) {
        return (ConcurrentHashMap<K, V>) map(MapType.ConcurrentHashMap, keyValues);
    }

    public static <K, V> TreeMap<K, V> treeMap(Object... keyValues) {
        return (TreeMap<K, V>) map(MapType.TreeMap, keyValues);
    }

    private static <K, V> Map<K, V> map(MapType mapType,Object... values){
        int length = values.length;
        if(length % 2 == 0 ){
            Map<K, V> map;
            if (mapType.equals(MapType.ConcurrentHashMap)) {
                map = new ConcurrentHashMap<>(length / 2);
            } else if (mapType.equals(MapType.HashMap)) {
                map = new HashMap<>(length / 2);
            } else if (mapType.equals(MapType.TreeMap)) {
                map = new TreeMap<>();
            } else {
                throw new RuntimeException("不支持的Map类型！");
            }
            for (int i = 1; i < values.length; i = i + 2) {
                //noinspection unchecked
                K key = (K) values[i - 1];
                //noinspection unchecked
                V value = (V) values[i];
                map.put(key, value);
            }
            return map;
        } else {
            throw new RuntimeException("键值对数目不匹配！");
        }
    }
}
