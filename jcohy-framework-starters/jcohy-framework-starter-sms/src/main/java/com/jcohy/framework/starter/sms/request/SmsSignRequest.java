package com.jcohy.framework.starter.sms.request;

import com.jcohy.framework.starter.sms.SmsAction;
import org.apache.commons.lang3.Validate;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/16/22:18:25
 * @since 2022.0.1
 */
public class SmsSignRequest extends SmsRequest {
    public SmsSignRequest(SmsAction action) {
        super(action);
        Validate.notNull(action,"action 不能为空 !");
    }
}
