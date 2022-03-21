package com.jcohy.framework.starter.sms.request;

import com.jcohy.framework.starter.sms.SmsAction;
import org.apache.commons.lang3.Validate;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/16/22:18:32
 * @since 2022.0.1
 */
public class SmsShortUrlRequest extends SmsRequest {

    private String sourceUrl;

    private String shortUrlName;

    private String effectiveDays;

    private String shortUrl;


    public SmsShortUrlRequest(SmsAction action) {
        super(action);
        Validate.notNull(action,"action 不能为空 !");
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public SmsShortUrlRequest sourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
        return this;
    }

    public String getShortUrlName() {
        return shortUrlName;
    }

    public SmsShortUrlRequest shortUrlName(String shortUrlName) {
        this.shortUrlName = shortUrlName;
        return this;
    }

    public String getEffectiveDays() {
        return effectiveDays;
    }

    public SmsShortUrlRequest effectiveDays(String effectiveDays) {
        this.effectiveDays = effectiveDays;
        return this;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public SmsShortUrlRequest shortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
        return this;
    }
}
