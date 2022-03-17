package com.jcohy.framework.starter.sms;

import com.jcohy.framework.starter.sms.request.SmsSendRequest;
import com.jcohy.framework.utils.RandomUtils;
import com.jcohy.framework.utils.StringUtils;
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

        assertThat(SmsAction.ADD_SMS_SIGN.getAction().contains("sign")).isFalse();

        assertThat(StringUtils.containsIgnoreCase(SmsAction.ADD_SMS_SIGN.getAction(),"sign")).isTrue();
        SmsSendRequest request = new SmsSendRequest(SmsAction.SEND_SMS)
                .phones("15529021191")
                .signs("玄武科技")
                .templateCode("SMS_167745198")
                .templateParams("code", RandomUtils::number)
                .validate(true);
        template.send(request);
    }
}
