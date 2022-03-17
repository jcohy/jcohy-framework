package com.jcohy.framework.starter.sms.ali;

import com.aliyun.dysmsapi20170525.models.SendBatchSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaModel;
import com.jcohy.framework.starter.sms.SmsAction;
import com.jcohy.framework.starter.sms.request.SmsRequest;
import com.jcohy.framework.starter.sms.request.SmsSendRequest;
import com.jcohy.framework.utils.JsonUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2022/3/17:18:47
 * @since 2022.0.1
 */
public class AliSmsRequestConverter implements Converter<SmsRequest, TeaModel> {


    @Override
    public TeaModel convert(SmsRequest request) {
        TeaModel model = null;
        if(request instanceof SmsSendRequest) {
            model = buildTeaModel((SmsSendRequest)request);
        }
        return model;
    }

    private TeaModel buildTeaModel(SmsSendRequest request) {
        if(request.getAction().equals(SmsAction.SEND_SMS)) {
            SendSmsRequest model = new SendSmsRequest()
                    .setPhoneNumbers(request.getPhones())
                    .setSignName(request.getSigns())
                    .setTemplateCode(request.getTemplateCode())
                    .setTemplateParam(JsonUtils.toJson(request.getTemplateParams()));

            return model;
        }

        if(request.getAction().equals(SmsAction.SEND_BATCH_SMS)) {
            SendBatchSmsRequest model = new SendBatchSmsRequest()
                    .setPhoneNumberJson(request.getPhones())
                    .setSignNameJson(request.getSigns())
                    .setTemplateCode(request.getTemplateCode())
                    .setTemplateParamJson(JsonUtils.toJson(request.getTemplateParams()));
            return model;
        }
        return null;
    }
}
