package com.jcohy.framework.starter.sms.ali;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.AddShortUrlRequest;
import com.aliyun.dysmsapi20170525.models.AddShortUrlResponse;
import com.aliyun.dysmsapi20170525.models.AddSmsSignRequest;
import com.aliyun.dysmsapi20170525.models.AddSmsSignResponse;
import com.aliyun.dysmsapi20170525.models.AddSmsTemplateRequest;
import com.aliyun.dysmsapi20170525.models.AddSmsTemplateResponse;
import com.aliyun.dysmsapi20170525.models.DeleteShortUrlRequest;
import com.aliyun.dysmsapi20170525.models.DeleteShortUrlResponse;
import com.aliyun.dysmsapi20170525.models.DeleteSmsSignRequest;
import com.aliyun.dysmsapi20170525.models.DeleteSmsSignResponse;
import com.aliyun.dysmsapi20170525.models.DeleteSmsTemplateRequest;
import com.aliyun.dysmsapi20170525.models.DeleteSmsTemplateResponse;
import com.aliyun.dysmsapi20170525.models.ListTagResourcesRequest;
import com.aliyun.dysmsapi20170525.models.ListTagResourcesResponse;
import com.aliyun.dysmsapi20170525.models.ModifySmsSignRequest;
import com.aliyun.dysmsapi20170525.models.ModifySmsSignResponse;
import com.aliyun.dysmsapi20170525.models.ModifySmsTemplateRequest;
import com.aliyun.dysmsapi20170525.models.ModifySmsTemplateResponse;
import com.aliyun.dysmsapi20170525.models.QuerySendDetailsRequest;
import com.aliyun.dysmsapi20170525.models.QuerySendDetailsResponse;
import com.aliyun.dysmsapi20170525.models.QuerySendStatisticsRequest;
import com.aliyun.dysmsapi20170525.models.QuerySendStatisticsResponse;
import com.aliyun.dysmsapi20170525.models.QueryShortUrlRequest;
import com.aliyun.dysmsapi20170525.models.QueryShortUrlResponse;
import com.aliyun.dysmsapi20170525.models.QuerySmsSignListRequest;
import com.aliyun.dysmsapi20170525.models.QuerySmsSignListResponse;
import com.aliyun.dysmsapi20170525.models.QuerySmsSignRequest;
import com.aliyun.dysmsapi20170525.models.QuerySmsSignResponse;
import com.aliyun.dysmsapi20170525.models.QuerySmsTemplateListRequest;
import com.aliyun.dysmsapi20170525.models.QuerySmsTemplateListResponse;
import com.aliyun.dysmsapi20170525.models.QuerySmsTemplateRequest;
import com.aliyun.dysmsapi20170525.models.QuerySmsTemplateResponse;
import com.aliyun.dysmsapi20170525.models.SendBatchSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendBatchSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.TagResourcesRequest;
import com.aliyun.dysmsapi20170525.models.TagResourcesResponse;
import com.aliyun.dysmsapi20170525.models.UntagResourcesRequest;
import com.aliyun.dysmsapi20170525.models.UntagResourcesResponse;
import com.aliyun.tea.TeaModel;
import org.apache.commons.lang3.Validate;

import org.springframework.core.convert.converter.Converter;

import com.jcohy.framework.starter.redis.RedisUtils;
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
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2022/3/17:18:44
 * @since 2022.0.1
 */
public class AliSmsTemplate implements SmsTemplate {

    /**
     * 类型转换.
     */
    public static final Converter<SmsRequest, TeaModel> CONVERTER = new AliSmsRequestConverter();

    /**
     * smsProperties.
     */
    private final SmsProperties smsProperties;

    /**
     * client 对象，用来发送短信.
     */
    private final Client client;

	private static final int SUCCESS = 200;

	private static final String FAIL = "fail";

	private static final String OK = "ok";

    private final RedisUtils<String, String> redisUtils;

    public AliSmsTemplate(SmsProperties smsProperties, Client client, RedisUtils<String, String> redisUtils) {
        this.smsProperties = smsProperties;
        this.client = client;
        this.redisUtils = redisUtils;
    }

