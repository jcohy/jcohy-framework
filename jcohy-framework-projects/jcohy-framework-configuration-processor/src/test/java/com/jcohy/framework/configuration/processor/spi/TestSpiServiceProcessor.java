package com.jcohy.framework.configuration.processor.spi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.annotation.processing.SupportedAnnotationTypes;

import org.springframework.util.StringUtils;

import com.jcohy.framework.configuration.processor.SpiServiceProcessor;
import com.jcohy.framework.configuration.processor.utils.Constants;

/**
 * 描述: 注解处理器.
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2/14/22:18:21
 * @since 2022.0.1
 */
@SupportedAnnotationTypes({ "com.jcohy.framework.configuration.processor.annotations.JcohySpiService" })
public class TestSpiServiceProcessor extends SpiServiceProcessor {

    private final File outputLocation;

    public TestSpiServiceProcessor(File outputLocation) {
        this.outputLocation = outputLocation;
    }

    public List<String> getWrittenProperties() throws IOException {
        File file = getWrittenFile();
        if (!file.exists()) {
            return null;
        }

		List<String> result;
		try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
			result = reader.lines().filter(StringUtils::hasText).collect(Collectors.toList());
		}
        return result;
    }

    public File getWrittenFile() {
        return new File(this.outputLocation, Constants.SERVICE_RESOURCE_LOCATION + "com.jcohy.framework.configuration.processor.spi.StartupService");
    }

}
