package com.jcohy.framework.starter.sms;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import com.jcohy.framework.commons.JcohyFrameworkVersion;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/15/22:10:39
 * @since 2022.0.1
 */
public class SmsRequest implements Serializable {

    private static final long serialVersionUID = JcohyFrameworkVersion.SERIAL_VERSION_UID;

    /**
     * 号码列表,如果有多个号码，请使用逗号隔开 例如： String phone = "123,256".
     */
    private String[] phones;

    private String message;

    private boolean validate;

    /**
     * 存储键值对.
     */
    private Map<String, Map<String, String>> store;

    public String[] getPhones() {
        return this.phones;
    }

    public void setPhones(String[] phones) {
        this.phones = phones;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isValidate() {
        return this.validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public Map<String, Map<String, String>> getStore() {
        return this.store;
    }

    public void setStore(Map<String, Map<String, String>> store) {
        this.store = store;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SmsRequest that = (SmsRequest) o;
        return isValidate() == that.isValidate() && Arrays.equals(getPhones(), that.getPhones())
                && Objects.equals(getMessage(), that.getMessage()) && Objects.equals(getStore(), that.getStore());
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(getPhones());
        result = 31 * result + ((getMessage() != null) ? getMessage().hashCode() : 0);
        result = 31 * result + (isValidate() ? 1 : 0);
        result = 31 * result + ((getStore() != null) ? getStore().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SmsRequest{" + "phones=" + Arrays.toString(this.phones) + ", message='" + this.message + '\''
                + ", validate=" + this.validate + ", store=" + this.store + '}';
    }

}
