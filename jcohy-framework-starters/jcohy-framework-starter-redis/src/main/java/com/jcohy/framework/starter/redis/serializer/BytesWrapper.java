package com.jcohy.framework.starter.redis.serializer;

/**
 * 描述: redis 序列化辅助类.单纯的泛型无法定义通用schema，原因是无法通过泛型 T 得到 Class.
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
public class BytesWrapper<T> {

    private T value;

    public BytesWrapper() {
    }

    public BytesWrapper(T value) {
        this.value = value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public BytesWrapper<T> clone() {
        try {
            return (BytesWrapper) super.clone();
        }
        catch (CloneNotSupportedException ex) {
            return new BytesWrapper<>();
        }
    }

}
