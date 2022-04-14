package com.jcohy.framework.starter.sms;

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
public enum SmsStatusEnum {

    /**
     * 关闭.
     */
    DISABLE(1),
    /**
     * 启用.
     */
    ENABLE(2);

    /**
     * 类型编号.
     */
    final int num;

    SmsStatusEnum(int num) {
        this.num = num;
    }

    public int getNum() {
        return this.num;
    }

}
