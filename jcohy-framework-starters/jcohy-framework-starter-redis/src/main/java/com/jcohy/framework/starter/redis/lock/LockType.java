package com.jcohy.framework.starter.redis.lock;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 * @author jiac
 * @version 2022.0.1 3/14/22:16:55
 * @since 2022.0.1
 */
public enum LockType {

    /**
     * 重入锁.
     */
    REENTRANT,
    /**
     * 公平锁.
     */
    FAIR

}
