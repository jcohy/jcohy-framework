package com.jcohy.framework.starter.sms.tencent;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.aliyun.dysmsapi20170525.models.SendBatchSmsRequest;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.apache.commons.lang3.Validate;
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
import com.jcohy.framework.utils.StringUtils;
import com.jcohy.framework.utils.api.Result;
import com.jcohy.framework.utils.constant.StringPools;

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
    public final Converter<SmsRequest, SendSmsRequest> CONVERTER = new TencentSmsRequestConverter();

    private final SmsProperties properties;

    private final SmsClient client;

    private final RedisUtils<String, String> redisUtils;

    public TencentSmsTemplate(SmsProperties properties, SmsClient client, RedisUtils<String, String> redisUtils) {
        this.properties = properties;
        this.client = client;
        this.redisUtils = redisUtils;
    }

    @Override
    public Result<Object> send(SmsSendRequest request) {
		SmsHelper.validSize(request.getPhones(), 1, "手机号只能有一个！");
        // 默认将 phone 格式化为 +86
        if (!request.isGlobal()) {
			request.phones(SmsHelper.formatPhones(request.getPhones()));
        }

		// 默认以 request 中的签名为准，如果不存在，则使用 properties 中的签名
		request.signs(request.getSigns() != null ? request.getSigns() :  Collections.singletonList(this.properties.getSignName()));

		SendSmsRequest smsRequest = this.CONVERTER.convert(request);
        smsRequest.setSmsSdkAppid(this.properties.getSmsSdkAppId());
        SendSmsResponse response;
        try {
            response = this.client.SendSms(smsRequest);
        }
        catch (TencentCloudSDKException ex) {
            throw new RuntimeException(ex);
        }
        return Result.data(response);
    }

    @Override
    public Result<Object> sendBatch(SmsSendRequest request) {
		int signSize = request.getSigns().size();
		if( signSize > 1 && signSize == request.getPhones().size()) {
			List<Map<String, String>> maps = SmsHelper.processSmsSendTemplateParams(request);
			for(int i = 0 ; i < signSize; i++ ){
				SmsSendRequest smsSendRequest = new SmsSendRequest(SmsAction.SEND_SMS)
						.phones(request.getPhones().toArray(new String[0])[i])
						.signs(request.getSigns().toArray(new String[0])[i])
						.templateCode(request.getTemplateCode())
						.templateParams(maps.get(i));
				this.send(smsSendRequest);
			}
		} else {
			SendSmsRequest smsRequest = this.CONVERTER.convert(request);
			smsRequest.setSmsSdkAppid(this.properties.getSmsSdkAppId());
			SendSmsResponse response;
			try {
				response = this.client.SendSms(smsRequest);
			}
			catch (TencentCloudSDKException ex) {
				throw new RuntimeException(ex);
			}
			return Result.data(response);
		}
		return null;
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
        return null;
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
