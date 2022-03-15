package com.jcohy.framework.starter.sms.ali;

import java.util.Map;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;

import org.springframework.core.convert.converter.Converter;

import com.jcohy.framework.starter.redis.RedisUtils;
import com.jcohy.framework.starter.sms.SmsRequest;
import com.jcohy.framework.starter.sms.SmsTemplate;
import com.jcohy.framework.starter.sms.props.SmsProperties;
import com.jcohy.framework.utils.JsonUtils;
import com.jcohy.framework.utils.StringUtils;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/15/22:10:41
 * @since 2022.0.1
 */
public class AliSmsTemplate implements SmsTemplate {

    private static final Converter<AliSmsRequest, CommonRequest> ALI_SMS_CONVERTER = new CommonRequestConverter();

    private final SmsProperties smsProperties;

    private final IAcsClient client;

    private final RedisUtils<String, String> redisUtils;

    public AliSmsTemplate(SmsProperties smsProperties, IAcsClient client, RedisUtils<String, String> redisUtils) {
        this.smsProperties = smsProperties;
        this.client = client;
        this.redisUtils = redisUtils;
    }

    @Override
    public String send(SmsRequest request) {
        CommonRequest commonRequest = ALI_SMS_CONVERTER.convert((AliSmsRequest) request);
        try {
            CommonResponse response = this.client.getCommonResponse(commonRequest);
            if (request.isValidate()) {
                SmsTemplate.store(request,
                        (key, value) -> this.redisUtils.hashMSet(this.smsProperties.getCacheKey() + key, value,
                                this.smsProperties.getTimeout().getSeconds()));
            }
            return JsonUtils.getInstance().writeValueAsString(response.getData());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    }

    @Override
    public boolean validate(String phone, Map<String, String> params) {
        for (Map.Entry<String, String> param : params.entrySet()) {
            String key = param.getKey();
            String value = param.getValue();
            String redisValue = (String) this.redisUtils.hashGet(this.smsProperties.getCacheKey() + phone, key);
            if (StringUtils.isNotEmpty(redisValue) || !redisValue.equalsIgnoreCase(value)) {
                return false;
            }
        }
        this.redisUtils.del(this.smsProperties.getCacheKey() + phone);
        return true;
    }

}
