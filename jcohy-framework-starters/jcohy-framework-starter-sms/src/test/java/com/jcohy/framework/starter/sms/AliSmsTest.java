package com.jcohy.framework.starter.sms;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.jcohy.framework.starter.sms.request.SmsQueryDetailsRequest;
import com.jcohy.framework.starter.sms.request.SmsSendRequest;
import com.jcohy.framework.utils.RandomUtils;
import com.jcohy.framework.utils.api.Result;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2022/3/17:18:41
 * @since 2022.0.1
 */
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("ali")
//@Disabled
public class AliSmsTest {

    @Autowired
    private SmsTemplate template;


	@Test
	void testSmsSendRequestWithMultiPhone() {
		SmsSendRequest request = new SmsSendRequest(SmsAction.SEND_SMS).phones("15529021191,18392638109").signs("玄武科技")
				.templateCode("SMS_167745198").templateParams("code", RandomUtils::number).validate(true);
		Assertions.assertThatIllegalArgumentException()
				.isThrownBy(() ->this.template.send(request))
				.withMessage("手机号只能有一个");
	}

    @Test
    void testSmsSendRequestSuccess() {
        SmsSendRequest request = new SmsSendRequest(SmsAction.SEND_SMS).phones("15529021191").signs("玄武科技")
                .templateCode("SMS_167745198").templateParams("code", RandomUtils::number).validate(true);
        this.template.send(request);
    }

    @Test
    void testSmsSendBatchRequest() {
        SmsSendRequest request = new SmsSendRequest(SmsAction.SEND_BATCH_SMS).phones("15529021191", "13152088219")
                .signs("玄武科技").templateCode("SMS_167745198").templateParams("code", RandomUtils::number).validate(true);
        Result<Object> objectResult = this.template.sendBatch(request);
        System.out.println(objectResult);
    }

    @Test
    void testSmsQueryDetailsRequest() {
        SmsQueryDetailsRequest request = new SmsQueryDetailsRequest(SmsAction.QUERY_SMS_DETAILS)
                .bizId("251124547846263954^0").phone("13152088219").sendDate("20220321").pageIndex(1L).pageSize(1L);
        Result<Object> objectResult = this.template.querySmsDetails(request);
        System.out.println(objectResult);
    }

}
