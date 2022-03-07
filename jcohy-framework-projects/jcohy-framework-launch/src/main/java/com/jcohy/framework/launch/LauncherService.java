package com.jcohy.framework.launch;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.Ordered;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/4/22:10:59
 * @since 2022.0.1
 */
public interface LauncherService extends Ordered, Comparable<LauncherService> {

    /**
     * 启动时加载 spi.
     * @param builder builder
     * @param applicationName 应用名
     * @param profile 环境
     * @param isLocalDev 是否是测试环境
     */
    void launcher(SpringApplicationBuilder builder, String applicationName, String profile, boolean isLocalDev);

    /**
     * 获取排列顺序.
     * @return order
     */
    @Override
    default int getOrder() {
        return 0;
    }

    /**
     * 对比排序.
     * @param launcherService startupService
     * @return compare
     */
    @Override
    default int compareTo(LauncherService launcherService) {
        return Integer.compare(this.getOrder(), launcherService.getOrder());
    }

}
