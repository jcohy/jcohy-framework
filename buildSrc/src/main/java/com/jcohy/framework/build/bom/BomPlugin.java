//package com.jcohy.framework.build.bom;
//
//import com.jcohy.framework.build.DeployedPlugin;
//import org.gradle.api.Plugin;
//import org.gradle.api.Project;
//import org.gradle.api.plugins.PluginContainer;
//
///**
// * Copyright: Copyright (c) 2020 <a href="http://www.jcohy.com" target="_blank">jcohy.com</a>
// *
// * <p> Description: 定义 BOM 的 {@link Plugin}，将依赖添加到 {@code api} 配置。
// * 导入 boms 添加 {@code api} platform 强依赖
// *
// * @author jiac
// * @version 1.0.0 2020/12/17:15:55
// * @since 1.0.0
// */
//public class BomPlugin implements Plugin<Project> {
//
//	@Override
//	public void apply(Project project) {
//		System.out.println("BomPlugin");
//		PluginContainer plugins = project.getPlugins();
//		plugins.apply(DeployedPlugin.class);
//	}
//}
