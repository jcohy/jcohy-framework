package com.jcohy.framework.utils.maps;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/14/22:17:19
 * @since 2022.0.1
 */
public final class Maps {

    private Maps() {
    }

    /**
     * 将 object 数组转为 map 结构。奇数位为 key，偶数位为 value.
     * @param keysValues 数组
     * @param <K> key
     * @param <V> value
     * @return maps
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> toMap(Object... keysValues) {
        int kvLength = keysValues.length;
        if (kvLength % 2 != 0) {
            throw new IllegalArgumentException("wrong number of arguments for met, keysValues length can not be odd");
        }
        Map<K, V> keyValueMap = new HashMap<>(kvLength);
        for (int i = kvLength - 2; i >= 0; i -= 2) {
            Object key = keysValues[i];
            Object value = keysValues[i + 1];
            keyValueMap.put((K) key, (V) value);
        }
        return keyValueMap;
    }

}
