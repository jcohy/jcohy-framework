package com.jcohy.framework.starter.sms.request;

import com.jcohy.framework.starter.sms.SmsAction;
import com.jcohy.framework.utils.StringUtils;
import org.apache.commons.lang3.Validate;

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
public class SmsRequestFactory {

    public static SmsRequest action(SmsAction smsAction) {
        String action = smsAction.getAction();
        Validate.notNull(action,"action not be null !");
        if(SmsAction.SEND_SMS.getAction().equals(action) || SmsAction.SEND_BATCH_SMS.getAction().equals(action)) {
            return new SmsSendRequest(action);
        }
        if(SmsAction.QUERY_SMS_DETAILS.getAction().equals(action) || SmsAction.QUERY_SMS_STATISTICS.getAction().equals(action)) {
            return new SmsQueryDetailsRequest(action);
        }
        if(StringUtils.containsIgnoreCase(action,"sign")) {
            return new SmsSignRequest(action);
        }
        if(StringUtils.containsIgnoreCase(action,"template")) {
            return new SmsTemplateRequest(action);
        }
        if(StringUtils.containsIgnoreCase(action,"ShortUrl")) {
            return new SmsShortUrlRequest(action);
        }
        if(StringUtils.containsIgnoreCase(action,"tag")) {
            return new SmsTagRequest(action);
        }
        return null;
    }

}
