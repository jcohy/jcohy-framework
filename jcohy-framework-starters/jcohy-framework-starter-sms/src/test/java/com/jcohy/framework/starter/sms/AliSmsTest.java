package com.jcohy.framework.starter.sms;

import com.aliyun.dysmsapi20170525.models.QuerySendDetailsResponse;
import com.aliyun.dysmsapi20170525.models.QuerySendStatisticsResponse;
import com.aliyun.dysmsapi20170525.models.SendBatchSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.jcohy.framework.starter.sms.request.SmsQueryDetailsRequest;
import com.jcohy.framework.starter.sms.request.SmsSendRequest;
import com.jcohy.framework.utils.DateTimeUtils;
import com.jcohy.framework.utils.RandomUtils;

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
@Disabled
public class AliSmsTest {

    @Autowired
    private SmsTemplate template;

	public final String phone1 = "15529021191";
	public final String phone2 = "18515892938";
	public final String phone3 = "13152088219";

	@Test
	void testSmsSendRequestSuccess() {
		SmsSendRequest request = new SmsSendRequest(SmsAction.SEND_SMS).phones(phone1).signs("玄武科技")
				.templateCode("SMS_167745198").templateParams("code", RandomUtils::number);
		Assertions.assertThat(((SendSmsResponse)this.template.send(request).getData()).getBody().getCode()).isEqualTo("OK");
	}

	@Test
	void testSmsSendRequestWithMultiPhoneFail() {
		SmsSendRequest request = new SmsSendRequest(SmsAction.SEND_SMS).phones(phone1, phone3).signs("玄武科技")
				.templateCode("SMS_167745198").templateParams("code", RandomUtils::number);
		Assertions.assertThatExceptionOfType(IndexOutOfBoundsException.class)
				.isThrownBy(() ->this.template.send(request))
				.withMessage("手机号只能有一个！");
	}

	@Test
	void testSmsSendRequestWithOutSignSuccess() {
		SmsSendRequest request = new SmsSendRequest(SmsAction.SEND_SMS).phones(phone1)
				.templateCode("SMS_167745198").templateParams("code", RandomUtils::number);
		Assertions.assertThat(((SendSmsResponse)this.template.send(request).getData()).getBody().getCode()).isEqualTo("OK");
	}

    @Test
    void testSmsSendBatchRequestWithOneSignSuccess() {
        SmsSendRequest request = new SmsSendRequest(SmsAction.SEND_BATCH_SMS).phones(phone1,phone3)
                .signs("玄武科技").templateCode("SMS_167745198").templateParams("code", RandomUtils::number).validate(true);
		Assertions.assertThat(((SendBatchSmsResponse)this.template.sendBatch(request).getData()).getBody().getCode()).isEqualTo("OK");
    }

	@Test
	void testSmsSendBatchRequestWithMultiSignSuccess() {
		SmsSendRequest request = new SmsSendRequest(SmsAction.SEND_BATCH_SMS).phones(phone1,phone3)
				.signs("玄武科技","壹肆零伍").templateCode("SMS_167745198").templateParams("code", RandomUtils::number).validate(true);
		Assertions.assertThat(((SendBatchSmsResponse)this.template.sendBatch(request).getData()).getBody().getCode()).isEqualTo("OK");
	}

	@Test
	void testSmsSendBatchRequestWithMultiSignFail() {
		SmsSendRequest request = new SmsSendRequest(SmsAction.SEND_BATCH_SMS).phones(phone1,phone2,phone3)
				.signs("玄武科技","壹肆零伍").templateCode("SMS_167745198").templateParams("code", RandomUtils::number).validate(true);
		Assertions.assertThatExceptionOfType(SmsException.class)
				.isThrownBy(() ->this.template.sendBatch(request))
				.withMessage("签名和手机号数量不匹配！");
	}

    @Test
    void testSmsQueryDetailsRequest() {
        SmsQueryDetailsRequest request = new SmsQueryDetailsRequest(SmsAction.QUERY_SMS_DETAILS)
                .bizId("843218948437350699^0").phone(phone1)
				.sendDate(DateTimeUtils.simpleToday().replace("-","")).pageIndex(1L).pageSize(1L);
		Assertions.assertThat(((QuerySendDetailsResponse)this.template.querySmsDetails(request).getData())
				.getBody()
				.getSmsSendDetailDTOs()
				.getSmsSendDetailDTO()).hasSizeGreaterThan(0);
    }

	@Test
	void testAliSmsQueryDetailStatistics() {
		SmsQueryDetailsRequest request = new SmsQueryDetailsRequest(SmsAction.QUERY_SMS_STATISTICS)
				.bizId("843218948437350699^0").phone(phone1)
				.startDate(DateTimeUtils.simpleToday().replace("-",""))
				.endDate(DateTimeUtils.simpleToday().replace("-","")).pageIndex(1L).pageSize(1L);
		Assertions.assertThat(((QuerySendStatisticsResponse)this.template.querySmsStatistics(request).getData())
				.getBody()
				.getData()
				.getTargetList()).hasSizeGreaterThan(0);
	}

}
