package com.jcohy.framework.starter.sms;

import java.util.Map;
import java.util.function.BiConsumer;

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
     * 发送短信.
     * @param request 短信发送内容
     * @return 发送短信结果
     */
    String send(SmsRequest request);

    /**
     * 验证验证码.
     * @param phone 手机号
     * @param params 验证码
     * @return {@code true} 验证成功
     */
    boolean validate(String phone, Map<String, String> params);

    /**
     * 存储 redis.
     * @param request request
     * @param consumer consumer
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
