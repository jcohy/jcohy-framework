package com.jcohy.framework.starter.sms.ali;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/15/22:10:40
 * @since 2022.0.1
 */
public enum ActionType {

    /**
     * api 操作的动作。根据不同的动作构建不同的 {@link AliSmsRequest}.
     */
    ADD_TEMPLATE("AddSmsTemplate"), QUERY_DETAIL("QuerySendDetails"), SEND_SMS("SendSms"), SEND_BATCH_SMS(
            "SendBatchSms");

    /**
     * action.
     */
    private final String action;

    ActionType(String action) {
        this.action = action;
    }

    public String getAction() {
        return this.action;
    }

}
