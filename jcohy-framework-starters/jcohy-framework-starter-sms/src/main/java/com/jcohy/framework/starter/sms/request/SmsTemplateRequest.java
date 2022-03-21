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
 * @version 2022.0.1 3/16/22:18:28
 * @since 2022.0.1
 */
public class SmsTemplateRequest extends SmsRequest {

    private Integer templateType;

    private String templateName;

    private String templateContent;

    private String remark;

    private String templateCode;

    private Integer pageIndex;

    private Integer pageSize;

    public SmsTemplateRequest(SmsAction action) {
        super(action);
        Validate.notNull(action,"action 不能为空 !");
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public SmsTemplateRequest templateType(Integer templateType) {
        this.templateType = templateType;
        return this;
    }

    public String getTemplateName() {
        return templateName;
    }

    public SmsTemplateRequest templateName(String templateName) {
        this.templateName = templateName;
        return this;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public SmsTemplateRequest templateContent(String templateContent) {
        this.templateContent = templateContent;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public SmsTemplateRequest remark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public SmsTemplateRequest templateCode(String templateCode) {
        this.templateCode = templateCode;
        return this;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public SmsTemplateRequest pageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public SmsTemplateRequest pageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
