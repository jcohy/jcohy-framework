package com.jcohy.framework.launch.props;

import org.junit.jupiter.api.Test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

import com.jcohy.framework.launch.JcohyApplication;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 描述: .
 * <p>
 *     Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 * @author jiac
 * @version 2022.0.1 3/8/22:16:02
 * @since 2022.0.1
 */
// @SpringBootTest(classes = ApplicationWithProfile.class)
public class YmlPropertySourceTest {

    // @Autowired
    // private Environment env;

    private ConfigurableApplicationContext context;

    private Environment getEnvironment() {
        if (this.context != null) {
            return this.context.getEnvironment();
        }
        throw new IllegalStateException("Could not obtain Environment");
    }

    @Test
    void loadProfileWithNoProfile() {
        SpringApplication application = JcohyApplication.build("Jcohy", ApplicationTest.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        ConfigurableEnvironment environment = new StandardEnvironment();
        application.setEnvironment(environment);
        application.run();
        assertThat(environment.getProperty("server.name")).isEqualTo("jcohy-dev");
    }

    @Test
    void loadProfileProd() {
        SpringApplication application = JcohyApplication.build("Jcohy", ApplicationTest.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.setAdditionalProfiles("prod");
        ConfigurableEnvironment environment = new StandardEnvironment();
        application.setEnvironment(environment);
        application.run();
        assertThat(environment.getProperty("server.name")).isEqualTo("jcohy-prod");
    }

}
