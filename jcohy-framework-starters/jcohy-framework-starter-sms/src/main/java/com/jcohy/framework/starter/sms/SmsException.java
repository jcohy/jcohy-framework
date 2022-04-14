package com.jcohy.framework.starter.sms;

import com.jcohy.framework.utils.api.ResultStatusCode;
import com.jcohy.framework.utils.exceptions.JcohyException;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/15/22:10:37
 * @since 2022.0.1
 */
public class SmsException extends JcohyException {

    public SmsException(String message) {
        super(message);
    }

    public SmsException(ResultStatusCode resultStatusCode) {
        super(resultStatusCode);
    }

    public SmsException(ResultStatusCode resultStatusCode, Throwable cause) {
        super(resultStatusCode, cause);
    }

}
