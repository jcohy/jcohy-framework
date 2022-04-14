package com.jcohy.framework.starter.sms.tencent;

import com.tencentcloudapi.common.AbstractModel;
import com.tencentcloudapi.sms.v20210111.models.AddSmsSignRequest;
import com.tencentcloudapi.sms.v20210111.models.DeleteSmsSignRequest;
import com.tencentcloudapi.sms.v20210111.models.DescribeSmsSignListRequest;
import com.tencentcloudapi.sms.v20210111.models.ModifySmsSignRequest;
import com.tencentcloudapi.sms.v20210111.models.PullSmsSendStatusByPhoneNumberRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;

import org.springframework.core.convert.converter.Converter;

import com.jcohy.framework.starter.sms.SmsAction;
import com.jcohy.framework.starter.sms.SmsHelper;
import com.jcohy.framework.starter.sms.request.SmsQueryDetailsRequest;
import com.jcohy.framework.starter.sms.request.SmsRequest;
import com.jcohy.framework.starter.sms.request.SmsSendRequest;
import com.jcohy.framework.starter.sms.request.SmsShortUrlRequest;
import com.jcohy.framework.starter.sms.request.SmsSignRequest;
import com.jcohy.framework.starter.sms.request.SmsTagRequest;
import com.jcohy.framework.starter.sms.request.SmsTemplateRequest;
import com.jcohy.framework.starter.sms.request.sign.TencentSmsSignRequest;
import com.jcohy.framework.utils.DateTimeUtils;
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
public class TencentSmsRequestConverter implements Converter<SmsRequest, AbstractModel> {

    @Override
    public AbstractModel convert(SmsRequest request) {
        AbstractModel model = null;
        if (request instanceof SmsSendRequest) {
            model = buildSmsSendRequestTeaModel((SmsSendRequest) request);
        }
        if (request instanceof SmsQueryDetailsRequest) {
            model = buildSmsQueryDetailsRequestTeaModel((SmsQueryDetailsRequest) request);
        }
        if (request instanceof SmsSignRequest) {
            model = buildSmsSignRequestTeaModel((TencentSmsSignRequest) request);
        }
        if (request instanceof SmsTemplateRequest) {
            model = buildSmsTemplateRequestTeaModel((SmsTemplateRequest) request);
        }
        if (request instanceof SmsShortUrlRequest) {
            model = buildSmsShortUrlRequestTeaModel((SmsShortUrlRequest) request);
        }
        if (request instanceof SmsTagRequest) {
            model = buildSmsTagRequestTeaModel((SmsTagRequest) request);
        }
        return model;
    }

    private AbstractModel buildSmsTagRequestTeaModel(SmsTagRequest request) {
        return null;
    }

    private AbstractModel buildSmsShortUrlRequestTeaModel(SmsShortUrlRequest request) {
        return null;
    }

    private AbstractModel buildSmsTemplateRequestTeaModel(SmsTemplateRequest request) {
        return null;
    }

    private AbstractModel buildSmsSignRequestTeaModel(TencentSmsSignRequest request) {
        if (request.getAction().equals(SmsAction.ADD_SMS_SIGN)) {
            AddSmsSignRequest model = new AddSmsSignRequest();
            model.setSignName(request.getSignName());
            model.setSignType(request.getSignType());
            model.setDocumentType(request.getDocumentType());
            model.setInternational(request.getInternational());
            model.setSignPurpose(request.getSignPurpose());
            model.setCommissionImage(request.getCommissionImage());
            model.setRemark(request.getRemark());
            return model;
        }
        if (request.getAction().equals(SmsAction.DELETE_SMS_SIGN)) {
            DeleteSmsSignRequest model = new DeleteSmsSignRequest();
            model.setSignId(request.getSignId());
            return model;
        }
        if (request.getAction().equals(SmsAction.MODIFY_SMS_SIGN)) {
            ModifySmsSignRequest model = new ModifySmsSignRequest();
            model.setSignId(request.getSignId());
            model.setSignName(request.getSignName());
            model.setSignType(request.getSignType());
            model.setDocumentType(request.getDocumentType());
            model.setInternational(request.getInternational());
            model.setSignPurpose(request.getSignPurpose());
            model.setProofImage(request.getProofImage());
            model.setCommissionImage(request.getCommissionImage());
            model.setRemark(request.getRemark());
            return model;
        }
        if (request.getAction().equals(SmsAction.QUERY_SMS_SIGN)) {
            DescribeSmsSignListRequest model = new DescribeSmsSignListRequest();
            model.setInternational(request.getInternational());
            model.setSignIdSet(new Long[] { request.getSignId() });
            return model;
        }
        return null;
    }

    private AbstractModel buildSmsQueryDetailsRequestTeaModel(SmsQueryDetailsRequest request) {
        PullSmsSendStatusByPhoneNumberRequest model = new PullSmsSendStatusByPhoneNumberRequest();
        model.setBeginTime(DateTimeUtils.toMilliseconds(request.getStartDate(), "yyyyMMdd"));
        model.setEndTime(DateTimeUtils.toMilliseconds(request.getEndDate(), "yyyyMMdd"));
        model.setOffset(request.getPageIndex());
        model.setLimit(request.getPageSize());
        model.setPhoneNumber(request.getPhone());
        return model;
    }

    private SendSmsRequest buildSmsSendRequestTeaModel(SmsSendRequest request) {
        SendSmsRequest model = new SendSmsRequest();
        String[] phones = request.getPhones().toArray((new String[0]));
        if (request.getAction().equals(SmsAction.SEND_SMS)) {
            SmsHelper.validSize(request.getPhones(), 1, "如果需要向多个手机号发送信息，请使用 SmsAction.SEND_BATCH_SMS!");
        }
        model.setPhoneNumberSet(phones);
        model.setSignName(StringUtils.collectionToCommaDelimitedString(request.getSigns()));
        model.setTemplateId(request.getTemplateCode());
        model.setTemplateParamSet(request.getTemplateParams().values().toArray((new String[0])));
        return model;
    }

}
