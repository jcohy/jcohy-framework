package com.jcohy.framework.launch.props;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jcohy.framework.launch.JcohyApplication;

/**
 * 描述: .
 *
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/8/22:16:00
 * @since 2022.0.1
 */
@SpringBootApplication
@YmlPropertySource("classpath:app.yml")
public class ApplicationTest {

    public static void main(String[] args) {
        JcohyApplication.run("jcohy", ApplicationTest.class);
    }

}
