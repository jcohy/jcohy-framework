package com.jcohy.framework.starter.sms.tencent;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.util.Assert;

import com.jcohy.framework.starter.sms.SmsRequest;
import com.jcohy.framework.utils.StringUtils;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/15/22:10:43
 * @since 2022.0.1
 */
public class TencentSmsRequest extends SmsRequest {

    // 手机号
    private final String[] phones;

    // 签名
    private final String[] signName;

    // 模板id
    private final String templateId;

    // 模板参数
    private final String[] templateParams;

    public TencentSmsRequest(Builder builder) {
        this.phones = Objects.requireNonNull(builder.phones);
        this.templateParams = Objects.requireNonNull(builder.templateParams);
        this.templateId = Objects.requireNonNull(builder.templateId);
        this.signName = builder.signName;
        super.setValidate(builder.validate);
        super.setStore(builder.store);
    }

    /**
     * 创建 builder.
     * @return builder
     */
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String[] getPhones() {
        return this.phones;
    }

    public String[] getSignName() {
        return this.signName;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public String[] getTemplateParams() {
        return this.templateParams;
    }

    @Override
    public String toString() {
        return "TenSmsRequest{" + ", phones=" + Arrays.toString(this.phones) + ", signName='"
                + Arrays.toString(this.signName) + '\'' + ", templateId='" + this.templateId + '\''
                + ", templateParams=" + Arrays.toString(this.templateParams) + '}';
    }

    public static final class Builder {

        private final Map<String, Map<String, String>> store = new HashMap<>();

        // 手机号.
        private String[] phones;

        // 签名.
        private String[] signName;

        // 模板id.
        private String templateId;

        // 模板参数.
        private String[] templateParams;

        // 是否需要验证.
        private boolean validate;

        /**
         * 手机号集合.
         * @param phones 手机号
         * @return {@link TencentSmsRequest.Builder}
         */
        public TencentSmsRequest.Builder phones(Collection<String> phones) {
            Assert.notNull(phones, "手机号不能为空");
            this.phones = phones.toArray(new String[0]);
            return this;
        }

        /**
         * 不定项参数.
         * @param phones 手机号
         * @return {@link TencentSmsRequest.Builder}
         */
        public TencentSmsRequest.Builder phones(String... phones) {
            Assert.notNull(phones, "手机号不能为空");
            this.phones = phones;
            return this;
        }

        /**
         * 设置手机号。可以是单个手机号，多个的话需要用逗号隔开.
         * @param phones 电话号码
         * @return {@link TencentSmsRequest.Builder}
         */
        public TencentSmsRequest.Builder phones(String phones) {
            Assert.notNull(phones, "手机号不能为空");
            this.phones = StringUtils.commaDelimitedListToStringArray(phones);
            return this;
        }

        /**
         * 签名,可以是单个签名，多个的话需要用逗号隔开.
         * @param signName 签名
         * @return {@link TencentSmsRequest.Builder}
         */
        public TencentSmsRequest.Builder signName(String signName) {
            Assert.notNull(signName, "签名不能为空");
            this.signName = StringUtils.commaDelimitedListToStringArray(signName);
            return this;
        }

        /**
         * 不定项参数.
         * @param signName 签名
         * @return {@link TencentSmsRequest.Builder}
         */
        public TencentSmsRequest.Builder signName(String... signName) {
            Assert.notNull(signName, "签名不能为空");
            this.signName = signName;
            return this;
        }

        /**
         * 签名集合.
         * @param signName 签名
         * @return {@link TencentSmsRequest.Builder}
         */
        public TencentSmsRequest.Builder signName(Collection<String> signName) {
            Assert.notNull(signName, "签名不能为空");
            this.signName = signName.toArray(new String[0]);
            return this;
        }

        /**
         * 设置是否需要验证.
         * @param validate 是否需要验证
         * @return {@link TencentSmsRequest.Builder}
         */
        public TencentSmsRequest.Builder validate(boolean validate) {
            this.validate = validate;
            return this;
        }

        /**
         * 设置模板Id.
         * @param templateId 模板Id
         * @return {@link TencentSmsRequest.Builder}
         */
        public TencentSmsRequest.Builder templateId(String templateId) {
            Assert.notNull(templateId, "模板Id不能为空");
            this.templateId = templateId;
            return this;
        }

        /**
         * 设置模板变量参数(验证码).
         * @param templateParams 模板变量参数
         * @return {@link TencentSmsRequest.Builder}
         */
        public TencentSmsRequest.Builder templateParams(Collection<String> templateParams) {
            Assert.notNull(templateParams, "模板参数不能为空");
            this.templateParams = templateParams.toArray(new String[0]);
            return this;
        }

        /**
         * 不定项模板变量参数(验证码).
         * @param templateParams 模板变量参数
         * @return {@link TencentSmsRequest.Builder}
         */
        public TencentSmsRequest.Builder templateParams(String... templateParams) {
            Assert.notNull(templateParams, "模板参数不能为空");
            this.templateParams = templateParams;
            return this;
        }

        /**
         * 设置模板变量参数(验证码)。可以是单个参数，多个的话需要用逗号隔开.
         * @param templateParams 模板变量参数
         * @return {@link TencentSmsRequest.Builder}
         */
        public TencentSmsRequest.Builder templateParams(String templateParams) {
            Assert.notNull(templateParams, "模板参数不能为空");
            this.templateParams = StringUtils.commaDelimitedListToStringArray(templateParams);
            return this;
        }

        /**
         * 创建 {@link TencentSmsRequest}.
         * @return {@link TencentSmsRequest.Builder}
         */
        public TencentSmsRequest build() {
            return new TencentSmsRequest(process());
        }

        private Builder process() {
            int phoneLength = this.phones.length;
            int templateLength = this.templateParams.length;
            Map<String, String> map = new HashMap<>();
            if (phoneLength > 1) {
                for (String phone : this.phones) {
                    for (String template : this.templateParams) {
                        for (int i = 1; i <= phoneLength; i++) {
                            map.put(String.valueOf(i), template);
                        }
                    }
                    this.store.put(phone, map);
                }
            }
            else {
                for (String template : this.templateParams) {
                    for (int i = 1; i <= templateLength; i++) {
                        map.put(String.valueOf(i), template);
                    }
                }
                this.store.put(this.phones[0], map);
            }
            return this;
        }

    }

}
