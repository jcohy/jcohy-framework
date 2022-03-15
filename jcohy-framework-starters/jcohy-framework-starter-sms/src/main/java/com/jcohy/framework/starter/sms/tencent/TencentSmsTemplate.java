package com.jcohy.framework.starter.sms.tencent;

import java.util.Map;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;

import org.springframework.core.convert.converter.Converter;

import com.jcohy.framework.starter.redis.RedisUtils;
import com.jcohy.framework.starter.redis.lock.RedisLock;
import com.jcohy.framework.starter.sms.SmsRequest;
import com.jcohy.framework.starter.sms.SmsTemplate;
import com.jcohy.framework.starter.sms.props.SmsProperties;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/15/22:10:45
 * @since 2022.0.1
 */
public class TencentSmsTemplate implements SmsTemplate {

    private static final Converter<TencentSmsRequest, SendSmsRequest> CONVERT = new TencentSmsRequestConvert();

    private final SmsProperties smsProperties;

    private final RedisUtils<String, String> redisUtils;

    private final SmsClient client;

    public TencentSmsTemplate(SmsProperties smsProperties, SmsClient client, RedisUtils<String, String> redisUtils) {
        this.client = client;
        this.smsProperties = smsProperties;
        this.redisUtils = redisUtils;
    }

    /**
     * 发送腾讯云短信.
     * @param request request
     * @return string
     */
    @Override
    public String send(SmsRequest request) {
        SendSmsRequest smsRequest = CONVERT.convert((TencentSmsRequest) request);
        try {
            assert smsRequest != null;
            smsRequest.setSmsSdkAppId(this.smsProperties.getSmsSdkAppId());
            if (smsRequest.getSignName() == null) {
                smsRequest.setSignName(this.smsProperties.getSignName());
            }
            SendSmsResponse resp = this.client.SendSms(smsRequest);
            if (request.isValidate()) {
                SmsTemplate.store(request,
                        (key, value) -> this.redisUtils.hashMSet(this.smsProperties.getCacheKey() + key, value,
                                this.smsProperties.getTimeout().getSeconds()));
            }
            return SendSmsResponse.toJsonString(resp);
        }
        catch (TencentCloudSDKException ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    }

    /**
     * 验证验证码.
     * @param phone 手机号
     * @param params 验证码
     * @return boolean
     */
    @Override
    public boolean validate(String phone, Map<String, String> params) {
        for (Map.Entry<String, String> param : params.entrySet()) {
            String key = param.getKey();
            String value = param.getValue();
            String redisValue = (String) this.redisUtils.hashGet(this.smsProperties.getCacheKey() + phone, key);
            if (!redisValue.equalsIgnoreCase(value)) {
                return false;
            }
        }
        this.redisUtils.del(this.smsProperties.getCacheKey() + phone);
        return true;
    }

}
