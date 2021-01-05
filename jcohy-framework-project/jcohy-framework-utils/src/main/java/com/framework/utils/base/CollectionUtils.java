package com.framework.utils.base;

import java.util.Collection;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Copyright: Copyright (c) 2020 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
 *
 * <p> Description:
 *
 * @author jiac
 * @version 1.0.0 2021/1/5:16:36
 * @since 1.0.0
 */
public class CollectionUtils {

	public static boolean isEmpty(@Nullable Collection<?> collection) {
		return (collection == null || collection.isEmpty());
	}
}
