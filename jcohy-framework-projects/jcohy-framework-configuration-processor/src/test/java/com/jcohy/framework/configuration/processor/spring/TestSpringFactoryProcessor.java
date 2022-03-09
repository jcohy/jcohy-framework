package com.jcohy.framework.configuration.processor.spring;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.processing.SupportedAnnotationTypes;

import com.jcohy.framework.configuration.processor.SpringFactoriesProcessor;
import com.jcohy.framework.configuration.processor.utils.Constants;

/**
 * 描述: .
 *
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2/15/22:10:51
 * @since 2022.0.1
 */
@SupportedAnnotationTypes({ "*" })
public class TestSpringFactoryProcessor extends SpringFactoriesProcessor {

    private final File outputLocation;

    public TestSpringFactoryProcessor(File outputLocation) {
        this.outputLocation = outputLocation;
    }

    public Properties getWrittenProperties() throws IOException {
        File file = getWrittenFile();
        if (!file.exists()) {
            return null;
        }
        try (FileInputStream inputStream = new FileInputStream(file)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        }
    }

    public File getWrittenFile() {
        return new File(this.outputLocation, Constants.SPRING_FACTORY_RESOURCE_LOCATION);
    }

}
