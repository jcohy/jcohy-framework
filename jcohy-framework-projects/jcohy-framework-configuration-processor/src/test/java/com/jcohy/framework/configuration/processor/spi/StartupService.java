package com.jcohy.framework.configuration.processor.spi;

/**
 * 描述: .
 * <p>
 *     Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 * @author jiac
 * @version 2022.0.1 2022/2/14:18:19
 * @since 2022.0.1
 */
public interface StartupService {

    /**
     * 启动时加载 spi.
     */
    void startup();

}
