package com.jcohy.framework.utils;

import org.apache.commons.collections4.SetUtils;

import java.util.Set;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/18/22:18:48
 * @since 2022.0.1
 */
public class Sets {

    public static <K> Set<K> getSameKeys(Set<K> left, Set<K> right) {
        return SetUtils.intersection(left, right).toSet();
    }
}
