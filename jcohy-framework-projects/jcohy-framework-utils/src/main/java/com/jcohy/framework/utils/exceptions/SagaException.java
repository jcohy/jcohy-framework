package com.jcohy.framework.utils.exceptions;

import com.jcohy.framework.commons.JcohyFrameworkVersion;
import com.jcohy.framework.utils.api.ResultStatusCode;

/**
 * 描述: .
 *
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:16:38
 * @since 2022.0.1
 */
public class SagaException extends RuntimeException {

    private static final long serialVersionUID = JcohyFrameworkVersion.SERIAL_VERSION_UID;

    /**
     * 构造异常.
     * @param message 详细信息
     */
    public SagaException(String message) {
        super(message);
    }

    /**
     * 构造异常.
     * @param resultStatusCode 详细信息
     */
    public SagaException(ResultStatusCode resultStatusCode) {
        super(resultStatusCode.getMessage());
    }

    /**
     * 构造异常.
     * @param resultStatusCode resultStatusCode
     * @param cause 异常du堆栈
     */
    public SagaException(ResultStatusCode resultStatusCode, Throwable cause) {
        super(cause);
    }

}
