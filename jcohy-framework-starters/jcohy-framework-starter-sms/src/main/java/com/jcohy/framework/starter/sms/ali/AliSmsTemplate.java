package com.jcohy.framework.starter.sms.ali;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.tea.TeaModel;
import com.jcohy.framework.starter.redis.RedisUtils;
import com.jcohy.framework.starter.sms.SmsTemplate;
import com.jcohy.framework.starter.sms.props.SmsProperties;
import com.jcohy.framework.starter.sms.request.*;
import com.jcohy.framework.utils.api.Result;
import com.jcohy.framework.utils.api.ResultStatusCode;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2022/3/17:18:44
 * @since 2022.0.1
 */
public class AliSmsTemplate implements SmsTemplate {

    private static final Logger logger = LoggerFactory.getLogger(AliSmsTemplate.class);

    /**
     * 类型转换.
     */
    public static final Converter<SmsRequest, TeaModel> converter = new AliSmsRequestConverter();
    /**
     * smsProperties
     */
    private final SmsProperties smsProperties;

    /**
     * client 对象，用来发送短信.
     */
    private final Client client;

    private final RedisUtils<String, String> redisUtils;

    public AliSmsTemplate(SmsProperties smsProperties, Client client, RedisUtils<String, String> redisUtils) {
        this.smsProperties = smsProperties;
        this.client = client;
        this.redisUtils = redisUtils;
    }

    @Override
    public Result<Object> send(SmsSendRequest request) {
        SendSmsRequest model = (SendSmsRequest) converter.convert(request);
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = client.sendSms(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data(sendSmsResponse.getBody());
    }

    @Override
    public Result<Object> sendBatch(SmsSendRequest request) {
        SendBatchSmsRequest model = (SendBatchSmsRequest) converter.convert(request);
        SendBatchSmsResponse response;
        try {
            response = client.sendBatchSms(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data( 10000,response.getBody().message,response);
    }

    @Override
    public Result<Object> querySmsDetails(SmsQueryDetailsRequest request) {
        QuerySendDetailsRequest model = (QuerySendDetailsRequest) converter.convert(request);
        QuerySendDetailsResponse response;
        try {
            response = client.querySendDetails(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data( 10000,response.getBody().message,response);
    }

    @Override
    public Result<Object> querySmsStatistics(SmsQueryDetailsRequest request) {
        QuerySendStatisticsRequest model = (QuerySendStatisticsRequest) converter.convert(request);
        QuerySendStatisticsResponse response;
        try {
            response = client.querySendStatistics(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data( 10000,response.getBody().message,response);
    }

    @Override
    public Result<Object> addSmsSign(SmsSignRequest request) {
        AddSmsSignRequest model = (AddSmsSignRequest) converter.convert(request);
        AddSmsSignResponse response;
        try {
            response = client.addSmsSign(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data( 10000,response.getBody().message,response);
    }

    @Override
    public Result<Object> deleteSmsSign(SmsSignRequest request) {
        DeleteSmsSignRequest model = (DeleteSmsSignRequest) converter.convert(request);
        DeleteSmsSignResponse response;
        try {
            response = client.deleteSmsSign(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data( 10000,response.getBody().message,response);
    }

    @Override
    public Result<Object> modifySmsSign(SmsSignRequest request) {
        ModifySmsSignRequest model = (ModifySmsSignRequest) converter.convert(request);
        ModifySmsSignResponse response;
        try {
            response = client.modifySmsSign(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data( 10000,response.getBody().message,response);
    }

    @Override
    public Result<Object> querySmsSign(SmsSignRequest request) {
        QuerySmsSignListRequest model = (QuerySmsSignListRequest) converter.convert(request);
        QuerySmsSignListResponse response;
        try {
            response = client.querySmsSignList(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data( 10000,response.getBody().message,response);
    }

    @Override
    public Result<Object> querySmsSignStatus(SmsSignRequest request) {
        QuerySmsSignRequest model = (QuerySmsSignRequest) converter.convert(request);
        QuerySmsSignResponse response;
        try {
            response = client.querySmsSign(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data( 10000,response.getBody().message,response);
    }

    @Override
    public Result<Object> addSmsTemplate(SmsTemplateRequest request) {
        AddSmsTemplateRequest model = (AddSmsTemplateRequest) converter.convert(request);
        AddSmsTemplateResponse response;
        try {
            response = client.addSmsTemplate(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data( 10000,response.getBody().message,response);
    }

    @Override
    public Result<Object> deleteSmsTemplate(SmsTemplateRequest request) {
        DeleteSmsTemplateRequest model = (DeleteSmsTemplateRequest) converter.convert(request);
        DeleteSmsTemplateResponse response;
        try {
            response = client.deleteSmsTemplate(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data( 10000,response.getBody().message,response);
    }

    @Override
    public Result<Object> modifySmsTemplate(SmsTemplateRequest request) {
        ModifySmsTemplateRequest model = (ModifySmsTemplateRequest) converter.convert(request);
        ModifySmsTemplateResponse response;
        try {
            response = client.modifySmsTemplate(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data( 10000,response.getBody().message,response);
    }

    @Override
    public Result<Object> querySmsTemplate(SmsTemplateRequest request) {
        QuerySmsTemplateListRequest model = (QuerySmsTemplateListRequest) converter.convert(request);
        QuerySmsTemplateListResponse response;
        try {
            response = client.querySmsTemplateList(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data( 10000,response.getBody().message,response);
    }

    @Override
    public Result<Object> querySmsTemplateStatus(SmsTemplateRequest request) {
        QuerySmsTemplateRequest model = (QuerySmsTemplateRequest) converter.convert(request);
        QuerySmsTemplateResponse response;
        try {
            response = client.querySmsTemplate(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data( 10000,response.getBody().message,response);
    }

    @Override
    public Result<Object> addShortUrl(SmsShortUrlRequest request) {
        AddShortUrlRequest model = (AddShortUrlRequest) converter.convert(request);
        AddShortUrlResponse response;
        try {
            response = client.addShortUrl(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data( 10000,response.getBody().message,response);
    }

    @Override
    public Result<Object> deleteShortUrl(SmsShortUrlRequest request) {
        DeleteShortUrlRequest model = (DeleteShortUrlRequest) converter.convert(request);
        DeleteShortUrlResponse response;
        try {
            response = client.deleteShortUrl(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data( 10000,response.getBody().message,response);
    }

    @Override
    public Result<Object> queryShortUrl(SmsShortUrlRequest request) {
        QueryShortUrlRequest model = (QueryShortUrlRequest) converter.convert(request);
        QueryShortUrlResponse response;
        try {
            response = client.queryShortUrl(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data( 10000,response.getBody().message,response);
    }

    @Override
    public Result<Object> addTag(SmsTagRequest request) {
        TagResourcesRequest model = (TagResourcesRequest) converter.convert(request);
        TagResourcesResponse response;
        try {
            response = client.tagResources(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data( response);
    }

    @Override
    public Result<Object> deleteTag(SmsTagRequest request) {
        UntagResourcesRequest model = (UntagResourcesRequest) converter.convert(request);
        UntagResourcesResponse response;
        try {
            response = client.untagResources(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data( response);
    }

    @Override
    public Result<Object> queryTag(SmsTagRequest request) {
        ListTagResourcesRequest model = (ListTagResourcesRequest) converter.convert(request);
        ListTagResourcesResponse response;
        try {
            response = client.listTagResources(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.data( response);
    }

    @Override
    public boolean validate(String phone, Map<String, String> params) {
        return false;
    }
}
