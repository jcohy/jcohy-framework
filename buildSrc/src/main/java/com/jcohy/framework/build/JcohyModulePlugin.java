package com.jcohy.framework.build;

import java.util.Collections;

import com.jcohy.convention.conventions.ConventionsPlugin;
import com.jcohy.convention.deployed.DeployedPlugin;
import com.jcohy.convention.optional.OptionalDependenciesPlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.plugins.JavaLibraryPlugin;
import org.gradle.api.plugins.PluginContainer;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2022/2/7:16:15
 * @since 2022.0.1
 */
public class JcohyModulePlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        PluginContainer plugins = project.getPlugins();
        plugins.apply(JavaLibraryPlugin.class);
        plugins.apply(ConventionsPlugin.class);
        plugins.apply(DeployedPlugin.class);
        plugins.apply(OptionalDependenciesPlugin.class);
        configureDependencyManagement(project);
    }

    private void configureDependencyManagement(Project project) {
        Dependency parent = project.getDependencies().enforcedPlatform(project.getDependencies()
                .project(Collections.singletonMap("path", ":jcohy-framework-projects:jcohy-framework-dependencies")));
        project.getConfigurations().getByName("dependencyManagement", (dependencyManagement) -> {
            dependencyManagement.getDependencies().add(parent);
        });
    }
}
