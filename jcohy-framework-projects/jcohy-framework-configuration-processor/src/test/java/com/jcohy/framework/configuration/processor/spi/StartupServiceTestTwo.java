package com.jcohy.framework.configuration.processor.spi;

import com.jcohy.framework.configuration.processor.annotations.JcohySpiService;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 2022/2/14:18:20
 * @since 2022.0.1
 */
@JcohySpiService(value = StartupService.class, name = "StartupServiceTestTwo")
public class StartupServiceTestTwo implements StartupService {

    @Override
    public void startup() {

    }

}
