package com.jcohy.framework.launch.props;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/8/22:11:24
 * @since 2022.0.1
 */
public class YmlPropertySourcePostProcessor  implements BeanFactoryPostProcessor, InitializingBean, Ordered  {

	private static final Logger logger = LoggerFactory.getLogger(YmlPropertySourcePostProcessor.class);

	private final ResourceLoader resourceLoader;

	private final List<PropertySourceLoader> propertySourceLoaders;

	public YmlPropertySourcePostProcessor(ResourceLoader resourceLoader, List<PropertySourceLoader> propertySourceLoaders) {
		this.resourceLoader = resourceLoader;
		this.propertySourceLoaders = SpringFactoriesLoader.loadFactories(PropertySourceLoader.class,
				getClass().getClassLoader());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("YmlPropertySourcePostProcessor init.");
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Map<String, Object> beans = beanFactory.getBeansWithAnnotation(YmlPropertySource.class);
		Set<Entry<String, Object>> beanEntries = beans.entrySet();
		if (beanEntries.isEmpty()) {
			logger.warn("Not found @YmlPropertySource on spring bean class.");
			return;
		}
		// 组装资源
		List<PropertyFile> propertyFileList = new ArrayList<>();
		for (Map.Entry<String, Object> entry : beanEntries) {
			Class<?> beanClass = ClassUtils.getUserClass(entry.getValue());
			YmlPropertySource propertySource = AnnotationUtils.getAnnotation(beanClass, YmlPropertySource.class);
			if (propertySource == null) {
				continue;
			}
			int order = propertySource.order();
			boolean loadActiveProfile = propertySource.loadActiveProfile();
			String location = propertySource.value();
			propertyFileList.add(new PropertyFile(order, location, loadActiveProfile));
		}

		Map<String, PropertySourceLoader> loaderMap = new HashMap<>(16);
		for (PropertySourceLoader loader : this.propertySourceLoaders) {
			String[] loaderExtensions = loader.getFileExtensions();
			for (String extension : loaderExtensions) {
				loaderMap.put(extension, loader);
			}
		}
		// 去重，排序
		List<PropertyFile> sortedPropertyList = propertyFileList.stream().distinct().sorted()
				.collect(Collectors.toList());
		ConfigurableEnvironment environment = beanFactory.getBean(ConfigurableEnvironment.class);
		MutablePropertySources propertySources = environment.getPropertySources();

		// 只支持 activeProfiles，没有必要支持 spring.profiles.include。
		String[] activeProfiles = environment.getActiveProfiles();
		ArrayList<PropertySource> propertySourceList = new ArrayList<>();
		for (String profile : activeProfiles) {
			for (PropertyFile propertyFile : sortedPropertyList) {
				// 不加载 ActiveProfile 的配置文件
				if (!propertyFile.loadActiveProfile) {
					continue;
				}
				String extension = propertyFile.getExtension();
				PropertySourceLoader loader = loaderMap.get(extension);
				if (loader == null) {
					throw new IllegalArgumentException(
							"Can't find PropertySourceLoader for PropertySource extension:" + extension);
				}
				String location = propertyFile.getLocation();
				String filePath = StringUtils.stripFilenameExtension(location);
				String profiledLocation = filePath + "-" + profile + "." + extension;
				Resource resource = this.resourceLoader.getResource(profiledLocation);
				loadPropertySource(profiledLocation, resource, loader, propertySourceList);
			}
		}

		// 本身的 Resource
		for (PropertyFile propertyFile : sortedPropertyList) {
			String extension = propertyFile.getExtension();
			PropertySourceLoader loader = loaderMap.get(extension);
			String location = propertyFile.getLocation();
			Resource resource = this.resourceLoader.getResource(location);
			loadPropertySource(location, resource, loader, propertySourceList);
		}
		// 转存
		for (PropertySource propertySource : propertySourceList) {
			propertySources.addLast(propertySource);
		}

	}

	private static void loadPropertySource(String location, Resource resource, PropertySourceLoader loader,
			List<PropertySource> sourceList) {
		if (resource.exists()) {
			String name = "sagaPropertySource: [" + location + "]";
			try {
				sourceList.addAll(loader.load(name, resource));
			}
			catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	@Override
	public int getOrder() {
		return  Ordered.LOWEST_PRECEDENCE;
	}

	public static class PropertyFile implements Comparable<PropertyFile> {

		private final int order;

		private final String location;

		private final String extension;

		private final boolean loadActiveProfile;

		PropertyFile(int order, String location, boolean loadActiveProfile) {
			this.order = order;
			this.location = location;
			this.loadActiveProfile = loadActiveProfile;
			this.extension = Objects.requireNonNull(StringUtils.getFilenameExtension(location));
		}

		@Override
		public int compareTo(PropertyFile other) {
			return Integer.compare(this.order, other.order);
		}

		public int getOrder() {
			return this.order;
		}

		public String getLocation() {
			return this.location;
		}

		public String getExtension() {
			return this.extension;
		}

		public boolean isLoadActiveProfile() {
			return this.loadActiveProfile;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			PropertyFile that = (PropertyFile) o;
			return getOrder() == that.getOrder() && isLoadActiveProfile() == that.isLoadActiveProfile()
					&& Objects.equals(getLocation(), that.getLocation())
					&& Objects.equals(getExtension(), that.getExtension());
		}

		@Override
		public int hashCode() {
			return Objects.hash(getOrder(), getLocation(), getExtension(), isLoadActiveProfile());
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder("PropertyFile{");
			sb.append("order=").append(this.order);
			sb.append(", location='").append(this.location).append('\'');
			sb.append(", extension='").append(this.extension).append('\'');
			sb.append(", loadActiveProfile=").append(this.loadActiveProfile);
			sb.append('}');
			return sb.toString();
		}

	}
}
