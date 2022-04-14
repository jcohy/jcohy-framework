package com.jcohy.framework.starter.redis.serializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.alibaba.fastjson.JSON;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import com.jcohy.framework.utils.StringUtils;

/**
 * 描述: 重写 StringRedisSerializer 序列器.
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/14/22:17:13
 * @since 2022.0.1
 */
public class StringRedisSerializer implements RedisSerializer<Object> {

    private final Charset charset;

    public StringRedisSerializer() {
        this(StandardCharsets.UTF_8);
    }

    private StringRedisSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    @Override
    public String deserialize(byte[] bytes) {
        return (bytes != null) ? new String(bytes, this.charset) : null;
    }

    @Override
    public byte[] serialize(Object object) {
        String string = JSON.toJSONString(object);
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        string = string.replace("\"", "");
        return string.getBytes(this.charset);
    }

}
