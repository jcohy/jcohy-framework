package com.jcohy.framework.starter.sms.request;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import com.jcohy.framework.commons.JcohyFrameworkVersion;
import com.jcohy.framework.starter.sms.SmsAction;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/17/22:10:26
 * @since 2022.0.1
 */
public class SmsRequest implements Serializable {

    private static final long serialVersionUID = JcohyFrameworkVersion.SERIAL_VERSION_UID;

    private final SmsAction action;

    public SmsRequest(SmsAction action) {
        this.action = action;
    }

    public SmsAction getAction() {
        return this.action;
    }

    public boolean isValidate() {
        return this.validate;
    }

    public SmsRequest setValidate(boolean validate) {
        this.validate = validate;
        return this;
    }

    public Map<String, Map<String, String>> getStore() {
        return this.store;
    }

    public SmsRequest setStore(Map<String, Map<String, String>> store) {
        this.store = store;
        return this;
    }

    private boolean validate;

    /**
     * 存储键值对.
     */
    private Map<String, Map<String, String>> store;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SmsRequest that = (SmsRequest) o;
        return isValidate() == that.isValidate() && Objects.equals(getAction(), that.getAction())
                && Objects.equals(getStore(), that.getStore());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAction(), isValidate(), getStore());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SmsRequest{");
        sb.append("action='").append(this.action).append('\'');
        sb.append(", validate=").append(this.validate);
        sb.append(", store=").append(this.store);
        sb.append('}');
        return sb.toString();
    }

}
