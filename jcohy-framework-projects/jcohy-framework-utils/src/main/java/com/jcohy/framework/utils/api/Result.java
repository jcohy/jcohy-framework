package com.jcohy.framework.utils.api;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 描述: 响应结果构建.
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @param <T> 实体类型
 * @author jiac
 * @version 2022.0.1 2/18/22:15:04
 * @since 2022.0.1
 */
@ApiModel(description = "返回信息")
public final class Result<T> implements Serializable {

    private static final String DEFAULT_NULL_MESSAGE = "暂无承载数据";

    private Result() {
    }

    @ApiModelProperty(value = "状态码", required = true)
    private int code;

    @ApiModelProperty(value = "返回消息", required = true)
    private String message;

    @ApiModelProperty(value = "是否成功", required = true)
    private boolean success;

    @ApiModelProperty(value = "返回数据", required = true)
    private T data;

    /**
     * 构造函数.
     * @param resultCode 返回值状态码
     */
    public Result(ResultStatusCode resultCode) {
        this(resultCode, resultCode.getMessage());
    }

    /**
     * 构造函数.
     * @param resultCode 返回值状态码
     * @param message 消息
     */
    private Result(ResultStatusCode resultCode, String message) {
        this(resultCode, message, null);
    }

    /**
     * 构造函数.
     * @param resultCode 返回值状态码
     * @param data 数据
     */
    public Result(ResultStatusCode resultCode, T data) {
        this(resultCode, resultCode.getMessage(), data);
    }

    /**
     * 构造函数,可以使用自定义 message 替换 {@code ResultStatusCode} 中的 message.
     * @param resultCode 返回值状态码
     * @param message 消息
     * @param data 数据
     */
    public Result(ResultStatusCode resultCode, String message, T data) {
        this(resultCode.getCode(), message, data);
    }

    /**
     * 构造函数,当 code 为 200 或者 10000 时 success 为 true.
     * @param resultCode 状态码
     * @param message 消息
     * @param data 数据
     */
    public Result(Integer resultCode, String message, T data) {
        this.code = resultCode;
        this.message = message;
        this.data = data;
        this.success = HttpStatusCode.SUCCESS.code == this.code || ServiceStatusCode.SUCCESS.getCode() == this.code;
    }

    /**
     * 返回 Result，默认为 返回 "ServiceStatusCode.SUCCESS.getMessage()".
     * @param data 数据
     * @param <T> 泛型标记
     * @return /
     */
    public static <T> Result<T> data(T data) {
        return data(ServiceStatusCode.SUCCESS.getMessage(), data);
    }

    /**
     * 返回 Result.
     * @param data 数据
     * @param msg 消息
     * @param <T> 泛型标记
     * @return /
     */
    public static <T> Result<T> data(String msg, T data) {
        return data(ServiceStatusCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * 返回 Result.
     * @param code 状态码
     * @param data 数据
     * @param msg 消息
     * @param <T> 泛型标记
     * @return /
     */
    public static <T> Result<T> data(int code, String msg, T data) {
        return new Result<>(code, (data != null) ? msg : DEFAULT_NULL_MESSAGE, data);
    }

    /**
     * 成功时返回 Result.
     * @param resultCode resultCode
     * @param <T> 泛型数据
     * @return 结果
     */
    public static <T> Result<T> success(ResultStatusCode resultCode) {
        return new Result<>(resultCode);
    }

    /**
     * 成功时返回，替换 message 消息.
     * @param message 消息
     * @param <T> 泛型数据
     * @return 结果
     */
    public static <T> Result<T> success(String message) {
        return new Result<>(ServiceStatusCode.SUCCESS, message);
    }

    /**
     * 成功时返回.
     * @param data 数据
     * @param <T> 泛型数据
     * @return 结果
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ServiceStatusCode.SUCCESS, data);
    }

    /**
     * 成功时返回，替换 ResultStatusCode message 中的消息.
     * @param resultCode resultCode
     * @param message 消息
     * @param <T> 泛型数据
     * @return 结果
     */
    public static <T> Result<T> success(ResultStatusCode resultCode, String message) {
        return new Result<>(resultCode, message);
    }

    /**
     * 成功时返回.
     * @param resultCode resultCode
     * @param data 数据
     * @param <T> 泛型数据
     * @return 结果
     */
    public static <T> Result<T> success(ResultStatusCode resultCode, T data) {
        return new Result<>(resultCode, data);
    }

    /**
     * 失败时返回.
     * @param message 消息
     * @param <T> 泛型数据
     * @return 结果
     */
    public static <T> Result<T> fail(String message) {
        return new Result<>(ServiceStatusCode.FAIL, message);
    }

    /**
     * 失败时返回.
     * @param code code
     * @param message 消息
     * @param <T> 泛型数据
     * @return 结果
     */
    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 失败时返回.
     * @param resultCode resultCode
     * @param <T> 泛型数据
     * @return 结果
     */
    public static <T> Result<T> fail(ResultStatusCode resultCode) {
        return new Result<>(resultCode);
    }

    /**
     * 失败时返回 R.
     * @param resultCode resultCode
     * @param message 消息
     * @param <T> 泛型数据
     * @return 结果
     */
    public static <T> Result<T> fail(ResultStatusCode resultCode, String message) {
        return new Result<>(resultCode, message);
    }

    /**
     * 返回.
     * @param <T> 泛型数据
     * @param flag 返回状态
     * @return 结果
     */
    public static <T> Result<T> status(boolean flag) {
        return flag ? success(ServiceStatusCode.SUCCESS) : fail(ServiceStatusCode.FAIL);
    }

    public Integer getCode() {
        return this.code;
    }

    public Result<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public Result<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    @Override
    public String toString() {
        return "R{" + "code=" + this.code + ", message='" + this.message + '\'' + ", data=" + this.data + ", success="
                + this.success + '}';
    }

}
