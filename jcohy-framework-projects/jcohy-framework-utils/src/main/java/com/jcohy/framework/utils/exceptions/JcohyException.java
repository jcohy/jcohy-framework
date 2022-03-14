package com.jcohy.framework.utils.exceptions;

import com.jcohy.framework.commons.JcohyFrameworkVersion;
import com.jcohy.framework.utils.api.ResultStatusCode;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:16:38
 * @since 2022.0.1
 */
public class JcohyException extends RuntimeException {

    private static final long serialVersionUID = JcohyFrameworkVersion.SERIAL_VERSION_UID;

    /**
     * 构造异常.
     * @param message 详细信息
     */
    public JcohyException(String message) {
        super(message);
    }

    /**
     * 构造异常.
     * @param resultStatusCode 详细信息
     */
    public JcohyException(ResultStatusCode resultStatusCode) {
        super(resultStatusCode.getMessage());
    }

    /**
     * 构造异常.
     * @param resultStatusCode resultStatusCode
     * @param cause 异常du堆栈
     */
    public JcohyException(ResultStatusCode resultStatusCode, Throwable cause) {
        super(cause);
    }

}
