package com.jcohy.framework.starter.sms.tencent;

import java.util.Collection;
import java.util.Map;

import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import org.apache.commons.lang3.Validate;

import org.springframework.core.convert.converter.Converter;

import com.jcohy.framework.starter.sms.SmsAction;
import com.jcohy.framework.starter.sms.SmsHelper;
import com.jcohy.framework.starter.sms.request.SmsRequest;
import com.jcohy.framework.starter.sms.request.SmsSendRequest;
import com.jcohy.framework.utils.StringUtils;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/22/22:14:42
 * @since 2022.0.1
 */
public class TencentSmsRequestConverter implements Converter<SmsRequest, SendSmsRequest> {

    @Override
    public SendSmsRequest convert(SmsRequest request) {
        SendSmsRequest model = null;
        if (request instanceof SmsSendRequest) {
            model = buildSmsSendRequestTeaModel((SmsSendRequest) request);
        }
        // if(request instanceof SmsQueryDetailsRequest) {
        // model = buildSmsQueryDetailsRequestTeaModel((SmsQueryDetailsRequest)request);
        // }
        // if( request instanceof SmsSignRequest) {
        // model = buildSmsSignRequestTeaModel((SmsSignRequest)request);
        // }
        // if( request instanceof SmsTemplateRequest) {
        // model = buildSmsTemplateRequestTeaModel((SmsTemplateRequest)request);
        // }
        // if( request instanceof SmsShortUrlRequest) {
        // model = buildSmsShortUrlRequestTeaModel((SmsShortUrlRequest)request);
        // }
        // if( request instanceof SmsTagRequest) {
        // model = buildSmsTagRequestTeaModel((SmsTagRequest)request);
        // }
        return model;
    }

    private SendSmsRequest buildSmsSendRequestTeaModel(SmsSendRequest request) {
        SendSmsRequest model = new SendSmsRequest();
        String[] phones = request.getPhones().toArray((new String[0]));
        if (request.getAction().equals(SmsAction.SEND_SMS)) {
            SmsHelper.validSize(request.getPhones(), 1, "如果需要向多个手机号发送信息，请使用 SmsAction.SEND_BATCH_SMS!");
        }
        model.setPhoneNumberSet(phones);
        model.setSign(StringUtils.collectionToCommaDelimitedString(request.getSigns()));
        model.setTemplateID(request.getTemplateCode());
        model.setTemplateParamSet(request.getTemplateParams().values().toArray((new String[0])));
        return model;
    }

}
