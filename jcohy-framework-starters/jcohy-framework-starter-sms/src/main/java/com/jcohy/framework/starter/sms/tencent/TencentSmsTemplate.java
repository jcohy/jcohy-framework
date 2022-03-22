package com.jcohy.framework.starter.sms.tencent;

import java.util.Map;
import java.util.stream.Collectors;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.convert.converter.Converter;

import com.jcohy.framework.starter.redis.RedisUtils;
import com.jcohy.framework.starter.sms.SmsTemplate;
import com.jcohy.framework.starter.sms.props.SmsProperties;
import com.jcohy.framework.starter.sms.request.SmsQueryDetailsRequest;
import com.jcohy.framework.starter.sms.request.SmsRequest;
import com.jcohy.framework.starter.sms.request.SmsSendRequest;
import com.jcohy.framework.starter.sms.request.SmsShortUrlRequest;
import com.jcohy.framework.starter.sms.request.SmsSignRequest;
import com.jcohy.framework.starter.sms.request.SmsTagRequest;
import com.jcohy.framework.starter.sms.request.SmsTemplateRequest;
import com.jcohy.framework.utils.api.Result;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/22/22:14:39
 * @since 2022.0.1
 */
public class TencentSmsTemplate implements SmsTemplate {

    private static final Logger logger = LoggerFactory.getLogger(TencentSmsTemplate.class);

    /**
     * 类型转换.
     */
    public static final Converter<SmsRequest, SendSmsRequest> converter = new TencentSmsRequestConverter();

    private final SmsProperties properties;

    private final SmsClient client;

    private final RedisUtils<String, String> redisUtils;

    public TencentSmsTemplate(SmsProperties properties, SmsClient client, RedisUtils<String, String> redisUtils) {
        this.properties = properties;
        this.client = client;
        this.redisUtils = redisUtils;
    }

    @Override
    public Result<Object> send(SmsSendRequest request) {
        Validate.validIndex(request.getPhones(), 2, "手机号只能有一个");
        // 默认将 phone 格式化为 +86
        if (!request.isGlobal()) {
            request.phones(request.getPhones().stream().filter((phone) -> !phone.startsWith("86"))
                    .filter((phone) -> !phone.startsWith("+86")).map("86"::concat).collect(Collectors.toList()));
        }

        SendSmsRequest smsRequest = converter.convert(request);
        smsRequest.setSmsSdkAppid(this.properties.getSmsSdkAppId());
        SendSmsResponse response;
        try {
            response = this.client.SendSms(smsRequest);
        }
        catch (TencentCloudSDKException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println(SendSmsResponse.toJsonString(response));

        return Result.data(response);
    }

    @Override
    public Result<Object> sendBatch(SmsSendRequest request) {
        return null;
    }

    @Override
    public Result<Object> querySmsDetails(SmsQueryDetailsRequest request) {
        return null;
    }

    @Override
    public Result<Object> querySmsStatistics(SmsQueryDetailsRequest request) {
        return null;
    }

    @Override
    public Result<Object> addSmsSign(SmsSignRequest request) {
        return null;
    }

    @Override
    public Result<Object> deleteSmsSign(SmsSignRequest request) {
        return null;
    }

    @Override
    public Result<Object> modifySmsSign(SmsSignRequest request) {
        return null;
    }

    @Override
    public Result<Object> querySmsSign(SmsSignRequest request) {
        return null;
    }

    @Override
    public Result<Object> querySmsSignStatus(SmsSignRequest request) {
        return null;
    }

    @Override
    public Result<Object> addSmsTemplate(SmsTemplateRequest request) {
        return null;
    }

    @Override
    public Result<Object> deleteSmsTemplate(SmsTemplateRequest request) {
        return null;
    }

    @Override
    public Result<Object> modifySmsTemplate(SmsTemplateRequest request) {
        return null;
    }

    @Override
    public Result<Object> querySmsTemplate(SmsTemplateRequest request) {
        return null;
    }

    @Override
    public Result<Object> querySmsTemplateStatus(SmsTemplateRequest request) {
        return null;
    }

    @Override
    public Result<Object> addShortUrl(SmsShortUrlRequest request) {
        return null;
    }

    @Override
    public Result<Object> deleteShortUrl(SmsShortUrlRequest request) {
        return null;
    }

    @Override
    public Result<Object> queryShortUrl(SmsShortUrlRequest request) {
        return null;
    }

    @Override
    public Result<Object> addTag(SmsTagRequest request) {
        return null;
    }

    @Override
    public Result<Object> deleteTag(SmsTagRequest request) {
        return null;
    }

    @Override
    public Result<Object> queryTag(SmsTagRequest request) {
        return null;
    }

    @Override
    public boolean validate(String phone, Map<String, String> params) {
        return false;
    }

}
