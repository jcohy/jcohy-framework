package com.jcohy.framework.starter.sms.ali;

import java.util.HashMap;
import java.util.Map;

import com.aliyuncs.http.MethodType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.jcohy.framework.starter.sms.SmsException;
import com.jcohy.framework.starter.sms.TestApplication;
import com.jcohy.framework.utils.RandomUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/15/22:11:17
 * @since 2022.0.1
 */
@SpringBootTest(classes = TestApplication.class)
@Profile("ali")
public class AliSmsTest {

    @Autowired
    private AliSmsTemplate aliSmsTemplate;

    @Test
    void contextLoads() {

    }

    @Test
    @DisplayName("当SEND_BATCH_SMS时，测试单发 ")
    public void testSimpleBySendBatchSMSToAliSmsResultFail() {

        AliSmsRequest aliSmsRequest = AliSmsRequest.builder(ActionType.SEND_BATCH_SMS, MethodType.POST)
                .phones("15529393207").templateId("SMS_167745198").templateParams("code", RandomUtils::number)
                .templateParams("name", () -> "jcohy").signs("玄武科技").validate(true).build();

        String send = this.aliSmsTemplate.send(aliSmsRequest);
        assertThat(send).contains("OK");
    }

    @Test
    @DisplayName("当SEND_BATCH_SMS时  标签数为1的群发")

    public void testBatchBySendBatchSMSToAliSmsResultUnreasonable() {

        String number = RandomUtils.number();
        AliSmsRequest aliSmsRequest = AliSmsRequest.builder(ActionType.SEND_BATCH_SMS, MethodType.POST)
                .phones("15529393207,18302913526").templateId("SMS_167745198").templateParams("code", () -> number)
                .templateParams("name", () -> "jcohy").signs("玄武科技").validate(true).build();

        String send = this.aliSmsTemplate.send(aliSmsRequest);
        assertThat(send).isNull();
    }

    @Test
    @DisplayName("当SEND_BATCH_SMS时 测试群发")
    public void testBatchAliSmsResultOk() {

        AliSmsRequest aliSmsRequest = AliSmsRequest.builder(ActionType.SEND_BATCH_SMS, MethodType.POST)
                .phones("18302913526,15529393207").templateId("SMS_167745198")
                .templateParams("code", RandomUtils::number).templateParams("name", () -> "jcohy")
                .signs("玄武科技", "深圳玄武人工智能科技").validate(true).build();

        String send = this.aliSmsTemplate.send(aliSmsRequest);
        assertThat(send).contains("OK");
    }

    @Test
    @DisplayName("当SEND_BATCH_SMS时 测试标签数与电话数不匹配")
    public void testBatchMismatchingAliSms() {

        String number = RandomUtils.number();
        AliSmsRequest.Builder aliSmsRequest = AliSmsRequest.builder(ActionType.SEND_BATCH_SMS, MethodType.POST)
                .phones("15529393207,18302913526,18515892938").templateId("SMS_167745198")
                .templateParams("code", () -> number).templateParams("name", () -> "jcohy").signs("玄武科技", "深圳玄武人工智能科技")
                .validate(true);

        Throwable throwable = catchThrowable(aliSmsRequest::build);
        assertThat(throwable).isInstanceOf(SmsException.class).hasMessage("签名和手机号不匹配");
    }

    /**
     * isv.MOBILE_NUMBER_ILLEGAL 原因：手机号码格式错误.
     */

    @Test
    @DisplayName("当SEND_BATCH_SMS时 验证验证码")
    public void testvalidateSendBatchSMSToAliSmsResultOk() {
        String number = RandomUtils.number();
        AliSmsRequest aliSmsRequest = AliSmsRequest.builder(ActionType.SEND_BATCH_SMS, MethodType.POST)
                .phones("18302913526,15529393207").templateId("SMS_167745198").templateParams("code", () -> number)
                .templateParams("name", () -> "jcohy").signs("玄武科技", "深圳玄武人工智能科技").validate(true).build();

        this.aliSmsTemplate.send(aliSmsRequest);
        Map<String, String> map = new HashMap<>();
        map.put("code", number);
        map.put("name", "jcohy");
        this.aliSmsTemplate.validate("18302913526", map);
        boolean validate = this.aliSmsTemplate.validate("15529393207", map);
        assertThat(validate).isTrue();

    }

    @Test
    @DisplayName("当SEND_SMS时  测试单发")

    public void testSimpleBySendSMSToAliSms() {

        AliSmsRequest aliSmsRequest = AliSmsRequest.builder(ActionType.SEND_SMS, MethodType.POST).phones("15529393207")
                .templateId("SMS_167745198").templateParams("code", () -> RandomUtils.number())
                .templateParams("name", () -> "jcohy").signs("玄武科技").validate(true).build();

        String send = this.aliSmsTemplate.send(aliSmsRequest);
        assertThat(send).contains("OK");
    }

    @Test
    @DisplayName("当SEND_SMS时  测试群发")

    public void testBatchBySendSMSToAliSmsResultFail() {

        AliSmsRequest aliSmsRequest = AliSmsRequest.builder(ActionType.SEND_SMS, MethodType.POST)
                .phones("15529393207,18302913526").templateId("SMS_167745198")
                .templateParams("code", RandomUtils::number).templateParams("name", () -> "jcohy").signs("玄武科技")
                .validate(true).build();

        String send = this.aliSmsTemplate.send(aliSmsRequest);
        assertThat(send).contains("OK");
    }

    @Test
    @DisplayName("ActionType.SEND_SMS  短信验证")

    public void testvalidateBySendSMSToAliSms() {

        String number = RandomUtils.number();
        AliSmsRequest aliSmsRequest = AliSmsRequest.builder(ActionType.SEND_SMS, MethodType.POST).phones("15529393207")
                .templateId("SMS_167745198").templateParams("code", () -> number).templateParams("name", () -> "jcohy")
                .signs("玄武科技").validate(true).build();

        this.aliSmsTemplate.send(aliSmsRequest);

        Map<String, String> map = new HashMap<>();
        map.put("code", number);
        map.put("name", "jcohy");
        boolean validate = this.aliSmsTemplate.validate("15529393207", map);
        assertThat(validate).isTrue();

    }

    @Test
    @DisplayName("ActionType.SEND_SMS  电话格式")

    public void testPhoneFormatAliSms() {

        String number = RandomUtils.number();
        AliSmsRequest aliSmsRequest = AliSmsRequest.builder(ActionType.SEND_SMS, MethodType.POST).phones("155293932078")
                .templateId("SMS_167745198").templateParams("code", () -> number).templateParams("name", () -> "jcohy")
                .signs("玄武科技").validate(true).build();

        String send = this.aliSmsTemplate.send(aliSmsRequest);
        assertThat(send).contains("isv.MOBILE_NUMBER_ILLEGAL");

    }

}