    @Override
    public Result<Object> send(SmsSendRequest request) {
        Validate.validIndex(request.getPhones(), 2, "手机号只能有一个");
		// 默认以 request 中的签名为准，如果不存在，则使用 properties 中的签名
		String sign = this.smsProperties.getSignName();
		request.signs(request.getSigns() != null ? request.getSigns() :  Collections.singletonList(sign));
        SendSmsRequest model = (SendSmsRequest) CONVERTER.convert(request);
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = this.client.sendSms(model);
			if(request.isValidate() && sendSmsResponse.getBody().getMessage().equals(OK)) {
				store(model.getPhoneNumbers(),model.getTemplateCode());
			}
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(sendSmsResponse.getBody());
    }

	private void store(String phoneNumbers, String templateCode) {
		String cacheKey = cacheKey(this.smsProperties.getCacheKey(),phoneNumbers);
		this.redisUtils.setExpire(cacheKey, templateCode, this.smsProperties.getTimeout().toMillis());
	}

	@Override
    public Result<Object> sendBatch(SmsSendRequest request) {
        SendBatchSmsRequest model = (SendBatchSmsRequest) CONVERTER.convert(request);
        SendBatchSmsResponse response;
        try {
            response = this.client.sendBatchSms(model);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(ServiceStatusCode.SUCCESS.getCode(), response.getBody().message, response);
    }

    @Override
    public Result<Object> querySmsDetails(SmsQueryDetailsRequest request) {
        QuerySendDetailsRequest model = (QuerySendDetailsRequest) CONVERTER.convert(request);
        QuerySendDetailsResponse response;
        try {
            response = this.client.querySendDetails(model);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(ServiceStatusCode.SUCCESS.getCode(), response.getBody().message, response);
    }

    @Override
    public Result<Object> querySmsStatistics(SmsQueryDetailsRequest request) {
        QuerySendStatisticsRequest model = (QuerySendStatisticsRequest) CONVERTER.convert(request);
        QuerySendStatisticsResponse response;
        try {
            response = this.client.querySendStatistics(model);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(ServiceStatusCode.SUCCESS.getCode(), response.getBody().message, response);
    }

    @Override
    public Result<Object> addSmsSign(SmsSignRequest request) {
        AddSmsSignRequest model = (AddSmsSignRequest) CONVERTER.convert(request);
        AddSmsSignResponse response;
        try {
            response = this.client.addSmsSign(model);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(ServiceStatusCode.SUCCESS.getCode(), response.getBody().message, response);
    }

    @Override
    public Result<Object> deleteSmsSign(SmsSignRequest request) {
        DeleteSmsSignRequest model = (DeleteSmsSignRequest) CONVERTER.convert(request);
        DeleteSmsSignResponse response;
        try {
            response = this.client.deleteSmsSign(model);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(ServiceStatusCode.SUCCESS.getCode(), response.getBody().message, response);
    }

    @Override
    public Result<Object> modifySmsSign(SmsSignRequest request) {
        ModifySmsSignRequest model = (ModifySmsSignRequest) CONVERTER.convert(request);
        ModifySmsSignResponse response;
        try {
            response = this.client.modifySmsSign(model);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(ServiceStatusCode.SUCCESS.getCode(), response.getBody().message, response);
    }

    @Override
    public Result<Object> querySmsSign(SmsSignRequest request) {
        QuerySmsSignListRequest model = (QuerySmsSignListRequest) CONVERTER.convert(request);
        QuerySmsSignListResponse response;
        try {
            response = this.client.querySmsSignList(model);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(ServiceStatusCode.SUCCESS.getCode(), response.getBody().message, response);
    }

    @Override
    public Result<Object> querySmsSignStatus(SmsSignRequest request) {
        QuerySmsSignRequest model = (QuerySmsSignRequest) CONVERTER.convert(request);
        QuerySmsSignResponse response;
        try {
            response = this.client.querySmsSign(model);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(ServiceStatusCode.SUCCESS.getCode(), response.getBody().message, response);
    }

    @Override
    public Result<Object> addSmsTemplate(SmsTemplateRequest request) {
        AddSmsTemplateRequest model = (AddSmsTemplateRequest) CONVERTER.convert(request);
        AddSmsTemplateResponse response;
        try {
            response = this.client.addSmsTemplate(model);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(ServiceStatusCode.SUCCESS.getCode(), response.getBody().message, response);
    }

    @Override
    public Result<Object> deleteSmsTemplate(SmsTemplateRequest request) {
        DeleteSmsTemplateRequest model = (DeleteSmsTemplateRequest) CONVERTER.convert(request);
        DeleteSmsTemplateResponse response;
        try {
            response = this.client.deleteSmsTemplate(model);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(ServiceStatusCode.SUCCESS.getCode(), response.getBody().message, response);
    }

    @Override
    public Result<Object> modifySmsTemplate(SmsTemplateRequest request) {
        ModifySmsTemplateRequest model = (ModifySmsTemplateRequest) CONVERTER.convert(request);
        ModifySmsTemplateResponse response;
        try {
            response = this.client.modifySmsTemplate(model);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(ServiceStatusCode.SUCCESS.getCode(), response.getBody().message, response);
    }

    @Override
    public Result<Object> querySmsTemplate(SmsTemplateRequest request) {
        QuerySmsTemplateListRequest model = (QuerySmsTemplateListRequest) CONVERTER.convert(request);
        QuerySmsTemplateListResponse response;
        try {
            response = this.client.querySmsTemplateList(model);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(ServiceStatusCode.SUCCESS.getCode(), response.getBody().message, response);
    }

    @Override
    public Result<Object> querySmsTemplateStatus(SmsTemplateRequest request) {
        QuerySmsTemplateRequest model = (QuerySmsTemplateRequest) CONVERTER.convert(request);
        QuerySmsTemplateResponse response;
        try {
            response = this.client.querySmsTemplate(model);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(ServiceStatusCode.SUCCESS.getCode(), response.getBody().message, response);
    }

    @Override
    public Result<Object> addShortUrl(SmsShortUrlRequest request) {
        AddShortUrlRequest model = (AddShortUrlRequest) CONVERTER.convert(request);
        AddShortUrlResponse response;
        try {
            response = this.client.addShortUrl(model);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(ServiceStatusCode.SUCCESS.getCode(), response.getBody().message, response);
    }

    @Override
    public Result<Object> deleteShortUrl(SmsShortUrlRequest request) {
        DeleteShortUrlRequest model = (DeleteShortUrlRequest) CONVERTER.convert(request);
        DeleteShortUrlResponse response;
        try {
            response = this.client.deleteShortUrl(model);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(ServiceStatusCode.SUCCESS.getCode(), response.getBody().message, response);
    }

    @Override
    public Result<Object> queryShortUrl(SmsShortUrlRequest request) {
        QueryShortUrlRequest model = (QueryShortUrlRequest) CONVERTER.convert(request);
        QueryShortUrlResponse response;
        try {
            response = this.client.queryShortUrl(model);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(ServiceStatusCode.SUCCESS.getCode(), response.getBody().message, response);
    }

    @Override
    public Result<Object> addTag(SmsTagRequest request) {
        TagResourcesRequest model = (TagResourcesRequest) CONVERTER.convert(request);
        TagResourcesResponse response;
        try {
            response = this.client.tagResources(model);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(response);
    }

    @Override
    public Result<Object> deleteTag(SmsTagRequest request) {
        UntagResourcesRequest model = (UntagResourcesRequest) CONVERTER.convert(request);
        UntagResourcesResponse response;
        try {
            response = this.client.untagResources(model);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(response);
    }

    @Override
    public Result<Object> queryTag(SmsTagRequest request) {
        ListTagResourcesRequest model = (ListTagResourcesRequest) CONVERTER.convert(request);
        ListTagResourcesResponse response;
        try {
            response = this.client.listTagResources(model);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(response);
    }

    @Override
    public boolean validate(String phone, Map<String, String> params) {
        return false;
    }

}
