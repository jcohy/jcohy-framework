package com.jcohy.framework.build;

import java.util.Collections;

import com.jcohy.convention.JcohyVersion;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2022/2/7:16:12
 * @since 2022.0.1
 */
public class AutoConfigurationPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getPlugins().withType(JavaPlugin.class, javaPlugin -> {
            project.getDependencies().add(JavaPlugin.ANNOTATION_PROCESSOR_CONFIGURATION_NAME, "org.springframework.boot:spring-boot-autoconfigure-processor:" + JcohyVersion.getSpringBootVersion());
            project.getDependencies().add(JavaPlugin.ANNOTATION_PROCESSOR_CONFIGURATION_NAME, "org.springframework.boot:spring-boot-configuration-processor:" + JcohyVersion.getSpringBootVersion());
            project.getDependencies().add(JavaPlugin.ANNOTATION_PROCESSOR_CONFIGURATION_NAME, project.getDependencies().project(Collections.singletonMap("path",
                    ":jcohy-framework-projects:jcohy-framework-configuration-processor")));
        });
    }
}
