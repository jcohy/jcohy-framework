package com.jcohy.framework.starter.sms;

import com.jcohy.framework.starter.sms.request.SmsSendRequest;
import com.jcohy.framework.utils.RandomUtils;
import com.jcohy.framework.utils.StringUtils;
import com.jcohy.framework.utils.api.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2022/3/17:18:41
 * @since 2022.0.1
 */
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("ali")
public class AliSmsTest {

    @Autowired
    private SmsTemplate template;

    @Test
    void testSmsSendRequest() {
        SmsSendRequest request = new SmsSendRequest(SmsAction.SEND_SMS)
                .phones("15529021191")
                .signs("玄武科技")
                .templateCode("SMS_167745198")
                .templateParams("code", RandomUtils::number)
                .validate(true);
        template.send(request);
    }

    @Test
    void testSmsSendBatchRequest() {
        SmsSendRequest request = new SmsSendRequest(SmsAction.SEND_BATCH_SMS)
                .phones("15529021191","13152088219")
                .signs("玄武科技")
                .templateCode("SMS_167745198")
                .templateParams("code", RandomUtils::number)
                .validate(true);
        Result<Object> objectResult = template.sendBatch(request);
        System.out.println(objectResult);
    }
}
