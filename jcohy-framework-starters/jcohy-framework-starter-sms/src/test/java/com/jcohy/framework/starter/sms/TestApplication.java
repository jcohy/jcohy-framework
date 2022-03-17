package com.jcohy.framework.starter.sms;

import com.jcohy.framework.starter.sms.request.SmsSignRequest;
import com.jcohy.framework.utils.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/15/22:11:16
 * @since 2022.0.1
 */
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }


    @Test
    void test() {

        assertThat(SmsAction.ADD_SMS_SIGN.getAction().contains("sign")).isFalse();

        assertThat(StringUtils.containsIgnoreCase(SmsAction.ADD_SMS_SIGN.getAction(),"sign")).isTrue();
    }
}

