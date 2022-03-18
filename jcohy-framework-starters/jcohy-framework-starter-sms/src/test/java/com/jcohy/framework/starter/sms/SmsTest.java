package com.jcohy.framework.starter.sms;

import com.jcohy.framework.starter.sms.request.SmsRequest;
import com.jcohy.framework.starter.sms.request.SmsRequestFactory;
import com.jcohy.framework.starter.sms.request.SmsSendRequest;
import org.junit.jupiter.api.Test;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/17/22:15:12
 * @since 2022.0.1
 */
public class SmsTest {

    @Test
    void test01() {
        SmsSendRequest action = ((SmsSendRequest)SmsRequestFactory.action(SmsAction.SEND_SMS))
                .phones();
    }
}
