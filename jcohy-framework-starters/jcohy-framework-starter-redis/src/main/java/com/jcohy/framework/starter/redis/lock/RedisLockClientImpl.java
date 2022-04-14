package com.jcohy.framework.starter.redis.lock;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import com.jcohy.framework.utils.exceptions.CheckedSupplier;
import com.jcohy.framework.utils.exceptions.Exceptions;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/14/22:17:03
 * @since 2022.0.1
 */
public class RedisLockClientImpl implements RedisLockClient {

    private final RedissonClient redissonClient;

    public RedisLockClientImpl(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public boolean tryLock(String lockName, LockType lockType, long waitTime, long leaseTime, TimeUnit timeUnit)
            throws InterruptedException {
        RLock lock = getLock(lockName, lockType);
        return lock.tryLock(waitTime, leaseTime, timeUnit);
    }

    @Override
    public void unLock(String lockName, LockType lockType) {
        RLock lock = getLock(lockName, lockType);
        // 仅仅在已经锁定和当前线程持有锁时解锁
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    @Override
    public <T> T lock(String lockName, LockType lockType, long waitTime, long leaseTime, TimeUnit timeUnit,
            CheckedSupplier<T> supplier) {
        try {
            boolean result = this.tryLock(lockName, lockType, waitTime, leaseTime, timeUnit);
            if (result) {
                return supplier.get();
            }
        }
        catch (Throwable ex) {
            throw Exceptions.unchecked(ex);
        }
        finally {
            this.unLock(lockName, lockType);
        }
        return null;
    }

    private RLock getLock(String lockName, LockType lockType) {
        RLock lock;
        if (LockType.REENTRANT == lockType) {
            lock = this.redissonClient.getLock(lockName);
        }
        else {
            lock = this.redissonClient.getFairLock(lockName);
        }
        return lock;
    }

}
