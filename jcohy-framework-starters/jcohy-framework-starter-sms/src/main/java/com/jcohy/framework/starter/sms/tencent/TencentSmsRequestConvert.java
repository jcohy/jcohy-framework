package com.jcohy.framework.starter.sms.tencent;

import java.util.Arrays;

import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;

import org.springframework.core.convert.converter.Converter;

/**
 * 描述: 将系统的请求转换为腾讯 api 请求.
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/15/22:10:43
 * @since 2022.0.1
 */
public class TencentSmsRequestConvert implements Converter<TencentSmsRequest, SendSmsRequest> {

    @Override
    public SendSmsRequest convert(TencentSmsRequest request) {
        SendSmsRequest req = new SendSmsRequest();
        // 给每个手机号+86
        String[] arrays = Arrays.stream(request.getPhones()).map((phone) -> "+86" + phone).toArray(String[]::new);
        req.setPhoneNumberSet(arrays);
        if (request.getSignName() != null) {
            for (String signName : request.getSignName()) {
                req.setSignName(signName);
            }
        }
        req.setTemplateId(request.getTemplateId());
        req.setTemplateParamSet(request.getTemplateParams());
        return req;
    }

}
