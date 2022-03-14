package com.jcohy.framework.starter.redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.Assert;

import com.jcohy.framework.utils.maps.Maps;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @param <K> key
 * @param <V> value
 * @author jiac
 * @version 2022.0.1 3/14/22:17:16
 * @since 2022.0.1
 */
public class RedisUtils<K, V> {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    private final RedisTemplate<String, V> redisTemplate;

    private final ValueOperations<String, V> valueOps;

    private final HashOperations<String, K, V> hashOps;

    private final ListOperations<String, V> listOps;

    private final SetOperations<String, V> setOps;

    private final ZSetOperations<String, V> zSetOps;

    public RedisUtils(RedisTemplate<String, V> redisTemplate) {
        Assert.notNull(redisTemplate, "redisTemplate must be set!");
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue();
        this.hashOps = redisTemplate.opsForHash();
        this.listOps = redisTemplate.opsForList();
        this.setOps = redisTemplate.opsForSet();
        this.zSetOps = redisTemplate.opsForZSet();
    }

    // ============================= 通用方法 ============================

    /**
     * 将 key 改名为 newkey . 当 key 和 newkey 相同,或者 key 不存在时,返回一个错误. 当 newkey 已经存在时, RENAME
     * 命令将覆盖旧值.
     * @param key 旧 key
     * @param newkey 新 key
     * @see <a href="https://redis.io/commands/rename">Redis Documentation: RENAME</a>
     * @see <a href="http://doc.redisfans.com/key/rename.html">Redis 命令中文文档: RENAME</a>
     */
    public void rename(String key, String newkey) {
        this.redisTemplate.rename(key, newkey);
    }

