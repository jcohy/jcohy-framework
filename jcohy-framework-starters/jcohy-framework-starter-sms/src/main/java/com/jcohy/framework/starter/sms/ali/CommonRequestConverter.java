package com.jcohy.framework.starter.sms.ali;

import com.aliyuncs.CommonRequest;

import org.springframework.core.convert.converter.Converter;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/15/22:10:41
 * @since 2022.0.1
 */
public class CommonRequestConverter implements Converter<AliSmsRequest, CommonRequest> {

    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    private static final String VERSION = "2017-05-25";

    @Override
    public CommonRequest convert(AliSmsRequest request) {
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setSysDomain(DOMAIN);
        commonRequest.setSysVersion(VERSION);
        commonRequest.setSysAction(request.getAction());
        commonRequest.setSysMethod(request.getMethod());
        request.getQueryParams().forEach(commonRequest::putQueryParameter);
        return commonRequest;
    }

}
