package com.jcohy.framework.launch.props;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.io.support.PropertySourceFactory;

/**
 * 描述: 解析 Yml 配置文件.
 *
 * <p>
 * 因为 Spring 默认的 {@link PropertySource} 注解仅支持解析 .xml 和 .properties 文件。虽然可以通过指定的
 * {@link PropertySourceFactory} 的自定义实现来达成次目的。但每次都需要配置 {@link PropertySource} 的 factory
 * 属性。为了简化起见。将 yml 解析封装在次注解中。
 *
 * <pre class="code">
 * &#064;Configuration
 * &#064;YmlPropertySource("classpath:/com/myco/app.properties")
 * public class AppConfig {
 *
 *     &#064;Autowired
 *     Environment env;
 *
 *     &#064;Bean
 *     public TestBean testBean() {
 *         TestBean testBean = new TestBean();
 *         testBean.setName(env.getProperty("testbean.name"));
 *         return testBean;
 *     }
 * }
 * </pre>
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 * @author jiac
 * @version 2022.0.1 3/8/22:10:16
 * @since 2022.0.1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface YmlPropertySource {

    /**
     * 指示要加载的属性文件的资源位置. {@code "classpath:/com/example/app.yml"}.
     * @return location(s)
     */
    String value();

    /**
     * 加载 app-{activeProfile}.yml.
     * @return {boolean}
     */
    boolean loadActiveProfile() default true;

    /**
     * 排序.
     * @return order
     */
    int order() default Ordered.LOWEST_PRECEDENCE;

}
