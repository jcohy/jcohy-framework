package com.jcohy.framework.configuration.processor;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import com.jcohy.framework.configuration.processor.spi.StartupServiceTestOne;
import com.jcohy.framework.configuration.processor.spi.StartupServiceTestTwo;
import com.jcohy.framework.configuration.processor.spi.TestSagaSpiServiceProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * 描述: 注解处理器.
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2/14/22:18:23
 * @since 2022.0.1
 */
class SpiServiceProcessorTest {

    @TempDir
    File tempDir;

    private TestCompiler compiler;

    @BeforeEach
    void createCompiler() throws IOException {
        this.compiler = new TestCompiler(new File("src/test/resources"));
    }

    @Test
    void annotatedClass() throws IOException {
        Properties properties = compile(StartupServiceTestOne.class, StartupServiceTestTwo.class);
        System.out.println(properties);
        assertThat(properties).hasSize(7);
    }

    private WebTestClient.ListBodySpec<Object> assertThat(Properties properties) {
        return null;
    }

    private Properties compile(Class<?>... types) throws IOException {
        return process(types).getWrittenProperties();
    }

    private TestSagaSpiServiceProcessor process(Class<?>... types) {
        TestSagaSpiServiceProcessor processor = new TestSagaSpiServiceProcessor(this.compiler.getOutputLocation());
        this.compiler.getTask(types).call(processor);
        return processor;
    }

}

