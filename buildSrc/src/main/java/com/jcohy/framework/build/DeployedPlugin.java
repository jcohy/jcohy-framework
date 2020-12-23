//package com.jcohy.framework.build;
//
//import org.gradle.api.Plugin;
//import org.gradle.api.Project;
//import org.gradle.api.plugins.PluginContainer;
//import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;
//
///**
// * Copyright: Copyright (c) 2020 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
// *
// * <p> Description: 部署项目的插件
// *
// * @author jiac
// * @version 1.0.0 2020/12/17:16:01
// * @since 1.0.0
// */
//public class DeployedPlugin implements Plugin<Project> {
//
//	/**
//	 * 生成部署的 pom 文件的任务的名称。
//	 */
//	public static final String GENERATE_POM_TASK_NAME = "generatePomFileForMavenPublication";
//
//	@Override
//	public void apply(Project project) {
//		PluginContainer plugins = project.getPlugins();
//		plugins.apply(MavenPublishPlugin.class);
//		plugins.apply(MavenRepositoryPlugin.class);
//	}
//}
