package com.jcohy.framework.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:16:21
 * @since 2022.0.1
 */
public final class CollectionUtils extends org.springframework.util.CollectionUtils {

    private CollectionUtils() {
    }

    /**
     * 加入全部.
     * @param <T> 集合元素类型
     * @param collection 被加入的集合 {@link Collection}
     * @param iterator 要加入的{@link Iterator}
     * @return 原集合
     * @since 2022.0.1
     */
    public static <T> Collection<T> addAll(Collection<T> collection, Iterator<T> iterator) {
        if (null != collection && null != iterator) {
            while (iterator.hasNext()) {
                collection.add(iterator.next());
            }
        }
        return collection;
    }

    /**
     * 加入全部.
     * @param <T> 集合元素类型
     * @param collection 被加入的集合 {@link Collection}
     * @param iterable 要加入的内容{@link Iterable}
     * @return 原集合
     * @since 2022.0.1
     */
    public static <T> Collection<T> addAll(Collection<T> collection, Iterable<T> iterable) {
        if (iterable == null) {
            return collection;
        }
        return addAll(collection, iterable.iterator());
    }

    /**
     * 集合 null 转 empty.
     * @param <T> 集合元素类型
     * @param list 被加入的集合 {@link List}
     * @return 原集合
     * @since 2022.0.1
     */
    public static <T> List<T> nullToEmpty(List<T> list) {
        return (list != null) ? list : Collections.emptyList();
    }

    /**
     * check whether the given Array contains the given element.
     * @param array the Array to check
     * @param element the element to look for
     * @param <T> the generic tag
     * @return {@code true} if found, {@code false} else
     * @since 2022.0.1
     */
    public static <T> boolean contains(@Nullable T[] array, final T element) {
        if (array == null) {
            return false;
        }
        return Arrays.stream(array).anyMatch((x) -> ObjectUtils.nullSafeEquals(x, element));
    }

    /**
     * 集合是否为非空.
     * @param collection 集合
     * @return 是否为非空
     * @since 2022.0.1
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

}
