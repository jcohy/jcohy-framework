package com.jcohy.framework.starter.sms.response;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/29/22:15:15
 * @since 2022.0.1
 */
public class SmsResponse {

    private String requestId;

    private String bizId;

    private String message;

    private String code;

    private Object data;

    public String getRequestId() {
        return this.requestId;
    }

    public SmsResponse setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public String getBizId() {
        return this.bizId;
    }

    public SmsResponse setBizId(String bizId) {
        this.bizId = bizId;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public SmsResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public SmsResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public Object getData() {
        return this.data;
    }

    public SmsResponse setData(Object data) {
        this.data = data;
        return this;
    }

}
