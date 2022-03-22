package com.jcohy.framework.starter.sms;

import com.jcohy.framework.starter.sms.request.SmsQueryDetailsRequest;
import com.jcohy.framework.starter.sms.request.SmsSendRequest;
import com.jcohy.framework.utils.RandomUtils;
import com.jcohy.framework.utils.api.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/22/22:16:51
 * @since 2022.0.1
 */
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("tencent")
public class TencentSmsTest {

    @Autowired
    private SmsTemplate template;

    @Test
    void testSmsSendRequest() {
        SmsSendRequest request = new SmsSendRequest(SmsAction.SEND_SMS)
                .phones("15529021191")
                .signs("壹肆零伍")
                .templateCode("981666")
                .templateParams("code", RandomUtils::number)
                .validate(true);
        template.send(request);
    }
//
//    @Test
//    void testSmsSendBatchRequest() {
//        SmsSendRequest request = new SmsSendRequest(SmsAction.SEND_BATCH_SMS)
//                .phones("15529021191","13152088219")
//                .signs("玄武科技")
//                .templateCode("SMS_167745198")
//                .templateParams("code", RandomUtils::number)
//                .validate(true);
//        Result<Object> objectResult = template.sendBatch(request);
//        System.out.println(objectResult);
//    }
//
//    @Test
//    void testSmsQueryDetailsRequest() {
//        SmsQueryDetailsRequest request = new SmsQueryDetailsRequest(SmsAction.QUERY_SMS_DETAILS)
//                .bizId("251124547846263954^0")
//                .phone("13152088219")
//                .sendDate("20220321")
//                .pageIndex(1L)
//                .pageSize(1L);
//        Result<Object> objectResult = template.querySmsDetails(request);
//        System.out.println(objectResult);
//    }

}
