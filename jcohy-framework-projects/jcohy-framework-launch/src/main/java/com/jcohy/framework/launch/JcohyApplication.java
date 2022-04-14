package com.jcohy.framework.launch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.jcohy.framework.commons.JcohyFrameworkVersion;
import com.jcohy.framework.commons.env.Profiles;

/**
 * 描述: 自定义启动类.
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/4/22:10:40
 * @since 2022.0.1
 */
public class JcohyApplication {

    public JcohyApplication() {
    }

    public static ConfigurableApplicationContext run(String applicationName, Class<?> source, String... args) {
        return build(applicationName, source).run(args);
    }

    // public static SpringApplication build(){}
    public static SpringApplication build(String applicationName, Class<?> source, String... args) {
        Assert.hasText(applicationName, "[applicationName] 服务名不能为空");
        SpringApplicationBuilder builder = new SpringApplicationBuilder()
                // .main(source)
                .sources(source).applicationStartup(new BufferingApplicationStartup(2048))
                .listeners(new ApplicationEnvironmentPreparedListener()).properties(addProperties(applicationName));
        // 获取环境变量
        String profile = configurationProfile(args);
        builder.profiles(profile);

        String startJarPath = JcohyApplication.class.getResource("/").getPath().split("!")[0];
        System.out.printf("----启动中，jar地址:[%s]----%n", startJarPath);

        List<LauncherService> services = new ArrayList<>();
        ServiceLoader.load(LauncherService.class).forEach(services::add);
        services.stream().sorted(Comparator.comparing(LauncherService::getOrder)).collect(Collectors.toList()).forEach(
                (launcherService) -> launcherService.launcher(builder, applicationName, profile, isLocalDev(profile)));

        return builder.build();
    }

    /**
     * 配置 Profile.
     * @param args args
     * @return /
     */
    private static String configurationProfile(String... args) {
        // 读取环境变量，使用spring boot的规则
        ConfigurableEnvironment environment = new StandardEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addFirst(new SimpleCommandLinePropertySource(args));
        propertySources.addLast(new MapPropertySource(StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME,
                environment.getSystemProperties()));
        propertySources.addLast(new SystemEnvironmentPropertySource(
                StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, environment.getSystemEnvironment()));
        // 获取配置的环境变量
        String[] activeProfiles = environment.getActiveProfiles();
        // 判断环境:dev、test、prod
        List<String> profiles = Arrays.asList(activeProfiles);
        // 预设的环境
        List<String> presetProfiles = new ArrayList<>(
                Arrays.asList(Profiles.DEV_CODE, Profiles.TEST_CODE, Profiles.PROD_CODE));
        // 交集
        presetProfiles.retainAll(profiles);
        // 当前使用
        List<String> activeProfileList = new ArrayList<>(profiles);
        Function<Object[], String> joinFun = StringUtils::arrayToCommaDelimitedString;
        String profile;
        if (activeProfileList.isEmpty()) {
            // 默认dev开发
            profile = Profiles.DEV_CODE;
            activeProfileList.add(profile);
        }
        else if (activeProfileList.size() == 1) {
            profile = activeProfileList.get(0);
        }
        else {
            // 同时存在dev、test、prod环境时
            throw new RuntimeException("同时存在环境变量:[" + StringUtils.arrayToCommaDelimitedString(activeProfiles) + "]");
        }
        return profile;
    }

    /**
     * 添加默认配置或属性.
     * @param applicationName 应用名
     * @return /
     */
    private static Properties addProperties(String applicationName) {
        Properties defaultProperties = new Properties();
        defaultProperties.setProperty("spring.application.name", applicationName);
        defaultProperties.setProperty("jcohy.version", JcohyFrameworkVersion.getVersion());
        defaultProperties.setProperty("spring.main.allow-bean-definition-overriding", "true");
        defaultProperties.setProperty("server.servlet.context-path", "/" + applicationName);
        defaultProperties.setProperty("copyright", "https://www.jcohy.com");
        return defaultProperties;
    }

    /**
     * 判断是否为本地测试或开发环境.
     * @param profile profile.
     * @return boolean
     */
    private static boolean isLocalDev(String profile) {
        if (!StringUtils.hasText(profile)) {
            return true;
        }
        return profile.equals(Profiles.DEV_CODE) || profile.equals(Profiles.TEST_CODE);
    }

}
