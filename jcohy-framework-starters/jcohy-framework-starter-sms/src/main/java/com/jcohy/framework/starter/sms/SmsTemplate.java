package com.jcohy.framework.starter.sms;

import java.util.Map;
import java.util.function.BiConsumer;

import com.jcohy.framework.starter.sms.request.SmsQueryDetailsRequest;
import com.jcohy.framework.starter.sms.request.SmsRequest;
import com.jcohy.framework.starter.sms.request.SmsSendRequest;
import com.jcohy.framework.starter.sms.request.SmsShortUrlRequest;
import com.jcohy.framework.starter.sms.request.SmsSignRequest;
import com.jcohy.framework.starter.sms.request.SmsTagRequest;
import com.jcohy.framework.starter.sms.request.SmsTemplateRequest;
import com.jcohy.framework.utils.api.Result;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/15/22:10:39
 * @since 2022.0.1
 */
public interface SmsTemplate {

    /**
     * 缓存键值.
     * @param prefix 前缀
     * @param phone 键值
     * @return 缓存键值返回
     */
    default String cacheKey(String prefix, String phone) {
        return prefix + SmsConstant.CAPTCHA_KEY + phone;
    }

    /**
     * 发送短信.
     * @param request request
     * @return 发送短信结果
     * @since 2022.0.1
     * @see SmsSendRequest
     */
    Result<Object> send(SmsSendRequest request);

    /**
     * 批量发送短信.
     * @param request request
     * @return 发送短信结果
     * @since 2022.0.1
     * @see SmsSendRequest
     */
    Result<Object> sendBatch(SmsSendRequest request);

    /**
     * 查询短信发送详情.
     * @param request request
     * @return 返回查询结果
     * @since 2022.0.1
     * @see SmsQueryDetailsRequest
     */
    Result<Object> querySmsDetails(SmsQueryDetailsRequest request);

    /**
     * 查询统计信息.
     * @param request request
     * @return 返回查询结果
     * @since 2022.0.1
     * @see SmsQueryDetailsRequest
     */
    Result<Object> querySmsStatistics(SmsQueryDetailsRequest request);

    /**
     * 添加短信签名.
     * @param request request
     * @return 返回结果
     * @since 2022.0.1
     * @see SmsSignRequest
     */
    Result<Object> addSmsSign(SmsSignRequest request);

    /**
     * 删除短信签名.
     * @param request request
     * @return 返回结果
     * @since 2022.0.1
     * @see SmsSignRequest
     */
    Result<Object> deleteSmsSign(SmsSignRequest request);

    /**
     * 修改未审核通过的短信签名.
     * @param request request
     * @return 返回结果
     * @since 2022.0.1
     * @see SmsSignRequest
     */
    Result<Object> modifySmsSign(SmsSignRequest request);

    /**
     * 查询短信签名列表.
     * @param request request
     * @return 返回结果
     * @since 2022.0.1
     * @see SmsSignRequest
     */
    Result<Object> querySmsSign(SmsSignRequest request);

    /**
     * 查询短信签名申请状态.
     * @param request request
     * @return 返回结果
     * @since 2022.0.1
     * @see SmsSignRequest
     */
    Result<Object> querySmsSignStatus(SmsSignRequest request);

    /**
     * 添加短信模版.
     * @param request request
     * @return 返回结果
     * @since 2022.0.1
     * @see SmsTemplateRequest
     */
    Result<Object> addSmsTemplate(SmsTemplateRequest request);

    /**
     * 删除短信模版.
     * @param request request
     * @return 返回结果
     * @since 2022.0.1
     * @see SmsTemplateRequest
     */
    Result<Object> deleteSmsTemplate(SmsTemplateRequest request);

    /**
     * 修改未通过审核的短信模版.
     * @param request request
     * @return 返回结果
     * @since 2022.0.1
     * @see SmsTemplateRequest
     */
    Result<Object> modifySmsTemplate(SmsTemplateRequest request);

    /**
     * 查询短信模版列表.
     * @param request request
     * @return 返回结果
     * @since 2022.0.1
     * @see SmsTemplateRequest
     */
    Result<Object> querySmsTemplate(SmsTemplateRequest request);

    /**
     * 查询短信模版申请状态.
     * @param request request
     * @return 返回结果
     * @since 2022.0.1
     * @see SmsTemplateRequest
     */
    Result<Object> querySmsTemplateStatus(SmsTemplateRequest request);

    /**
     * 创建短链.
     * @param request request
     * @return 返回结果
     * @since 2022.0.1
     * @see SmsShortUrlRequest
     */
    Result<Object> addShortUrl(SmsShortUrlRequest request);

    /**
     * 删除短链.
     * @param request request
     * @return 返回结果
     * @since 2022.0.1
     * @see SmsShortUrlRequest
     */
    Result<Object> deleteShortUrl(SmsShortUrlRequest request);

    /**
     * 查询短链状态.
     * @param request request
     * @return 返回结果
     * @since 2022.0.1
     * @see SmsShortUrlRequest
     */
    Result<Object> queryShortUrl(SmsShortUrlRequest request);

    /**
     * 添加模版标签.
     * @param request 短信发送内容
     * @return 返回结果
     * @since 2022.0.1
     * @see SmsTagRequest
     */
    Result<Object> addTag(SmsTagRequest request);

    /**
     * 删除模版标签.
     * @param request request
     * @return 返回结果
     * @since 2022.0.1
     * @see SmsTagRequest
     */
    Result<Object> deleteTag(SmsTagRequest request);

    /**
     * 查询模版标签.
     * @param request request
     * @return 返回结果
     * @since 2022.0.1
     * @see SmsTagRequest
     */
    Result<Object> queryTag(SmsTagRequest request);

    /**
     * 验证验证码.
     * @param phone 手机号
     * @param params 验证码
     * @return {@code true} 验证成功
     * @since 2022.0.1
     */
    boolean validate(String phone, Map<String, String> params);

    /**
     * 存储 redis.
     * @param request request
     * @param consumer consumer
     * @since 2022.0.1
     */
    static void store(SmsRequest request, BiConsumer<String, Map<String, String>> consumer) {
        Map<String, Map<String, String>> store = request.getStore();
        for (Map.Entry<String, Map<String, String>> params : store.entrySet()) {
            String key = params.getKey();
            Map<String, String> value = params.getValue();
            consumer.accept(key, value);
        }
    }

}
