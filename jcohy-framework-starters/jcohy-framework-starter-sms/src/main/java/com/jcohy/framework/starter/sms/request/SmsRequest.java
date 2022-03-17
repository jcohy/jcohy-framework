package com.jcohy.framework.starter.sms.request;

import com.jcohy.framework.commons.JcohyFrameworkVersion;

import java.io.Serializable;
import java.util.Map;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/17/22:10:26
 * @since 2022.0.1
 */
public class SmsRequest implements Serializable {

    private static final long serialVersionUID = JcohyFrameworkVersion.SERIAL_VERSION_UID;

    private final String action;
    private boolean validate;

    /**
     * 存储键值对.
     */
    private Map<String, Map<String, String>> store;

    public boolean validate() {
        return this.validate;
    }

    public SmsRequest(String action) {
        this.action = action;
    }

    public SmsRequest setValidate(boolean validate) {
        this.validate = validate;
        return this;
    }

    public Map<String, Map<String, String>> store() {
        return this.store;
    }

    public SmsRequest setStore(Map<String, Map<String, String>> store) {
        this.store = store;
        return this;
    }

    public String action() {
        return this.action;
    }
}
