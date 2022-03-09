package com.jcohy.framework.utils.api;

/**
 * 描述: HTTP 状态码.
 *
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2/18/22:15:06
 * @since 2022.0.1
 */
public enum HttpStatusCode implements ResultStatusCode {

    /**
     * 200 操作成功.
     */
    SUCCESS(ResultStatusCode.SC_OK, "操作成功"),

    /**
     * 401 请求未授权.
     */
    UN_AUTHORIZED(ResultStatusCode.SC_UNAUTHORIZED, "请求未授权"),

    /**
     * 404 没找到请求.
     */
    NOT_FOUND(ResultStatusCode.SC_NOT_FOUND, "404 没找到请求"),

    /**
     * 400 消息不能读取.
     */
    MSG_NOT_READABLE(ResultStatusCode.SC_BAD_REQUEST, "消息不能读取"),

    /**
     * 405 不支持当前请求方法.
     */
    METHOD_NOT_SUPPORTED(ResultStatusCode.SC_METHOD_NOT_ALLOWED, "不支持当前请求方法"),

    /**
     * 415 不支持当前媒体类型.
     */
    MEDIA_TYPE_NOT_SUPPORTED(ResultStatusCode.SC_UNSUPPORTED_MEDIA_TYPE, "不支持当前媒体类型"),

    /**
     * 403 请求被拒绝.
     */
    REQ_REJECT(ResultStatusCode.SC_FORBIDDEN, "请求被拒绝"),

    /**
     * 500 服务器异常.
     */
    INTERNAL_SERVER_ERROR(ResultStatusCode.SC_INTERNAL_SERVER_ERROR, "服务器异常"),

    /**
     * 400.
     */
    PARAM_VALID_ERROR(ResultStatusCode.SC_BAD_REQUEST, "参数校验失败");

    /**
     * code编码.
     */
    final int code;

    /**
     * 中文信息描述.
     */
    final String message;

    HttpStatusCode(int code, String message) {
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
