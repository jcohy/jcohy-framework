package com.jcohy.framework.starter.redis.serializer;

import java.nio.charset.StandardCharsets;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 描述: 重写 FastJsonRedisSerializer 序列化器.
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @param <T> 范型类型
 * @author jiac
 * @version 2022.0.1 3/14/22:17:11
 * @since 2022.0.1
 */
public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {

    private ObjectMapper mapper = new ObjectMapper();

    private final Class<T> clazz;

    static {

        // 全局开启 AutoType，这里方便开发，使用全局的方式
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        // 建议使用这种方式，小范围指定白名单.
        // 如果遇到反序列化 autoType is not support 错误，请添加并修改一下报名到 bean 的路径
        // ParserConfig.getGlobalInstance().addAccept("com.jcohy.framework");
    }

    public FastJson2JsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public T deserialize(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, StandardCharsets.UTF_8);
        return JSON.parseObject(str, this.clazz);
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    protected JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }

}
