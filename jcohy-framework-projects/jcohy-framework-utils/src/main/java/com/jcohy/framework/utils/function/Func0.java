package com.jcohy.framework.utils.function;

import java.io.Serializable;

/**
 * 描述: 无参数的函数对象. 接口灵感来自于 <a href="http://actframework.org/">ActFramework</a><br>
 * 一个函数接口代表一个一个函数，用于包装一个函数为对象<br>
 * 在 JDK8 之前，Java 的函数并不能作为参数传递，也不能作为返回值存在，此接口用于将一个函数包装成为一个对象，从而传递对象
 * <p>
 *     Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 * @param <R> 返回值类型
 * @author jiac
 * @version 2022.0.1 3/3/22:17:41
 * @since 2022.0.1
 */
@FunctionalInterface
public interface Func0<R> extends Serializable {

    /**
     * 执行函数.
     * @return 函数执行结果
     * @throws Exception 自定义异常
     */
    R call() throws Exception;

    /**
     * 执行函数，异常包装为 RuntimeException.
     * @return 函数执行结果
     * @since 5.3.6
     */
    default R callWithRuntimeException() {
        try {
            return call();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