    /**
     * 为给定 key 设置生存时间,当 key 过期时(生存时间为 0 ),它会被自动删除.
     * @param key 键
     * @param time 时间(秒)
     * @return {@code true} 成功, {@code false} 失败
     * @see <a href="https://redis.io/commands/expire">Redis Documentation: EXPIRE</a>
     * @see <a href="http://doc.redisfans.com/key/expire.html">Redis 命令中文文档: EXPIRE</a>
     */
    public Boolean expire(String key, long time) {
        try {
            if (time > 0) {
                return this.redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * EXPIREAT 的作用和 EXPIRE 类似,都用于为 key 设置生存时间. 不同在于 EXPIREAT 命令接受的时间参数是 UNIX 时间戳(unix
     * timestamp).
     * @param key key
     * @param date data
     * @return 如果生存时间设置成功,返回 {@code 1} .当 key 不存在或没办法设置生存时间,返回 {@code 0} .
     * @see <a href="https://redis.io/commands/expireat">Redis Documentation: EXPIREAT</a>
     * @see <a href="http://doc.redisfans.com/key/expireat.html">Redis 命令中文文档:
     * EXPIREAT</a>
     */
    public Boolean expireAt(String key, Date date) {
        return this.redisTemplate.expireAt(key, date);
    }

    /**
     * 以秒为单位,返回给定 key 的剩余生存时间(TTL, time to live).
     * @param key 键 不能为 null
     * @return 时间(秒) 返回 0 代表为永久有效
     * @see <a href="https://redis.io/commands/ttl">Redis Documentation: TTL</a>
     * @see <a href="http://doc.redisfans.com/key/ttl.html">Redis 命令中文文档: TTL</a>
     */
    public Long getExpire(String key) {
        return this.redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 查找匹配 key.
     * @param pattern key
     * @return 数据列表
     * @see <a href="https://redis.io/commands/scan">Redis Documentation: SCAN</a>
     * @see <a href="http://doc.redisfans.com/key/scan.html">Redis 命令中文文档: SCAN</a>
     */
    public List<String> scan(String pattern) {
        ScanOptions options = ScanOptions.scanOptions().match(pattern).build();
        RedisConnectionFactory factory = this.redisTemplate.getConnectionFactory();
        RedisConnection rc = Objects.requireNonNull(factory).getConnection();
        Cursor<byte[]> cursor = rc.scan(options);
        List<String> result = new ArrayList<>();
        while (cursor.hasNext()) {
            result.add(new String(cursor.next()));
        }
        try {
            RedisConnectionUtils.releaseConnection(rc, factory);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 分页查询 key.
     * @param patternKey key
     * @param page 页码
     * @param size 每页数目
     * @return 数据列表
     * @see <a href="https://redis.io/commands/scan">Redis Documentation: SCAN</a>
     * @see <a href="http://doc.redisfans.com/key/scan.html">Redis 命令中文文档: SCAN</a>
     */
    public List<String> findKeysForPage(String patternKey, int page, int size) {
        ScanOptions options = ScanOptions.scanOptions().match(patternKey).build();
        RedisConnectionFactory factory = this.redisTemplate.getConnectionFactory();
        RedisConnection rc = Objects.requireNonNull(factory).getConnection();
        Cursor<byte[]> cursor = rc.scan(options);
        List<String> result = new ArrayList<>(size);
        int tmpIndex = 0;
        int fromIndex = page * size;
        int toIndex = page * size + size;
        while (cursor.hasNext()) {
            if (tmpIndex >= fromIndex && tmpIndex < toIndex) {
                result.add(new String(cursor.next()));
                tmpIndex++;
                continue;
            }
            // 获取到满足条件的数据后,就可以退出了
            if (tmpIndex >= toIndex) {
                break;
            }
            tmpIndex++;
            cursor.next();
        }
        try {
            RedisConnectionUtils.releaseConnection(rc, factory);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 检查给定 key 是否存在.
     * @param key 键
     * @return {@code true} 成功, {@code false} 失败
     * @see <a href="https://redis.io/commands/exists">Redis Documentation: EXISTS</a>
     * @see <a href="http://doc.redisfans.com/key/exists.html">Redis 命令中文文档: EXISTS</a>
     */
    public Boolean hasKey(String key) {
        try {
            return this.redisTemplate.hasKey(key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 删除给定的一个或多个 key .
     * @param keys 可以传一个值 或多个
     * @see <a href="https://redis.io/commands/del">Redis Documentation: DEL</a>
     * @see <a href="http://doc.redisfans.com/key/del.html">Redis 命令中文文档: DEL</a>
     */
    public void del(String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                this.redisTemplate.delete(keys[0]);
            }
            else {
                del(Arrays.asList(keys));
            }
        }
    }

    /**
     * 删除给定的一个或多个 key .
     * @param keys 可以传一个值 或多个
     * @see <a href="https://redis.io/commands/del">Redis Documentation: DEL</a>
     * @see <a href="http://doc.redisfans.com/key/del.html">Redis 命令中文文档: DEL</a>
     */
    public void del(Collection<String> keys) {
        this.redisTemplate.delete(keys);
    }

    /**
     * 将当前数据库的 key 移动到给定的数据库 db 当中. 如果当前数据库(源数据库)和给定数据库(目标数据库)有相同名字的给定 key ,或者 key
     * 不存在于当前数据库,那么 MOVE 没有任何效果. 因此,也可以利用这一特性,将 MOVE 当作锁(locking)原语(primitive).
     * @param key key
     * @param dbIndex 数据库索引
     * @return 移动成功返回 {@code 1} ,失败则返回 {@code 0} .
     * @see <a href="https://redis.io/commands/move">Redis Documentation: MOVE</a>
     * @see <a href="http://doc.redisfans.com/key/move.html">Redis 命令中文文档: MOVE</a>
     */
    public Boolean move(String key, int dbIndex) {
        return this.redisTemplate.move(key, dbIndex);
    }

    /**
     * 移除给定 key 的生存时间,将这个 key 从『易失的』(带生存时间 key )转换成『持久的』(一个不带生存时间、永不过期的 key ).
     * @param key key
     * @return 当生存时间移除成功时,返回 {@code 1} .如果 key 不存在或 key 没有设置生存时间,返回 {@code 0} .
     * @see <a href="https://redis.io/commands/persist">Redis Documentation: PERSIST</a>
     * @see <a href="http://doc.redisfans.com/key/persist.html">Redis 命令中文文档: PERSIST</a>
     */
    public Boolean persist(String key) {
        return this.redisTemplate.persist(key);
    }

    /**
     * 返回 key 所储存的值的类型.
     * @param key key
     * @return {@code none} (key不存在),{@code string} (字符串),{@code list} (列表),{@code set}
     * (集合),{@code zset} (有序集),{@code hash} (哈希表)
     * @see <a href="https://redis.io/commands/type">Redis Documentation: TYPE</a>
     * @see <a href="http://doc.redisfans.com/key/type.html">Redis 命令中文文档: TYPE</a>
     */
    public String type(String key) {
        return Objects.requireNonNull(this.redisTemplate.type(key)).code();
    }
    // ============================ String 字符串 =============================

    /**
     * 返回 key 所关联的字符串值. 假如 key 储存的值不是字符串类型,返回一个错误,因为 GET 只能用于处理字符串值.
     * @param key 键
     * @return 当 key 不存在时,返回 {@code null} ,否则,返回 key 的值.
     * @see <a href="https://redis.io/commands/get">Redis Documentation: GET</a>
     * @see <a href="http://doc.redisfans.com/string/get.html">Redis 命令中文文档: GET</a>
     */
    public V get(String key) {
        return (key != null) ? this.valueOps.get(key) : null;
    }

    /**
     * 对 key 所储存的字符串值,获取指定偏移量上的位(bit) 当 offset 比字符串值的长度大,或者 key 不存在时,返回 0 .
     * @param key 键
     * @param offset 偏移量
     * @return 字符串值指定偏移量上的位(bit).
     * @see <a href="https://redis.io/commands/getbit">Redis Documentation: GETBIT</a>
     * @see <a href="http://doc.redisfans.com/string/getbit.html">Redis 命令中文文档: MGET</a>
     */
    public Object getBit(String key, long offset) {
        return (key != null) ? this.valueOps.getBit(key, offset) : null;
    }

    /**
     * 将给定 key 的值设为 value ,并返回 key 的旧值(old value).
     * @param key 键
     * @param value 新值
     * @return 旧值,key 不存在时,返回 {@code null} .
     * @see <a href="https://redis.io/commands/getset">Redis Documentation: GETSET</a>
     * @see <a href="http://doc.redisfans.com/string/getset.html">Redis 命令中文文档: GETSET</a>
     */
    public String getAndSet(String key, V value) {
        return (key != null) ? (String) this.valueOps.getAndSet(key, value) : null;
    }

    /**
     * 返回所有(一个或多个)给定 key 的值.
     * @param keys keys
     * @return 一个包含所有给定 key 的值的列表.
     * @see <a href="https://redis.io/commands/mget">Redis Documentation: MGET</a>
     * @see <a href="http://doc.redisfans.com/string/mget.html">Redis 命令中文文档: MGET</a>
     */
    public List<V> multiGet(String... keys) {
        return this.valueOps.multiGet(Arrays.asList(keys));
    }

    /**
     * 返回所有(一个或多个)给定 key 的值.
     * @param keys keys
     * @return 一个包含所有给定 key 的值的列表.
     * @see <a href="https://redis.io/commands/mget">Redis Documentation: MGET</a>
     * @see <a href="http://doc.redisfans.com/string/mget.html">Redis 命令中文文档: MGET</a>
     */
    public List<V> multiGet(Collection<String> keys) {
        return this.valueOps.multiGet(keys);
    }

    /**
     * 将字符串值 value 关联到 key . 如果 key 已经持有其他值, SET 就覆写旧值,无视类型. 对于某个原本带有生存时间（TTL）的键来说, 当 SET
     * 命令成功在这个键上执行时, 这个键原有的 TTL 将被清除.
     * @param key 键
     * @param value 值
     * @return {@code true} 成功, {@code false} 失败
     * @see <a href="https://redis.io/commands/set">Redis Documentation: SET</a>
     * @see <a href="http://doc.redisfans.com/string/set.html">Redis 命令中文文档: SET</a>
     */
    public boolean set(String key, V value) {
        Assert.notNull(key, "key must not be null.");
        try {
            this.valueOps.set(key, value);
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 同时设置一个或多个 key-value 对. 如果某个给定 key 已经存在,那么 MSET 会用新值覆盖原来的旧值,如果这不是你所希望的效果,请考虑使用
     * MSETNX 命令：它只会在所有给定 key 都不存在的情况下进行设置操作. MSET 是一个原子性(atomic)操作,所有给定 key
     * 都会在同一时间内被设置,某些给定 key 被更新而另一些给定 key 没有改变的情况,不可能发生.
     * @param keysValues 键值对数组
     * @see <a href="https://redis.io/commands/mset">Redis Documentation: MSET</a>
     * @see <a href="http://doc.redisfans.com/string/mset.html">Redis 命令中文文档: MGET</a>
     */
    public final void multiSet(Object... keysValues) {
        this.valueOps.multiSet(Maps.toMap(keysValues));
    }

    /**
     * 将值 value 关联到 key ,并将 key 的生存时间设为 seconds (以秒为单位). 如果 key 已经存在, SETEX 命令将覆写旧值.
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time 要大于 0 如果 time 小于等于 0 将设置无限期
     * @return {@code true} 成功, {@code false} 失败
     * @see <a href="https://redis.io/commands/setex">Redis Documentation: SETEX</a>
     * @see <a href="http://doc.redisfans.com/string/setex.html">Redis 命令中文文档: SETEX</a>
     */
    public boolean setExpire(String key, V value, long time) {
        Assert.notNull(key, "key must not be null.");
        return setExpire(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 将值 value 关联到 key 如果 key 已经存在, SETEX 命令将覆写旧值.
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time 要大于 0 如果 time 小于等于 0 将设置无限期
     * @param timeUnit 类型
     * @return {@code true} 成功, {@code false} 失败
     * @see <a href="https://redis.io/commands/setex">Redis Documentation: SETEX</a>
     * @see <a href="http://doc.redisfans.com/string/setex.html">Redis 命令中文文档: SETEX</a>
     */
    public boolean setExpire(String key, V value, long time, TimeUnit timeUnit) {
        Assert.notNull(key, "key must not be null.");
        try {
            if (time > 0) {
                this.valueOps.set(key, value, time, timeUnit);
            }
            else {
                set(key, value);
            }
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 将 key 的值设为 value ,当且仅当 key 不存在.若给定的 key 已经存在,则 SETNX 不做任何动作.
     * @param key 键
     * @param value 值
     * @return 设置成功,返回 {@code 1},设置失败,返回 {@code 0}.
     * @see <a href="https://redis.io/commands/setnx">Redis Documentation: SETNX</a>
     * @see <a href="http://doc.redisfans.com/string/setnx.html">Redis Documentation:
     * SETNX</a>
     */
    public Boolean setIfAbsent(String key, V value) {
        Assert.notNull(key, "key must not be null.");
        return this.valueOps.setIfAbsent(key, value);
    }

    // ============================ Map 类型的获取与设置 =============================

    /**
     * 返回哈希表 key 中给定域 field 的值.
     * @param key 键 不能为 {@code null}
     * @param field 项 不能为 {@code null}
     * @return 给定域的值,当给定域不存在或是给定 key 不存在时,返回 {@code null} .
     * @see <a href="https://redis.io/commands/hget">Redis Documentation: HGET</a>
     * @see <a href="http://doc.redisfans.com/hash/hget.html">Redis 命令中文文档: HGET</a>
     */
    public Object hashGet(String key, String field) {
        return this.hashOps.get(key, field);
    }

    /**
     * 返回哈希表 key 中,一个或多个给定域的值.
     * @param key 键
     * @return 一个包含多个给定域的关联值的表,表值的排列顺序和给定域参数的请求顺序一样.
     * @see <a href="https://redis.io/commands/hmget">Redis Documentation: HMGET</a>
     * @see <a href="http://doc.redisfans.com/hash/hmget.html">Redis 命令中文文档: HMGET</a>
     */
    public Map<K, V> hashMGet(String key) {
        return this.hashOps.entries(key);
    }

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中.
     * @param key 键
     * @param map 对应多个键值
     * @return {@code true} 成功, {@code false} 失败
     * @see <a href="https://redis.io/commands/hmset">Redis Documentation: HMSET</a>
     * @see <a href="http://doc.redisfans.com/hash/hmset.html">Redis 命令中文文档: HMSET</a>
     */
    public boolean hashMSet(String key, Map<? extends K, ? extends V> map) {
        Assert.notNull(key, "key must not be null.");
        try {
            this.hashOps.putAll(key, map);
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中, 并设置时间.
     * @param key 键
     * @param map 对应多个键值
     * @param time 时间(秒)
     * @return {@code true} 成功, {@code false} 失败
     * @see <a href="https://redis.io/commands/hmset">Redis Documentation: HMSET</a>
     * @see <a href="https://redis.io/commands/expire">Redis Documentation: EXPIRE</a>
     * @see <a href="http://doc.redisfans.com/hash/hmset.html">Redis 命令中文文档: HMSET</a>
     * @see <a href="http://doc.redisfans.com/key/expire.html">Redis 命令中文文档: EXPIRE</a>
     */
    public boolean hashMSet(String key, Map<? extends K, ? extends V> map, long time) {
        Assert.notNull(key, "key must not be null.");
        try {
            this.hashOps.putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 将哈希表 key 中的域 field 的值设为 value .
     * @param key 键
     * @param field 项
     * @param value 值
     * @return {@code true} 成功, {@code false} 失败
     * @see <a href="https://redis.io/commands/hset">Redis Documentation: HSET</a>
     * @see <a href="http://doc.redisfans.com/hash/hset.html">Redis 命令中文文档: HSET</a>
     */
    public boolean hashSet(String key, K field, V value) {
        Assert.notNull(key, "key must not be null.");
        try {
            this.hashOps.put(key, field, value);
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 将哈希表 key 中的域 field 的值设为 value, 并设置时间.
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(秒) 注意:如果已存在的 hash 表有时间,这里将会替换原有的时间
     * @return {@code true} 成功, {@code false} 失败
     * @see <a href="https://redis.io/commands/hset">Redis Documentation: HSET</a>
     * @see <a href="https://redis.io/commands/expire">Redis Documentation: EXPIRE</a>
     * @see <a href="http://doc.redisfans.com/hash/hset.html">Redis 命令中文文档: HSET</a>
     * @see <a href="http://doc.redisfans.com/key/expire.html">Redis 命令中文文档: EXPIRE</a>
     */
    public boolean hashSet(String key, K item, V value, long time) {
        Assert.notNull(key, "key must not be null.");
        try {
            this.hashOps.put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 删除哈希表 key 中的一个或多个指定域,不存在的域将被忽略.
     * @param key 键 不能为 {@code null}
     * @param item 项 可以使多个,不能为 {@code null}
     * @return 被成功移除的域的数量,不包括被忽略的域.
     * @see <a href="https://redis.io/commands/hdel">Redis Documentation: HDEL</a>
     * @see <a href="http://doc.redisfans.com/hash/hdel.html">Redis 命令中文文档: HDEL</a>
     */
    public Long hashDel(String key, Object... item) {
        Assert.notNull(key, "key must not be null.");
        return this.hashOps.delete(key, item);
    }

    /**
     * 查看哈希表 key 中,给定域 field 是否存在.
     * @param key 键 不能为 {@code null}
     * @param field 项 不能为 {@code null}
     * @return {@code true} 成功, {@code false} 失败
     * @see <a href="https://redis.io/commands/hexists">Redis Documentation: HEXISTS</a>
     * @see <a href="http://doc.redisfans.com/hash/hexists.html">Redis 命令中文文档: HEXISTS</a>
     */
    public boolean hashHasKey(String key, String field) {
        Assert.notNull(key, "key must not be null.");
        return this.hashOps.hasKey(key, field);
    }

    /**
     * 为哈希表 key 中的域 field 的值加上增量 increment . 增量也可以为负数,相当于对给定域进行减法操作 参考
     * {@link RedisUtils#hashDecrBy(String, Object, double)} . 如果 key 不存在,一个新的哈希表被创建并执行
     * HINCRBY 命令. 如果域 field 不存在,那么在执行命令前,域的值被初始化为 0 . 对一个储存字符串值的域 field 执行 HINCRBY
     * 命令将造成一个错误. 本操作的值被限制在 64 位(bit)有符号数字表示之内.
     * @param key 键
     * @param field 项
     * @param increment 要增加几(大于0)
     * @return 哈希表 key 中域 field 的值
     * @see <a href="https://redis.io/commands/hincrby">Redis Documentation: HINCRBY</a>
     * @see <a href="http://doc.redisfans.com/hash/hincrby.html">Redis 命令中文文档: HINCRBY</a>
     */
    public double hashIncrBy(String key, K field, double increment) {
        Assert.notNull(key, "key must not be null.");
        return this.hashOps.increment(key, field, increment);
    }

    /**
     * 为哈希表 key 中的域 field 的值加上增量 increment . 增量也可以为正数,相当于对给定域进行加法操作 参考
     * {@link RedisUtils#hashIncrBy(String, Object, double)}. 如果 key 不存在,一个新的哈希表被创建并执行
     * HINCRBY 命令. 如果域 field 不存在,那么在执行命令前,域的值被初始化为 0 . 对一个储存字符串值的域 field 执行 HINCRBY
     * 命令将造成一个错误. 本操作的值被限制在 64 位(bit)有符号数字表示之内.
     * @param key 键
     * @param item 项
     * @param increment 要减少记(小于0)
     * @return 哈希表 key 中域 field 的值
     * @see <a href="https://redis.io/commands/hincrby">Redis Documentation: HINCRBY</a>
     * @see <a href="http://doc.redisfans.com/hash/hincrby.html">Redis 命令中文文档: HINCRBY</a>
     */
    public double hashDecrBy(String key, K item, double increment) {
        Assert.notNull(key, "key must not be null.");
        return this.hashOps.increment(key, item, -increment);
    }

    // ============================ set =============================

    /**
     * 返回集合 key 中的所有成员.
     * @param key 键
     * @return 集合中的所有成员.
     * @see <a href="https://redis.io/commands/smembers">Redis Documentation: SMEMBERS</a>
     * @see <a href="http://doc.redisfans.com/set/smembers.html">Redis 命令中文文档:
     * SMEMBERS</a>
     */
    public Set<V> setGet(String key) {
        Assert.notNull(key, "key must not be null.");
        try {
            return this.setOps.members(key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 判断 member 元素是否集合 key 的成员.
     * @param key 键
     * @param member 值
     * @return {@code true} 成功, {@code false} 失败
     * @see <a href="https://redis.io/commands/sismember">Redis Documentation:
     * SISMEMBER</a>
     * @see <a href="http://doc.redisfans.com/set/sismember.html">Redis 命令中文文档:
     * SISMEMBER</a>
     */
    public Boolean setHasKey(String key, Object member) {
        Assert.notNull(key, "key must not be null.");
        try {
            return this.setOps.isMember(key, member);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 将一个或多个 member 元素加入到集合 key 当中,已经存在于集合的 member 元素将被忽略. 假如 key 不存在,则创建一个只包含 member
     * 元素作成员的集合. 当 key 不是集合类型时,返回一个错误.
     * @param key 键
     * @param members 值 可以是多个
     * @return 成功个数
     * @see <a href="https://redis.io/commands/sadd">Redis Documentation: SADD</a>
     * @see <a href="http://doc.redisfans.com/set/sadd.html">Redis 命令中文文档: SADD</a>
     */
    @SafeVarargs
    public final Long setAdd(String key, V... members) {
        Assert.notNull(key, "key must not be null.");
        try {
            return this.setOps.add(key, members);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }

    /**
     * 将一个或多个 member 元素加入到集合 key 当中,已经存在于集合的 member 元素将被忽略.并设置时间.
     * @param key 键
     * @param time 时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     * @see <a href="https://redis.io/commands/sadd">Redis Documentation: SADD</a>
     * @see <a href="https://redis.io/commands/expire">Redis Documentation: EXPIRE</a>
     * @see <a href="http://doc.redisfans.com/set/sadd.html">Redis 命令中文文档: SADD</a>
     * @see <a href="http://doc.redisfans.com/key/expire.html">Redis 命令中文文档: EXPIRE</a>
     */
    @SafeVarargs
    public final Long setAddAndExpire(String key, long time, V... values) {
        Assert.notNull(key, "key must not be null.");
        try {
            Long count = this.setOps.add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }

    /**
     * 返回集合 key 的个数(集合中元素的数量).
     * @param key 键
     * @return 集合的个数.当 key 不存在时,返回 0 .
     * @see <a href="https://redis.io/commands/scard">Redis Documentation: SCARD</a>
     * @see <a href="http://doc.redisfans.com/set/scard.html">Redis 命令中文文档: SCARD</a>
     */
    public Long setCard(String key) {
        Assert.notNull(key, "key must not be null.");
        try {
            return this.setOps.size(key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }

    /**
     * 移除集合 key 中的一个或多个 member 元素,不存在的 member 元素会被忽略.
     * @param key 键
     * @param member 值 可以是多个
     * @return 移除的个数
     * @see <a href="https://redis.io/commands/srem">Redis Documentation: SREM</a>
     * @see <a href="http://doc.redisfans.com/set/srem.html">Redis 命令中文文档: SREM</a>
     */
    public Long setRem(String key, Object... member) {
        Assert.notNull(key, "key must not be null.");
        try {
            return this.setOps.remove(key, member);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }

    // ============================ zset =============================

    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中. 如果某个 member 已经是有序集的成员,那么更新这个 member 的
     * score 值,并通过重新插入这个 member 元素,来保证该 member 在正确的位置上.
     * @param key 如果 key 不存在,则创建一个空的有序集并执行 ZADD 操作.当 key 存在但不是有序集类型时,返回一个错误.
     * @param member 元素
     * @param score score 值可以是整数值或双精度浮点数.
     * @return 被成功添加的新成员的数量,不包括那些被更新的、已经存在的成员.
     * @see <a href="https://redis.io/commands/zadd">Redis Documentation: ZADD</a>
     * @see <a href="http://doc.redisfans.com/sorted_set/zadd.html">Redis 命令中文文档: ZADD</a>
     */
    public Boolean zAdd(String key, V member, double score) {
        Assert.notNull(key, "key must not be null.");
        return this.zSetOps.add(key, member, score);
    }

    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中. 如果某个 member 已经是有序集的成员,那么更新这个 member 的
     * score 值,并通过重新插入这个 member 元素,来保证该 member 在正确的位置上.
     * @param key 如果 key 不存在,则创建一个空的有序集并执行 ZADD 操作.当 key 存在但不是有序集类型时,返回一个错误.
     * @param scoreMembers 元素
     * @return 被成功添加的新成员的数量,不包括那些被更新的、已经存在的成员.
     * @see <a href="https://redis.io/commands/zadd">Redis Documentation: ZADD</a>
     * @see <a href="http://doc.redisfans.com/sorted_set/zadd.html">Redis 命令中文文档: ZADD</a>
     */
    public Long zAdd(String key, Map<V, Double> scoreMembers) {
        Assert.notNull(key, "key must not be null.");
        Set<ZSetOperations.TypedTuple<V>> tuples = new HashSet<>();
        scoreMembers.forEach((k, v) -> tuples.add(new DefaultTypedTuple<>(k, v)));
        return this.zSetOps.add(key, tuples);
    }

    /**
     * 返回有序集 key 的个数.
     * @param key 如果 key 不存在,则创建一个空的有序集并执行 ZADD 操作.当 key 存在但不是有序集类型时,返回一个错误.
     * @return 当 key 存在且是有序集类型时,返回有序集的个数.当 key 不存在时,返回 0 .
     * @see <a href="https://redis.io/commands/zcard">Redis Documentation: ZCARD</a>
     * @see <a href="http://doc.redisfans.com/sorted_set/zcard.html">Redis 命令中文文档:
     * ZCARD</a>
     */
    public Long zCard(String key) {
        Assert.notNull(key, "key must not be null.");
        return this.zSetOps.zCard(key);
    }

    /**
     * 返回有序集 key 中, score 值在 {@code min} 和 {@code max} 之间(默认包括 score 值等于 min 或 max
     * )的成员的数量. 关于参数 min 和 max 的详细使用方法,请参考 <a href=
     * "http://doc.redisfans.com/sorted_set/zrangebyscore.html#zrangebyscore">ZRANGEBYSCORE</a>.
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return score 值在 min 和 max 之间的成员的数量.
     * @see <a href="https://redis.io/commands/zcount">Redis Documentation: ZCOUNT</a>
     * @see <a href="http://doc.redisfans.com/sorted_set/zadd.html">Redis 命令中文文档:
     * ZCOUNT</a>
     */
    public Long zCount(String key, double min, double max) {
        Assert.notNull(key, "key must not be null.");
        return this.zSetOps.count(key, min, max);
    }

    /**
     * 为有序集 key 的成员 member 的 score 值加上增量 {@code increment} . 可以通过传递一个负数值 {@code increment}
     * ,让 score 减去相应的值
     * <p>
     * ZINCRBY key -5 member ,就是让 member 的 score 值减去 5 .
     * @param key 当 key 不存在,或 member 不是 key 的成员时, ZINCRBY key increment member 等同于 ZADD
     * key increment member .当 key 不是有序集类型时,返回一个错误.
     * @param member 元素
     * @param score score 值可以是整数值或双精度浮点数.
     * @return member 成员的新 score 值,以字符串形式表示.
     * @see <a href="https://redis.io/commands/zincrby">Redis Documentation: ZINCRBY</a>
     * @see <a href="http://doc.redisfans.com/sorted_set/zincrby.html">Redis 命令中文文档:
     * ZINCRBY</a>
     */
    public Double zIncrBy(String key, V member, double score) {
        Assert.notNull(key, "key must not be null.");
        return this.zSetOps.incrementScore(key, member, score);
    }

    /**
     * 返回有序集 key 中,指定区间内的成员. 其中成员的位置按 score 值递增(从小到大)来排序.如果需要按 score 值递减(从大到小),可以使用
     * {@link RedisUtils#zRevrange(String, long, long)} 下标参数 {@code start} 和 {@code stop}
     * 都以 0 为底,也就是说,以 0 表示有序集第一个成员,以 1 表示有序集第二个成员,以此类推. 你也可以使用负数下标,以 -1 表示最后一个成员, -2
     * 表示倒数第二个成员,以此类推.
     * @param key key
     * @param start 开始位置
     * @param end 结束位置
     * @return 指定区间内,带有 score 值(可选)的有序集成员的列表.
     * @see <a href="https://redis.io/commands/zrange">Redis Documentation: ZRANGE</a>
     * @see <a href="http://doc.redisfans.com/sorted_set/zrange.html">Redis 命令中文文档:
     * ZRANGE</a>
     */
    public Set<V> zRange(String key, long start, long end) {
        Assert.notNull(key, "key must not be null.");
        return this.zSetOps.range(key, start, end);
    }

    /**
     * 返回有序集 key 中,指定区间内的成员. 其中成员的位置按 score 值递减(从大到小)来排序.如果需要按 score 值递增(从小到大),可以使用
     * {@link RedisUtils#zRange(String, long, long)} 下标参数 {@code start} 和 {@code stop} 都以
     * 0 为底,也就是说,以 0 表示有序集第一个成员,以 1 表示有序集第二个成员,以此类推. 你也可以使用负数下标,以 -1 表示最后一个成员, -2
     * 表示倒数第二个成员,以此类推.
     * @param key key
     * @param start 开始位置
     * @param end 结束位置
     * @return 指定区间内,带有 score 值(可选)的有序集成员的列表.
     * @see <a href="https://redis.io/commands/zrevrange">Redis Documentation:
     * ZREVRANGE</a>
     * @see <a href="http://doc.redisfans.com/sorted_set/zrevrange.html">Redis 命令中文文档:
     * ZREVRANGE</a>
     */
    public Set<V> zRevrange(String key, long start, long end) {
        Assert.notNull(key, "key must not be null.");
        return this.zSetOps.reverseRange(key, start, end);
    }

    /**
     * 返回有序集 key 中,所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员.有序集成员按 score
     * 值递增(从小到大)次序排列. 具有相同 score 值的成员按字典序(lexicographical order)来排列(该属性是有序集提供的,不需要额外的计算).
     * @param key 如果 key 不存在,则创建一个空的有序集并执行 ZADD 操作.当 key 存在但不是有序集类型时,返回一个错误.
     * @param min score 最小值
     * @param max score 最大值
     * @return 指定区间内,带有 score 值(可选)的有序集成员的列表.
     * @see <a href="https://redis.io/commands/zrangebyscore">Redis Documentation:
     * ZRANGEBYSCORE</a>
     * @see <a href="http://doc.redisfans.com/sorted_set/zrangebyscore.html">Redis 命令中文文档:
     * ZRANGEBYSCORE</a>
     */
    public Set<V> zRangeByScore(String key, double min, double max) {
        Assert.notNull(key, "key must not be null.");
        return this.zSetOps.rangeByScore(key, min, max);
    }

    /**
     * 返回有序集 key 中成员 member 的排名.其中有序集成员按 score 值递增(从小到大)顺序排列. 排名以 0 为底,也就是说, score
     * 值最小的成员排名为 0.
     * @param key key
     * @param member 元素
     * @return 如果 member 是有序集 key 的成员,返回 member 的排名.如果 member 不是有序集 key 的成员,返回
     * {@code null}
     * @see <a href="https://redis.io/commands/zrank">Redis Documentation: ZRANK</a>
     * @see <a href="http://doc.redisfans.com/sorted_set/zrank.html">Redis 命令中文文档:
     * ZRANK</a>
     */
    public Long zRank(String key, V member) {
        Assert.notNull(key, "key must not be null.");
        return this.zSetOps.rank(key, member);
    }

    /**
     * 返回有序集 key 中成员 member 的排名.其中有序集成员按 score 值递减(从大到小)排列的排名.
     * @param key key
     * @param member 元素
     * @return 如果 member 是有序集 key 的成员,返回 member 的排名.如果 member 不是有序集 key 的成员,返回
     * {@code null}
     * @see <a href="https://redis.io/commands/zrevrank">Redis Documentation: ZREVRANK</a>
     * @see <a href="http://doc.redisfans.com/sorted_set/zrevrank.html">Redis 命令中文文档:
     * ZREVRANK</a>
     */
    public Long zRevrank(String key, Object member) {
        Assert.notNull(key, "key must not be null.");
        return this.zSetOps.reverseRank(key, member);
    }

    /**
     * 移除有序集 key 中的一个或多个成员,不存在的成员将被忽略.
     *
     * <p>
     * <b>在 Redis 2.4 版本以前, ZREM 每次只能删除一个元素.</b>
     * </p>
     * @param key key 存在但不是有序集类型时,返回一个错误.
     * @param members 元素
     * @return 被成功移除的成员的数量,不包括被忽略的成员.
     * @see <a href="https://redis.io/commands/zrem">Redis Documentation: ZREM</a>
     * @see <a href="http://doc.redisfans.com/sorted_set/zrem.html">Redis 命令中文文档: ZREM</a>
     */
    public Long zRem(String key, Object... members) {
        Assert.notNull(key, "key must not be null.");
        return this.zSetOps.remove(key, members);
    }

    /**
     * 返回有序集 key 中,成员 member 的 score 值.
     * @param key key 不存在,返回 {@code null}
     * @param member 如果 member 元素不是有序集 key 的成员,返回 {@code null}.
     * @return member 成员的 score 值,以字符串形式表示.
     * @see <a href="https://redis.io/commands/zscore">Redis Documentation: ZSCORE</a>
     * @see <a href="http://doc.redisfans.com/sorted_set/zscore.html">Redis 命令中文文档:
     * ZSCORE</a>
     */
    public Double zScore(String key, Object member) {
        Assert.notNull(key, "key must not be null.");
        return this.zSetOps.score(key, member);
    }

    // =============================== list =================================

    /**
     * 返回列表 key 中指定区间内的元素,区间以偏移量 start 和 stop 指定. 标(index)参数 {@code index >= 0 } 时, 0
     * 表示列表的第一个元素,1 表示列表的第二个元素,依次类推;{@code index<0}时, -1 表示列表的最后一个元素, -2
     * 表示列表的倒数第二个元素,以此类推.
     * @param key 键
     * @param start 开始
     * @param stop 结束 0 到 -1代表所有值
     * @return 一个列表,包含指定区间内的元素.
     * @see <a href="https://redis.io/commands/lrange">Redis Documentation: LRANGE</a>
     * @see <a href="http://doc.redisfans.com/list/lrange.html">Redis 命令中文文档: LRANGE</a>
     */
    public List<V> listGet(String key, long start, long stop) {
        Assert.notNull(key, "key must not be null.");
        try {
            return this.listOps.range(key, start, stop);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 返回列表 key 的长度.如果 key 不存在,则 key 被解释为一个空列表,返回 0 .如果 key 不是列表类型,返回一个错误.
     * @param key 键
     * @return 列表 key 的长度.
     * @see <a href="https://redis.io/commands/llen">Redis Documentation: LLEN</a>
     * @see <a href="http://doc.redisfans.com/list/llen.html">Redis 命令中文文档: LLEN</a>
     */
    public Long listLen(String key) {
        Assert.notNull(key, "key must not be null.");
        try {
            return this.listOps.size(key);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }

    /**
     * 返回列表 key 中,下标为 index 的元素.
     * @param key 键
     * @param index 索引 {@code index >= 0 } 时, 0 表头,1
     * 第二个元素,依次类推;{@code index<0}时,-1,表尾,-2倒数第二个元素,依次类推
     * @return 列表中下标为 index 的元素.如果 index 参数的值不在列表的区间范围内(out of range),返回 {@code null} .
     * @see <a href="https://redis.io/commands/lindex">Redis Documentation: LINDEX</a>
     * @see <a href="http://doc.redisfans.com/list/lindex.html">Redis 命令中文文档: LINDEX</a>
     */
    public Object listIndex(String key, long index) {
        Assert.notNull(key, "key must not be null.");
        try {
            return this.listOps.index(key, index);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 将一个值 value 插入到列表 key 的表尾(最右边).
     * @param key 键
     * @param value 值
     * @return {@code true} 成功, {@code false} 失败
     * @see <a href="https://redis.io/commands/rpush">Redis Documentation: RPUSH</a>
     * @see <a href="http://doc.redisfans.com/list/rpush.html">Redis 命令中文文档: RPUSH</a>
     */
    public boolean listRightPush(String key, V value) {
        Assert.notNull(key, "key must not be null.");
        try {
            this.listOps.rightPush(key, value);
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 将一个值 value 插入到列表 key 的表尾(最右边).并设置过期时间.
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return {@code true} 成功, {@code false} 失败
     * @see <a href="https://redis.io/commands/rpush">Redis Documentation: RPUSH</a>
     * @see <a href="https://redis.io/commands/expire">Redis Documentation: EXPIRE</a>
     * @see <a href="http://doc.redisfans.com/list/rpush.html">Redis 命令中文文档: RPUSH</a>
     * @see <a href="http://doc.redisfans.com/key/expire.html">Redis 命令中文文档: EXPIRE</a>
     */
    public boolean listRightPushAndExpire(String key, V value, long time) {
        Assert.notNull(key, "key must not be null.");
        try {
            this.listOps.rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边).
     * @param key 键
     * @param value 值
     * @return {@code true} 成功, {@code false} 失败
     * @see <a href="https://redis.io/commands/rpush">Redis Documentation: RPUSH</a>
     * @see <a href="http://doc.redisfans.com/list/rpush.html">Redis 命令中文文档: RPUSH</a>
     */
    public boolean listRightPushAll(String key, List<V> value) {
        Assert.notNull(key, "key must not be null.");
        try {
            this.listOps.rightPushAll(key, value);
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边).并设置过期时间.
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return {@code true} 成功, {@code false} 失败
     * @see <a href="https://redis.io/commands/hexists">Redis Documentation: HEXISTS</a>
     * @see <a href="https://redis.io/commands/expire">Redis Documentation: EXPIRE</a>
     * @see <a href="http://doc.redisfans.com/hash/hexists.html">Redis 命令中文文档: HEXISTS</a>
     * @see <a href="http://doc.redisfans.com/key/expire.html">Redis 命令中文文档: EXPIRE</a>
     */
    public boolean listRightPushAllAndExpire(String key, List<V> value, long time) {
        Assert.notNull(key, "key must not be null.");
        try {
            this.listOps.rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 将列表 key 下标为 index 的元素的值设置为 value . 当 index 参数超出范围,或对一个空列表( key 不存在)进行 LSET
     * 时,返回一个错误.
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return {@code true} 成功, {@code false} 失败
     * @see <a href="https://redis.io/commands/lset">Redis Documentation: LSET</a>
     * @see <a href="http://doc.redisfans.com/list/lset.html">Redis 命令中文文档: LSET</a>
     */
    public boolean listSet(String key, long index, V value) {
        Assert.notNull(key, "key must not be null.");
        try {
            this.listOps.set(key, index, value);
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 根据参数 count 的值,移除列表中与参数 value 相等的元素. count 的值可以是以下几种：
     *
     * <ul>
     * <li>count &gt; 0 : 从表头开始向表尾搜索,移除与 value 相等的元素,数量为 count .</li>
     * <li>count &lt; 0 : 从表尾开始向表头搜索,移除与 value 相等的元素,数量为 count 的绝对值.</li>
     * <li>count = 0 : 移除表中所有与 value 相等的值.</li>
     * </ul>
     * .
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 被移除元素的数量.因为不存在的 key 被视作空表(empty list),所以当 key 不存在时, LREM 命令总是返回 0 .
     * @see <a href="https://redis.io/commands/lrem">Redis Documentation: LREM</a>
     * @see <a href="http://doc.redisfans.com/list/lrem.html">Redis 命令中文文档: LREM</a>
     */
    public Long listRemove(String key, long count, V value) {
        Assert.notNull(key, "key must not be null.");
        try {
            return this.listOps.remove(key, count, value);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }

    /**
     * 命令 RPOPLPUSH 在一个原子时间内,执行以下两个动作：
     * <ul>
     * <li>将列表 source 中的最后一个元素(尾元素)弹出,并返回给客户端.</li>
     * <li>将 source 弹出的元素插入到列表 destination ,作为 destination 列表的的头元素.</li>
     * </ul>
     * 如果 source 不存在,值 {@code null} 被返回,并且不执行其他动作. 如果 source 和 destination
     * 相同,则列表中的表尾元素被移动到表头,并返回该元素,可以把这种特殊情况视作列表的旋转(rotation)操作.
     * @param source 源 key
     * @param destination 目标 key
     * @return 被弹出的元素.
     * @see <a href="https://redis.io/commands/rpoplpush">Redis Documentation:
     * RPOPLPUSH</a>
     * @see <a href="http://doc.redisfans.com/list/rpoplpush.html">Redis 命令中文文档:
     * RPOPLPUSH</a>
     */
    public Object listRightPopLeftPush(String source, String destination) {
        return this.listOps.rightPopAndLeftPush(source, destination);
    }

    /**
     * 根据key删除.
     * @param prefix 前缀
     * @param ids id
     * @see <a href="https://redis.io/commands/hexists">Redis Documentation: HEXISTS</a>
     * @see <a href="http://doc.redisfans.com/hash/hexists.html">Redis 命令中文文档: HEXISTS</a>
     */
    public void delByKeys(String prefix, Set<Long> ids) {
        Set<String> keys = new HashSet<>();
        for (Long id : ids) {
            keys.addAll(Objects.requireNonNull(this.redisTemplate.keys(prefix + id)));
        }
        Long count = this.redisTemplate.delete(keys);
        // 此处提示可自行删除
        logger.debug("--------------------------------------------");
        logger.debug("成功删除缓存：" + keys.toString());
        logger.debug("缓存删除数量：" + count + "个");
        logger.debug("--------------------------------------------");
    }

}
