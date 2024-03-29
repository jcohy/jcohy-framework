package com.jcohy.framework.utils;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;

import com.jcohy.framework.utils.function.Func0;

/**
 * 描述: 简单缓存，无超时实现，默认使用{@link WeakHashMap}实现缓存自动清理.
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @author jiac
 * @version 2022.0.1 3/3/22:17:39
 * @since 2022.0.1
 */
public class SimpleCache<K, V> implements Iterable<Map.Entry<K, V>>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 池.
     */
    private final Map<K, V> cache;

    // 乐观读写锁
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 写的时候每个 key 一把锁，降低锁的粒度.
     */
    protected final Map<K, Lock> keyLockMap = new ConcurrentHashMap<>();

    /**
     * 构造，默认使用{@link WeakHashMap}实现缓存自动清理.
     */
    public SimpleCache() {
        this(new WeakHashMap<>());
    }

    /**
     * 构造.
     * <p>
     * 通过自定义Map初始化，可以自定义缓存实现。<br>
     * 比如使用{@link WeakHashMap}则会自动清理key，使用HashMap则不会清理<br>
     * 同时，传入的Map对象也可以自带初始化的键值对，防止在get时创建
     * </p>
     * @param initMap 初始Map，用于定义Map类型
     */
    public SimpleCache(Map<K, V> initMap) {
        this.cache = initMap;
    }

    /**
     * 从缓存池中查找值.
     * @param key 键
     * @return 值
     */
    public V get(K key) {
        this.lock.readLock().lock();
        try {
            return this.cache.get(key);
        }
        finally {
            this.lock.readLock().unlock();
        }
    }

    /**
     * 从缓存中获得对象，当对象不在缓存中或已经过期返回 Func0 回调产生的对象.
     * @param key 键
     * @param supplier 如果不存在回调方法，用于生产值对象
     * @return 值对象
     */
    public V get(K key, Func0<V> supplier) {
        return get(key, null, supplier);
    }

    /**
     * 从缓存中获得对象，当对象不在缓存中或已经过期返回 Func0 回调产生的对象.
     * @param key 键
     * @param validPredicate 检查结果对象是否可用，如是否断开连接等
     * @param supplier 如果不存在回调方法或结果不可用，用于生产值对象
     * @return 值对象
     * @since 2022.0.1
     */
    public V get(K key, Predicate<V> validPredicate, Func0<V> supplier) {
        V v = get(key);
        if (null == v && null != supplier) {
            // 每个key单独获取一把锁，降低锁的粒度提高并发能力，see pr#1385@Github
            final Lock keyLock = this.keyLockMap.computeIfAbsent(key, (k) -> new ReentrantLock());
            keyLock.lock();
            try {
                // 双重检查，防止在竞争锁的过程中已经有其它线程写入
                v = this.cache.get(key);
                if (null == v || (null != validPredicate && !validPredicate.test(v))) {
                    try {
                        v = supplier.call();
                    }
                    catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    put(key, v);
                }
            }
            finally {
                keyLock.unlock();
                this.keyLockMap.remove(key);
            }
        }

        return v;
    }

    /**
     * 放入缓存.
     * @param key 键
     * @param value 值
     * @return 值
     */
    public V put(K key, V value) {
        // 独占写锁
        this.lock.writeLock().lock();
        try {
            this.cache.put(key, value);
        }
        finally {
            this.lock.writeLock().unlock();
        }
        return value;
    }

    /**
     * 移除缓存.
     * @param key 键
     * @return 移除的值
     */
    public V remove(K key) {
        // 独占写锁
        this.lock.writeLock().lock();
        try {
            return this.cache.remove(key);
        }
        finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * 清空缓存池.
     */
    public void clear() {
        // 独占写锁
        this.lock.writeLock().lock();
        try {
            this.cache.clear();
        }
        finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return this.cache.entrySet().iterator();
    }

}
