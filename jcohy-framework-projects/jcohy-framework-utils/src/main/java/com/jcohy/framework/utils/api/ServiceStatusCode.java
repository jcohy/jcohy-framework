package com.jcohy.framework.utils.api;

/**
 * 描述: 业务状态码.
 *
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2/18/22:15:07
 * @since 2022.0.1
 */
public enum ServiceStatusCode implements ResultStatusCode {

    /**
     * 10000 操作成功.
     */
    SUCCESS(10000, "操作成功"),

    /**
     * 99999 其他错误.
     */
    FAIL(99999, "其他错误");

    private final Integer code;

    private final String message;

    ServiceStatusCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
