package com.jcohy.framework.utils.exceptions;

import org.springframework.lang.Nullable;

/**
 * 描述: 受检的 Supplier.
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @param <T> 范型类型
 * @author jiac
 * @version 2022.0.1 3/14/22:17:02
 * @since 2022.0.1
 */
@FunctionalInterface
public interface CheckedSupplier<T> {

    /**
     * Run the Supplier.
     * @return t t
     * @throws Throwable checkedException
     */
    @Nullable
    T get() throws Throwable;

}
