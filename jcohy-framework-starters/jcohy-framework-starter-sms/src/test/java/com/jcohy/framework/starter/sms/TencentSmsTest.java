package com.jcohy.framework.starter.sms;

import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.jcohy.framework.starter.sms.request.SmsSendRequest;
import com.jcohy.framework.utils.RandomUtils;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/22/22:16:51
 * @since 2022.0.1
 */
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("tencent")
@Disabled
public class TencentSmsTest {

    @Autowired
    private SmsTemplate template;

    public final String phone1 = "15529021191";

    public final String phone2 = "18515892938";

    public final String phone3 = "13152088219";

    public final String templateCode = "981666";

    public final String sign1 = "壹肆零伍";

    public final String sign2 = "西安壹肆零伍防务科技有限";

    @Test
    void testSmsSendRequestSuccess() {
        SmsSendRequest request = new SmsSendRequest(SmsAction.SEND_SMS).phones(this.phone1).signs(this.sign1)
                .templateCode(this.templateCode).templateParams("code", RandomUtils::number);
        SendStatus[] sendStatusSet = ((SendSmsResponse) this.template.send(request).getData()).getSendStatusSet();
        Assertions.assertThat(sendStatusSet).hasSize(1);
        Assertions.assertThat(sendStatusSet[0].getCode()).isEqualToIgnoringCase("OK");
    }

    @Test
    void testSmsSendRequestWithMultiPhoneFail() {
        SmsSendRequest request = new SmsSendRequest(SmsAction.SEND_SMS).phones(this.phone1, this.phone3)
                .signs(this.sign1).templateCode(this.templateCode).templateParams("code", RandomUtils::number);
        Assertions.assertThatExceptionOfType(IndexOutOfBoundsException.class)
                .isThrownBy(() -> this.template.send(request)).withMessage("手机号只能有一个！");
    }

    @Test
    void testSmsSendRequestWithOutSignSuccess() {
        SmsSendRequest request = new SmsSendRequest(SmsAction.SEND_SMS).phones(this.phone1)
                .templateCode(this.templateCode).templateParams("code", RandomUtils::number);
        SendStatus[] sendStatusSet = ((SendSmsResponse) this.template.send(request).getData()).getSendStatusSet();
        Assertions.assertThat(sendStatusSet).hasSize(1);
        Assertions.assertThat(sendStatusSet[0].getCode()).isEqualToIgnoringCase("OK");
    }

    @Test
    void testSmsSendBatchRequestWithOneSignSuccess() {
        SmsSendRequest request = new SmsSendRequest(SmsAction.SEND_BATCH_SMS).phones(this.phone1, this.phone3)
                .signs(this.sign1).templateCode(this.templateCode).templateParams("code", RandomUtils::number)
                .validate(true);
        Assertions.assertThat(this.template.sendBatch(request).isSuccess()).isTrue();
    }

    @Test
    void testSmsSendBatchRequestWithMultiSignSuccess() {
        SmsSendRequest request = new SmsSendRequest(SmsAction.SEND_BATCH_SMS).phones(this.phone1, this.phone3)
                .signs(this.sign1, this.sign2).templateCode(this.templateCode)
                .templateParams("code", RandomUtils::number).validate(true);
        Assertions.assertThat(this.template.sendBatch(request).isSuccess()).isTrue();
    }

    @Test
    void testSmsSendBatchRequestWithMultiSignFail() {
        SmsSendRequest request = new SmsSendRequest(SmsAction.SEND_BATCH_SMS)
                .phones(this.phone1, this.phone2, this.phone3).signs(this.sign1, this.sign2)
                .templateCode(this.templateCode).templateParams("code", RandomUtils::number).validate(true);
        Assertions.assertThatExceptionOfType(SmsException.class).isThrownBy(() -> this.template.sendBatch(request))
                .withMessage("签名和手机号数量不匹配！");
    }

    // @Test
    // void testSmsQueryDetailsRequest() {
    // SmsQueryDetailsRequest request = new
    // SmsQueryDetailsRequest(SmsAction.QUERY_SMS_DETAILS)
    // .bizId("843218948437350699^0")
    // .phone(phone1)
    // .sendDate(DateTimeUtils.simpleToday().replace("-",""))
    // .pageIndex(1L)
    // .pageSize(1L);
    // Assertions.assertThat(((QuerySendDetailsResponse)this.template.querySmsDetails(request).getData())
    // .getBody()
    // .getSmsSendDetailDTOs()
    // .getSmsSendDetailDTO()).hasSizeGreaterThan(0);
    // }
    //
    // @Test
    // void testAliSmsQueryDetailStatistics() {
    // SmsQueryDetailsRequest request = new
    // SmsQueryDetailsRequest(SmsAction.QUERY_SMS_STATISTICS)
    // .bizId("843218948437350699^0").phone(phone1)
    // .startDate(DateTimeUtils.simpleToday().replace("-",""))
    // .endDate(DateTimeUtils.simpleToday().replace("-","")).pageIndex(1L).pageSize(1L);
    // Assertions.assertThat(((QuerySendStatisticsResponse)this.template.querySmsStatistics(request).getData())
    // .getBody()
    // .getData()
    // .getTargetList()).hasSizeGreaterThan(0);
    // }
    //

}
