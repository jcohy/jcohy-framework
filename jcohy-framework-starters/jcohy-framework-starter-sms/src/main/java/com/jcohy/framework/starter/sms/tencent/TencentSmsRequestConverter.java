package com.jcohy.framework.starter.sms.tencent;

import com.aliyun.tea.TeaModel;
import com.jcohy.framework.starter.sms.request.*;
import com.jcohy.framework.utils.JsonUtils;
import com.jcohy.framework.utils.StringUtils;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import org.springframework.core.convert.converter.Converter;

import java.util.Collection;
import java.util.Map;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
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
        if(request instanceof SmsSendRequest) {
            model = buildSmsSendRequestTeaModel((SmsSendRequest)request);
        }
//        if(request instanceof SmsQueryDetailsRequest) {
//            model = buildSmsQueryDetailsRequestTeaModel((SmsQueryDetailsRequest)request);
//        }
//        if( request instanceof SmsSignRequest) {
//            model = buildSmsSignRequestTeaModel((SmsSignRequest)request);
//        }
//        if( request instanceof SmsTemplateRequest) {
//            model = buildSmsTemplateRequestTeaModel((SmsTemplateRequest)request);
//        }
//        if( request instanceof SmsShortUrlRequest) {
//            model = buildSmsShortUrlRequestTeaModel((SmsShortUrlRequest)request);
//        }
//        if( request instanceof SmsTagRequest) {
//            model = buildSmsTagRequestTeaModel((SmsTagRequest)request);
//        }
        return model;
    }

    private SendSmsRequest buildSmsSendRequestTeaModel(SmsSendRequest request) {
        Collection<String> phones = request.getPhones();
        Map<String, String> templateParams = request.getTemplateParams();
        SendSmsRequest model = new SendSmsRequest();

//        StringUtils.commaDelimitedListToStringArray(request.getPhones());
        model.setPhoneNumberSet(request.getPhones().toArray((new String[0])));
        model.setSign(StringUtils.collectionToCommaDelimitedString(request.getSigns()));
        model.setTemplateID(request.getTemplateCode());
        model.setTemplateParamSet(request.getTemplateParams().values().toArray((new String[0])));
//        model.setExtendCode();
//        model.setSessionContext();
//        model.setSenderId();
        return model;
    }
}
