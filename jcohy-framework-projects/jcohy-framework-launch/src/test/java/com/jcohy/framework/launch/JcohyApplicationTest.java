package com.jcohy.framework.launch;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 描述: .
 *
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/4/22:11:36
 * @since 2022.0.1
 */

@ExtendWith(OutputCaptureExtension.class)
class JcohyApplicationTest {

    private ConfigurableApplicationContext context;

    private Environment getEnvironment() {
        if (this.context != null) {
            return this.context.getEnvironment();
        }
        throw new IllegalStateException("Could not obtain Environment");
    }

    @Test
    void sourceMustNotNull() {
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> JcohyApplication.run("Jcohy", null))
                .withMessage("Source must not be null");
    }

    @Test
    void applicationMustNameNotNull() {
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> JcohyApplication.run(null, JcohyApplication.class))
                .withMessage("[applicationName] 服务名不能为空");
    }

    @Test
    void customBanner(CapturedOutput output) {
        SpringApplication application = JcohyApplication.build("Jcohy", ExampleConfig.class);
        MockResourceLoader resourceLoader = new MockResourceLoader();
        resourceLoader.addResource("banner.txt", "jcohy.txt");
        application.setWebApplicationType(WebApplicationType.NONE);
        application.setResourceLoader(resourceLoader);
        application.run();
        assertThat(output).contains("----启动中，jar地址:");
        assertThat(output).contains(" Copyright ©2020 https://www.jcohy.com");
        assertThat(output).contains(" :: Application Profile			::  dev");
        assertThat(output).contains(" :: Application Name			::  Jcohy");
        assertThat(output).contains(" :: Running Jcohy Framework		::  2022.0.1");
        assertThat(output).contains(" :: Running SpringBoot Version	::  2.4.6");
    }

    @Test
    void addProfile() {
        SpringApplication application = JcohyApplication.build("Jcohy", ExampleConfig.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.setAdditionalProfiles("foo");
        ConfigurableEnvironment environment = new StandardEnvironment();
        application.setEnvironment(environment);
        this.context = application.run();
        assertThat(environment.acceptsProfiles(Profiles.of("foo"))).isTrue();
    }

    @Test
    void addProfilesOrder() {
        SpringApplication application = JcohyApplication.build("Jcohy", ExampleConfig.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.setAdditionalProfiles("foo");
        ConfigurableEnvironment environment = new StandardEnvironment();
        application.setEnvironment(environment);
        this.context = application.run("--spring.profiles.active=bar,spam");
        // 从 SpringBoot 2.4 后，addition 始终是第一个。
        assertThat(environment.getActiveProfiles()).containsExactly("foo", "bar", "spam");
    }

    @Configuration(proxyBeanMethods = false)
    static class ExampleConfig {

        @Bean
        String someBean() {
            return "test";
        }

    }

    static class MockResourceLoader implements ResourceLoader {

        private final Map<String, Resource> resources = new HashMap<>();

        void addResource(String source, String path) {
            this.resources.put(source, new ClassPathResource(path, getClass()));
        }

        @Override
        public Resource getResource(String path) {
            Resource resource = this.resources.get(path);
            return (resource != null) ? resource : new ClassPathResource("doesnotexist");
        }

        @Override
        public ClassLoader getClassLoader() {
            return getClass().getClassLoader();
        }

    }

}
