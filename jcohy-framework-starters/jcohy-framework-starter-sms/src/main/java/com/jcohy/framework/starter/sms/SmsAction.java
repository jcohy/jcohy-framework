package com.jcohy.framework.starter.sms;

/**
 * 描述: api 操作的动作。根据不同的动作构建不同的 request.
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/17/22:10:06
 * @since 2022.0.1
 */
public enum SmsAction {

    /**
     * 发送短信.
     */
    SEND_SMS("SendSms"),
    /**
     * 批量发送短信.
     */
    SEND_BATCH_SMS("SendBatchSms"),
    /**
     * 查询短信发送详情.
     */
    QUERY_SMS_DETAILS("QuerySmsDetails"),
    /**
     * 查询统计信息.
     */
    QUERY_SMS_STATISTICS("QuerySmsStatistics"),
    /**
     * 添加短信签名.
     */
    ADD_SMS_SIGN("AddSmsSign"),
    /**
     * 删除短信签名.
     */
    DELETE_SMS_SIGN("Delete_Sms_Sign"),
    /**
     * 修改未审核通过的短信签名.
     */
    MODIFY_SMS_SIGN("ModifySmsSign"),
    /**
     * 查询短信签名申请状态.
     */
    QUERY_SMS_SIGN("QuerySmsSign"),
    /**
     * 查询短信签名申请状态.
     */
    QUERY_SMS_SIGN_STATUS("QuerySmsSignStatus"),
    /**
     * 添加短信模版.
     */
    ADD_SMS_TEMPLATE("AddSmsTemplate"),
    /**
     * 删除短信模版.
     */
    DELETE_SMS_TEMPLATE("DeleteSmsTemplate"),
    /**
     * 修改未通过审核的短信模版.
     */
    MODIFY_SMS_TEMPLATE("ModifySmsTemplate"),
    /**
     * 查询短信模版列表.
     */
    QUERY_SMS_TEMPLATE("QuerySmsTemplate"),
    /**
     * 查询短信模版申请状态.
     */
    QUERY_SMS_TEMPLATE_STATUS("QuerySmsTemplateStatus"),
    /**
     * 创建短链.
     */
    ADD_SHORT_URL("AddShortUrl"),
    /**
     * 删除短链.
     */
    DELETE_SHORT_URL("DeleteShortUrl"),
    /**
     * 查询短链状态..
     */
    QUERY_SHORT_URL("QueryShortUrl"),
    /**
     * 添加模版标签..
     */
    ADD_TAG("AddTag"),
    /**
     * 删除模版标签.
     */
    DELETE_TAG("DeleteTag"),
    /**
     * 查询模版标签.
     */
    QUERY_TAG("QueryTag");

    /**
     * action.
     */
    private final String action;

    SmsAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return this.action;
    }

}
