package com.jcohy.framework.utils.maps;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.lang.Nullable;
import org.springframework.util.LinkedCaseInsensitiveMap;

import com.jcohy.framework.utils.constant.StringPools;

/**
 * 描述: 不区分大小写的有序 map. 提供获取指定值的通用方法 {@link LinkedCaseInsensitiveMap} .
 *
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:16:13
 * @since 2022.0.1
 */
public final class CaseInsensitiveChainMap extends LinkedCaseInsensitiveMap<Object> {

    private CaseInsensitiveChainMap() {
        super();
    }

    /**
     * 创建 caseInsensitiveChainMap.
     * @return caseInsensitiveChainMap
     */
    public static CaseInsensitiveChainMap create() {
        return new CaseInsensitiveChainMap();
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>(16);
    }

    /**
     * 设置值.
     * @param key key
     * @param value value
     * @return caseInsensitiveChainMap
     */
    public CaseInsensitiveChainMap set(String key, Object value) {
        this.put(key, value);
        return this;
    }

    /**
     * 设置全部值.
     * @param map map
     * @return caseInsensitiveChainMap
     */
    public CaseInsensitiveChainMap setAll(@Nullable Map<? extends String, ?> map) {
        if (map != null) {
            this.putAll(map);
        }
        return this;
    }

    public Object get(String key) {
        return (Object) super.get(key);
    }

    /**
     * 将 获取到的结果转换为指定类型的值.
     * @param key key
     * @param defaultValue 默认值
     * @param <T> 结果类型
     * @return /
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, T defaultValue) {
        Object result = get(key);
        return (T) ((result != null) ? result : defaultValue);
    }

    public String getString(String key) {
        return String.valueOf(get(key, StringPools.EMPTY));
    }

    public Integer getInteger(String key) {
        return Integer.valueOf(get(key, null));
    }

    /**
     * 获得特定类型值.
     * @param attr 字段名
     * @return 字段值
     */
    public Date getDate(String attr) {
        return get(attr, null);
    }

    /**
     * 获得特定类型值.
     * @param attr 字段名
     * @return 字段值
     */
    public Time getTime(String attr) {
        return get(attr, null);
    }

    /**
     * 获得特定类型值.
     * @param attr 字段名
     * @return 字段值
     */
    public Timestamp getTimestamp(String attr) {
        return get(attr, null);
    }

    /**
     * 获得特定类型值.
     * @param attr 字段名
     * @return 字段值
     */
    public Number getNumber(String attr) {
        return get(attr, null);
    }

}
