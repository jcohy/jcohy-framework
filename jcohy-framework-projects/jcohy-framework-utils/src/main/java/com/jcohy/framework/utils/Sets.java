package com.jcohy.framework.utils;

import java.util.Set;

import org.apache.commons.collections4.SetUtils;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/18/22:18:48
 * @since 2022.0.1
 */
public final class Sets {

    private Sets() {
    }

    /**
     * 获取两个 set 中相同的 kay.
     * @param left left
     * @param right right
     * @param <K> 泛型类型
     * @return /
     */
    public static <K> Set<K> getSameKeys(Set<K> left, Set<K> right) {
        return SetUtils.intersection(left, right).toSet();
    }

}
