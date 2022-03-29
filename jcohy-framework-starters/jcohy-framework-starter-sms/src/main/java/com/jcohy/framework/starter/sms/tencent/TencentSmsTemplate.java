package com.jcohy.framework.starter.sms.tencent;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.tencentcloudapi.common.AbstractModel;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.AddSmsSignRequest;
import com.tencentcloudapi.sms.v20210111.models.AddSmsSignResponse;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.convert.converter.Converter;

import com.jcohy.framework.starter.redis.RedisUtils;
import com.jcohy.framework.starter.sms.SmsAction;
import com.jcohy.framework.starter.sms.SmsException;
import com.jcohy.framework.starter.sms.SmsHelper;
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
import com.jcohy.framework.utils.api.ServiceStatusCode;

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
    public static final Converter<SmsRequest, AbstractModel> CONVERTER = new TencentSmsRequestConverter();

    private final SmsProperties properties;

    private final SmsClient client;

    private final RedisUtils<String, String> redisUtils;

    public TencentSmsTemplate(SmsProperties properties, SmsClient client, RedisUtils<String, String> redisUtils) {
        this.properties = properties;
        this.client = client;
        this.redisUtils = redisUtils;
    }

    private SendSmsResponse sendSms(SmsSendRequest request) {
        SendSmsResponse response;
        try {
            // 默认将 phone 格式化为 +86
            if (!request.isGlobal()) {
                request.phones(SmsHelper.formatPhones(request.getPhones()));
            }

            // 默认以 request 中的签名为准，如果不存在，则使用 properties 中的签名
            request.signs((request.getSigns() != null) ? request.getSigns()
                    : Collections.singletonList(this.properties.getSignName()));

            SendSmsRequest smsRequest = (SendSmsRequest) CONVERTER.convert(request);
            smsRequest.setSmsSdkAppId(this.properties.getSmsSdkAppId());
            response = this.client.SendSms(smsRequest);
        }
        catch (TencentCloudSDKException ex) {
            throw new RuntimeException(ex);
        }
        return response;
    }

    @Override
    public Result<Object> send(SmsSendRequest request) {
        SmsHelper.validSize(request.getPhones(), 1, "手机号只能有一个！");
        return Result.data(sendSms(request));
    }

    /**
     * 如果签名数量大于1，或者模版参数中含有动态变化的值，将一条一条的发送短信.
     * @param request request
     * @return /
     */
    @Override
    public Result<Object> sendBatch(SmsSendRequest request) {
        int signSize = request.getSigns().size();
        int phoneSize = request.getPhones().size();
        int dynamicParamsSize = request.getValueSupplier().size();
        if (signSize > 1 || dynamicParamsSize > 0) {
            SmsHelper.processSmsSendSign(request);
            List<Map<String, String>> templateParams = SmsHelper.processSmsSendTemplateParams(request);
            for (int i = 0; i < phoneSize; i++) {
                String phone = request.getPhones().toArray(new String[0])[i];
                SmsSendRequest smsSendRequest = new SmsSendRequest(SmsAction.SEND_BATCH_SMS).phones(phone)
                        .signs(request.getSigns().toArray(new String[0])[i]).templateCode(request.getTemplateCode())
                        .templateParams(templateParams.get(i));
                SendSmsResponse sendSmsResponse = this.sendSms(smsSendRequest);
                if (!sendSmsResponse.getSendStatusSet()[0].getCode().equalsIgnoreCase("OK")) {
                    throw new SmsException(String.format("手机号 %s 发送失败！", phone));
                }
            }
        }
        else {
            return Result.data(this.sendSms(request));
        }
        return Result.success(ServiceStatusCode.SUCCESS);
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
        AddSmsSignRequest smsRequest = (AddSmsSignRequest) CONVERTER.convert(request);
        AddSmsSignResponse response;
        try {
            response = this.client.AddSmsSign(smsRequest);
        }
        catch (TencentCloudSDKException ex) {
            throw new RuntimeException(ex);
        }

        return Result.data(response);
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
