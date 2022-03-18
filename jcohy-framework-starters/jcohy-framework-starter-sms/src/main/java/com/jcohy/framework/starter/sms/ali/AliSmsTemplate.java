package com.jcohy.framework.starter.sms.ali;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendBatchSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendBatchSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaModel;
import com.jcohy.framework.starter.redis.RedisUtils;
import com.jcohy.framework.starter.sms.SmsTemplate;
import com.jcohy.framework.starter.sms.props.SmsProperties;
import com.jcohy.framework.starter.sms.request.*;
import com.jcohy.framework.utils.api.Result;
import com.jcohy.framework.utils.api.ResultStatusCode;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2022/3/17:18:44
 * @since 2022.0.1
 */
public class AliSmsTemplate implements SmsTemplate {

    private static final Logger logger = LoggerFactory.getLogger(AliSmsTemplate.class);

    /**
     * 类型转换.
     */
    public static final Converter<SmsRequest, TeaModel> converter = new AliSmsRequestConverter();
    /**
     * smsProperties
     */
    private final SmsProperties smsProperties;

    /**
     * client 对象，用来发送短信.
     */
    private final Client client;

    private final RedisUtils<String, String> redisUtils;

    public AliSmsTemplate(SmsProperties smsProperties, Client client, RedisUtils<String, String> redisUtils) {
        this.smsProperties = smsProperties;
        this.client = client;
        this.redisUtils = redisUtils;
    }

    @Override
    public Result<Object> send(SmsSendRequest request) {
        SendSmsRequest model = (SendSmsRequest) converter.convert(request);
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = client.sendSms(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data(sendSmsResponse.getBody());
    }

    @Override
    public Result<Object> sendBatch(SmsSendRequest request) {
        SendBatchSmsRequest model = (SendBatchSmsRequest) converter.convert(request);
        SendBatchSmsResponse response = null;
        try {
            response = client.sendBatchSms(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data( 10000,response.getBody().message,response);
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
