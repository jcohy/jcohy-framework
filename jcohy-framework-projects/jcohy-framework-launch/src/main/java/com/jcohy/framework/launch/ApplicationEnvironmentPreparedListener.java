package com.jcohy.framework.launch;

import java.util.Properties;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StringUtils;

/**
 * 描述: 此事件是在 Environment 被上下文使用,但是在上下文创建之前发布..
 * <p>
 *     Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 * @author jiac
 * @version 2022.0.1 3/4/22:10:45
 * @since 2022.0.1
 */
public class ApplicationEnvironmentPreparedListener
        implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        // 设置默认的 profile，并设置 jcohy.env 属性
        String activeProfiles = StringUtils.arrayToCommaDelimitedString(event.getEnvironment().getActiveProfiles());
        String defaultProfiles = StringUtils.arrayToCommaDelimitedString(event.getEnvironment().getDefaultProfiles());
        Properties defaultProperties = System.getProperties();
        defaultProperties.setProperty("jcohy.env",
                StringUtils.hasText(activeProfiles) ? activeProfiles : defaultProfiles);
        event.getSpringApplication().setDefaultProperties(defaultProperties);
    }

}
