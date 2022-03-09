package com.jcohy.framework.utils.exceptions;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * 描述: .
 *
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:15:55
 * @since 2022.0.1
 */
public final class Exceptions {

    private Exceptions() {
    }

    /**
     * 将 checkedException 转换为 uncheckedException.
     * @param ex throwable
     * @return {runtimeException}
     */
    public static RuntimeException unchecked(Throwable ex) {
        if (ex instanceof Error) {
            throw (Error) ex;
        }
        else if (ex instanceof IllegalAccessException || ex instanceof IllegalArgumentException
                || ex instanceof NoSuchMethodException) {
            return new IllegalArgumentException(ex);
        }
        else if (ex instanceof InvocationTargetException) {
            return new RuntimeException(((InvocationTargetException) ex).getTargetException());
        }
        else if (ex instanceof RuntimeException) {
            return (RuntimeException) ex;
        }
        else if (ex instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }
        return Exceptions.runtime(ex);
    }

    /**
     * 不采用 runtimeException 包装，直接抛出，使异常更加精准.
     * @param throwable throwable
     * @param <T> 泛型标记
     * @return throwable
     * @throws T 泛型
     */
    @SuppressWarnings("unchecked")
    private static <T extends Throwable> T runtime(Throwable throwable) throws T {
        throw (T) throwable;
    }

    /**
     * 代理异常解包.
     * @param wrapped 包装过得异常
     * @return 解包后的异常
     */
    public static Throwable unwrap(Throwable wrapped) {
        Throwable unwrapped = wrapped;
        while (true) {
            if (unwrapped instanceof InvocationTargetException) {
                unwrapped = ((InvocationTargetException) unwrapped).getTargetException();
            }
            else if (unwrapped instanceof UndeclaredThrowableException) {
                unwrapped = ((UndeclaredThrowableException) unwrapped).getUndeclaredThrowable();
            }
            else {
                return unwrapped;
            }
        }
    }

    /**
     * 将ErrorStack转化为String.
     * @param ex 异常
     * @return {String}
     */
    public static String getStackTraceAsString(Throwable ex) {
        Writer stringWriter = new CharArrayWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

}
