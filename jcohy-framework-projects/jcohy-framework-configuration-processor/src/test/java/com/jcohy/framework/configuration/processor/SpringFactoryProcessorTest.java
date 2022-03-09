package com.jcohy.framework.configuration.processor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.tools.JavaCompiler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.springframework.util.StringUtils;

import com.jcohy.framework.configuration.processor.spring.TestApplicationContextInitializer;
import com.jcohy.framework.configuration.processor.spring.TestApplicationListener;
import com.jcohy.framework.configuration.processor.spring.TestNoApplicationListener;
import com.jcohy.framework.configuration.processor.spring.TestSpringFactoryProcessor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 描述: Wrapper to make the {@link JavaCompiler} easier to use in tests.
 *
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2/14/22:18:23
 * @since 2022.0.1
 */
class SpringFactoryProcessorTest {

    @TempDir
    File tempDir;

    private TestCompiler compiler;

    private static Map<String, List<String>> loadSpringFactories(Properties properties) {
        Map<String, List<String>> result = new HashMap<>();

        if (properties == null) {
            return result;
        }

        for (Map.Entry<?, ?> entry : properties.entrySet()) {
            String factoryTypeName = ((String) entry.getKey()).trim();
            String[] factoryImplementationNames = StringUtils
                    .commaDelimitedListToStringArray((String) entry.getValue());
            for (String factoryImplementationName : factoryImplementationNames) {
                result.computeIfAbsent(factoryTypeName, (key) -> new ArrayList<>())
                        .add(factoryImplementationName.trim());
            }
        }
        return result;
    }

    @BeforeEach
    void createCompiler() throws IOException {
        this.compiler = new TestCompiler(new File("src/test/resources"));
    }

    /**
     * 预期结果：没有实现 ApplicationListener 接口的类不写入到 spring.factories 中.
     * TestApplicationListener.class,TestApplicationContextInitializer.class,
     */
    @Test
    void annotatedClass() throws IOException {
        Properties properties = compile(TestApplicationListener.class, TestApplicationContextInitializer.class,
                TestNoApplicationListener.class);
        Map<String, List<String>> stringListMap = loadSpringFactories(properties);
        assertThat(properties).hasSize(2);
    }

    private Properties compile(Class<?>... types) throws IOException {
        return process(types).getWrittenProperties();
    }

    private TestSpringFactoryProcessor process(Class<?>... types) {
        TestSpringFactoryProcessor processor = new TestSpringFactoryProcessor(this.compiler.getOutputLocation());
        this.compiler.getTask(types).call(processor);
        return processor;
    }

}
