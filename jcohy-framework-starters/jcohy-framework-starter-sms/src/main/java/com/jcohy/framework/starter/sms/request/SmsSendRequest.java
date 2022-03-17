package com.jcohy.framework.starter.sms.request;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/17/22:10:34
 * @since 2022.0.1
 */
public class SmsSendRequest extends SmsRequest {

    private String phones;

    private String signs;

    private String templateCode;

    private Map<String,String> templateParams;

    private final Map<String, Supplier<String>> valueSupplier = new HashMap<>();


    public SmsSendRequest(String action) {
        super(action);
    }

    public SmsSendRequest phones(String phones) {
        Assert.notNull(phones, "手机号不能为空");
        this.phones = phones;
        return this;
    }

    public SmsSendRequest phones(Collection<String> phones) {
        Assert.notNull(phones, "手机号不能为空");
        this.phones = StringUtils.collectionToCommaDelimitedString(phones);
        return this;
    }

    public SmsSendRequest phones(String... phones) {
        Assert.notNull(phones, "手机号不能为空");
        this.phones = StringUtils.arrayToCommaDelimitedString(phones);
        return this;
    }

    public SmsSendRequest signs(String signs) {
        Assert.notNull(signs, "短信签名不能为空");
        this.signs = signs;
        return this;
    }

    public SmsSendRequest signs(Collection<String> signs) {
        Assert.notNull(signs, "手机号不能为空");
        this.signs = StringUtils.collectionToCommaDelimitedString(signs);
        return this;
    }

    public SmsSendRequest signs(String... signs) {
        Assert.notNull(signs, "手机号不能为空");
        this.signs = StringUtils.arrayToCommaDelimitedString(signs);
        return this;
    }

    public SmsSendRequest templateCode(String templateCode) {
        Assert.notNull(templateCode, "模版编码不能为空");
        this.templateCode = templateCode;
        return this;
    }

    public SmsSendRequest templateParams(String key, String value) {
        Assert.notNull(key, "模板变量的 key 不能为空");
        Assert.notNull(value, "模板变量的 value 不能为空");
        this.templateParams.put(key,value);
        return this;
    }

    public SmsSendRequest templateParams(String key, Supplier<String> supplier) {
        Assert.notNull(key, "模板变量的 key 不能为空");
        Assert.notNull(supplier, "模板变量的 supplier 不能为空");
        this.templateParams.put(key, supplier.get());
        return this;
    }

    public SmsSendRequest templateParams(Map<String, String> params) {
        Assert.notNull(params, "params must be set");
        this.templateParams.putAll(params);
        return this;
    }


    /**
     * 设置是否需要验证.
     * @param validate 是否需要验证
     * @return {@link SmsSendRequest}
     */
    public SmsSendRequest validate(boolean validate) {
        setValidate(validate);
        return this;
    }

    public String getPhones() {
        return this.phones;
    }

    public String getSigns() {
        return this.signs;
    }

    public String getTemplateCode() {
        return this.templateCode;
    }

    public Map<String, String> getTemplateParams() {
        return templateParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmsSendRequest that = (SmsSendRequest) o;
        return Objects.equals(getPhones(), that.getPhones()) && Objects.equals(getSigns(), that.getSigns()) && Objects.equals(getTemplateCode(), that.getTemplateCode()) && Objects.equals(getTemplateParams(), that.getTemplateParams());
    }

    /**
     * TODO 替换方法
     * @return /
     */
    @Override
    public int hashCode() {
        return Objects.hash(getPhones(), getSigns(), getTemplateCode(), getTemplateParams());
    }
}
