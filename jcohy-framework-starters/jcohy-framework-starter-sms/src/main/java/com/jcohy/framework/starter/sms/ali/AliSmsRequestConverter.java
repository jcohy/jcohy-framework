package com.jcohy.framework.starter.sms.ali;

import com.aliyun.dysmsapi20170525.models.SendBatchSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.tea.TeaModel;
import com.jcohy.framework.starter.sms.SmsAction;
import com.jcohy.framework.starter.sms.SmsException;
import com.jcohy.framework.starter.sms.request.*;
import com.jcohy.framework.utils.JsonUtils;
import com.jcohy.framework.utils.Sets;
import com.jcohy.framework.utils.StringUtils;
import com.jcohy.framework.utils.constant.StringPools;
import com.jcohy.framework.utils.maps.Maps;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.core.convert.converter.Converter;

import java.util.*;
import java.util.function.Supplier;

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
            model = buildSmsSendRequestTeaModel((SmsSendRequest)request);
        }
        if(request instanceof SmsQueryDetailsRequest) {
            model = buildSmsQueryDetailsRequestTeaModel((SmsSendRequest)request);
        }
        if( request instanceof SmsSignRequest) {
            model = buildSmsSignRequestTeaModel((SmsSendRequest)request);
        }
        if( request instanceof SmsTemplateRequest) {
            model = buildSmsTemplateRequestTeaModel((SmsSendRequest)request);
        }
        if( request instanceof SmsShortUrlRequest) {
            model = buildSmsShortUrlRequestTeaModel((SmsSendRequest)request);
        }
        if( request instanceof SmsTagRequest) {
            model = buildSmsTagRequestTeaModel((SmsSendRequest)request);
        }
        return model;
    }

    private TeaModel buildSmsSignRequestTeaModel(SmsSendRequest request) {
        return null;
    }

    private TeaModel buildSmsTemplateRequestTeaModel(SmsSendRequest request) {
        return null;
    }

    private TeaModel buildSmsShortUrlRequestTeaModel(SmsSendRequest request) {
        return null;
    }

    private TeaModel buildSmsTagRequestTeaModel(SmsSendRequest request) {
        return null;
    }

    private TeaModel buildSmsQueryDetailsRequestTeaModel(SmsSendRequest request) {
        return null;
    }

    private TeaModel buildSmsSendRequestTeaModel(SmsSendRequest request) {
        Validate.notEmpty(request.getPhones());
        Validate.notEmpty(request.getSigns());
        Validate.notEmpty(request.getTemplateCode());
        Validate.notEmpty(request.getTemplateParams());
        int phoneSize = request.getPhones().size();
        int signsSize = request.getSigns().size();
        Map<String, String> templateParams = request.getTemplateParams();
        Map<String, Supplier<String>> valueSupplier = request.getValueSupplier();


        // 如果签名数量大于 1 且和手机号不匹配
        if(signsSize > 1 && signsSize != phoneSize) {
            throw new SmsException("签名和手机号不匹配");
        }
        // 如果签名和手机号不匹配，则默认都使用第一个签名
        if( phoneSize > 1 && signsSize != phoneSize) {
            String repeat = StringUtils.repeat(request.getSigns().iterator().next(), StringPools.COMMA, phoneSize);
            request.signs(StringUtils.defaultSplit(repeat));
        }

        /**
         *  处理模版参数。数量需要和手机号，签名保持一致
         *  首先获取两个 map 中相同的 key. 动态定义的 value 会覆盖静态定义的 value.
         */
        List<Map<String,String>> templateParamsList = new ArrayList<>();
        Set<String> sameKeys = Sets.getSameKeys(valueSupplier.keySet(), templateParams.keySet());


        for (int i = 0; i < phoneSize; i++) {
            Map<String,String> templateParamsMap = new HashMap<>();
            for (Map.Entry<String,String> result : templateParams.entrySet()) {
                String key = result.getKey();
                String value = result.getValue();
                if(sameKeys.contains(key)) {
                    value = valueSupplier.get(key).get();
                }
                templateParamsMap.put(result.getKey(),value);
            }
            templateParamsList.add(templateParamsMap);
        }

        if(request.getAction().equals(SmsAction.SEND_SMS)) {
            return new SendSmsRequest()
                    .setPhoneNumbers(JsonUtils.toJson(request.getPhones()))
                    .setSignName(JsonUtils.toJson(request.getSigns()))
                    .setTemplateCode(request.getTemplateCode())
                    .setTemplateParam(JsonUtils.toJson(request.getTemplateParams()));
        }
        if(request.getAction().equals(SmsAction.SEND_BATCH_SMS)) {
            return new SendBatchSmsRequest()
                    .setPhoneNumberJson(JsonUtils.toJson(request.getPhones()))
                    .setSignNameJson(JsonUtils.toJson(request.getSigns()))
                    .setTemplateCode(request.getTemplateCode())
                    .setTemplateParamJson(JsonUtils.toJson(templateParamsList));
        }
        return null;
    }
}
