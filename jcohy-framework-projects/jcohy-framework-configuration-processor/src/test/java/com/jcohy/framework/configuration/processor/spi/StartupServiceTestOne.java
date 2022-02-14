package com.jcohy.framework.configuration.processor.spi;

import com.jcohy.framework.configuration.processor.annotations.JcohySpiService;

/**
 * 描述: 注解处理器.
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2022/2/14:18:20
 * @since 2022.0.1
 */
@JcohySpiService(value = StartupService.class, name = "StartupServiceTestOne")
public class StartupServiceTestOne implements StartupService {

    @Override
    public void startup() {

    }

}
