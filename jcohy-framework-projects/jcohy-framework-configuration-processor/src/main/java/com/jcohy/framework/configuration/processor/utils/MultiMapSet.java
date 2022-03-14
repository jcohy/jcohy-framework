package com.jcohy.framework.configuration.processor.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @param <K> key
 * @param <V> value
 * @author jiac
 * @version 2022.0.1 2022/2/11:10:59
 * @since 2022.0.1
 */
public class MultiMapSet<K, V> {

    private final transient Map<K, Set<V>> map;

    public MultiMapSet() {
        this.map = new HashMap<>();
    }

    public Set<V> createSet() {
        return new HashSet<>();
    }

    /**
     * put to MultiMapSet.
     * @param key 键
     * @param value 值
     * @return boolean
     */
    public boolean put(K key, V value) {
        Set<V> set = this.map.get(key);
        if (set == null) {
            set = createSet();
            if (set.add(value)) {
                this.map.put(key, set);
                return true;
            }
            else {
                throw new AssertionError("New set violated the set spec");
            }
        }
        else {
            return set.add(value);
        }
    }

    /**
     * 是否包含某个 key.
     * @param key key
     * @return 结果
     */
    public boolean containsKey(K key) {
        return this.map.containsKey(key);
    }

    /**
     * 是否包含 value 中的某个值.
     * @param value value
     * @return 是否包含
     */
    public boolean containsVal(V value) {
        Collection<Set<V>> values = this.map.values();
        return values.stream().anyMatch((vs) -> vs.contains(value));
    }

    /**
     * key 集合.
     * @return keys
     */
    public Set<K> keySet() {
        return this.map.keySet();
    }

    /**
     * put list to MultiSetMap.
     * @param key 键
     * @param set 值列表
     * @return boolean
     */
    public boolean putAll(K key, Set<V> set) {
        if (set == null) {
            return false;
        }
        else {
            this.map.put(key, set);
            return true;
        }
    }

    /**
     * get List by key.
     * @param key 键
     * @return 返回一个 List
     */
    public Set<V> get(K key) {
        return this.map.get(key);
    }

    /**
     * clear MultiSetMap.
     */
    public void clear() {
        this.map.clear();
    }

    /**
     * isEmpty.
     * @return isEmpty
     */
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public String toString() {
        return "MultiMapSet{" + "map=" + this.map + '}';
    }

}
