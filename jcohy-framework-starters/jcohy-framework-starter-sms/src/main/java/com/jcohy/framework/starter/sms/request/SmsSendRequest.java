package com.jcohy.framework.starter.sms.request;

import com.jcohy.framework.starter.sms.SmsAction;
import org.apache.commons.lang3.Validate;

import java.util.*;
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

    /**
     * 手机号列表.
     */
    private Collection<String> phones;

    /**
     * 签名列表.
     */
    private Collection<String> signs;

    /**
     * 模版 Id.
     */
    private String templateCode;

    /**
     * 模版参数.
     * @see #templateParams(Map)
     * @see #templateParams(String, String)
     */
    private Map<String,String> templateParams = new LinkedHashMap<>();

    /**
     * 模版参数自定义.
     * 当模版中有参数是动态的，可以提供一个函数来替代。
     * @see #templateParams(String, Supplier)
     */
    private final Map<String, Supplier<String>> valueSupplier = new HashMap<>();


    public SmsSendRequest(SmsAction action) {
        super(action);
    }

    public SmsSendRequest phones(String phones) {
        Validate.notEmpty(phones, "手机号不能为空!");
        this.phones = Collections.singletonList(phones);
        return this;
    }

    public SmsSendRequest phones(Collection<String> phones) {
        Validate.notEmpty(phones, "手机号不能为空!");
        this.phones = phones;
        return this;
    }

    public SmsSendRequest phones(String... phones) {
        Validate.notEmpty(phones, "手机号不能为空!");
        this.phones = Arrays.asList(phones);
        return this;
    }

    public SmsSendRequest signs(String signs) {
        Validate.notEmpty(signs, "短信签名不能为空!");
        this.signs = Collections.singletonList(signs);
        return this;
    }

    public SmsSendRequest signs(Collection<String> signs) {
        Validate.notEmpty(signs, "短信签名不能为空!");
        this.signs = signs;
        return this;
    }

    public SmsSendRequest signs(String... signs) {
        Validate.notEmpty(signs, "短信签名不能为空!");
        this.signs = Arrays.asList(signs);
        return this;
    }

    public SmsSendRequest templateCode(String templateCode) {
        Validate.notEmpty(templateCode, "模版编码不能为空!");
        this.templateCode = templateCode;
        return this;
    }

    public SmsSendRequest templateParams(String key, String value) {
        Validate.notEmpty(key, "模板变量的 key 不能为空!");
        Validate.notEmpty(value, "模板变量的 value 不能为空!");
        this.templateParams.put(key,value);
        return this;
    }

    public SmsSendRequest templateParams(String key, Supplier<String> supplier) {
        Validate.notNull(key, "模板变量的 key 不能为空!");
        Validate.notNull(supplier, "模板变量的 supplier 不能为空!");
        this.templateParams.put(key, supplier.get());
        this.valueSupplier.put(key,supplier);
        return this;
    }

    public SmsSendRequest templateParams(Map<String, String> params) {
        Validate.notNull(params, "params must be set!");
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

    public Collection<String> getPhones() {
        return this.phones;
    }

    public Collection<String> getSigns() {
        return this.signs;
    }

    public String getTemplateCode() {
        return this.templateCode;
    }

    public Map<String, String> getTemplateParams() {
        return this.templateParams;
    }

    public Map<String, Supplier<String>> getValueSupplier() {
        return this.valueSupplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmsSendRequest that = (SmsSendRequest) o;
        return Objects.equals(getPhones(), that.getPhones()) &&
                Objects.equals(getSigns(), that.getSigns()) &&
                Objects.equals(getTemplateCode(), that.getTemplateCode()) &&
                Objects.equals(getTemplateParams(), that.getTemplateParams()) &&
                Objects.equals(getValueSupplier(), that.getValueSupplier());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPhones(), getSigns(), getTemplateCode(), getTemplateParams(), getValueSupplier());
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SmsSendRequest{");
        sb.append("phones='").append(this.phones).append('\'');
        sb.append(", signs='").append(this.signs).append('\'');
        sb.append(", templateCode='").append(this.templateCode).append('\'');
        sb.append(", templateParams=").append(this.templateParams);
        sb.append(", valueSupplier=").append(this.valueSupplier);
        sb.append('}');
        return sb.toString();
    }
}
