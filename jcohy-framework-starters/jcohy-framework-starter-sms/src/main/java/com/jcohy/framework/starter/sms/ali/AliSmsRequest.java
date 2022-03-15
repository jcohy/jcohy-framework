package com.jcohy.framework.starter.sms.ali;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import com.aliyuncs.http.MethodType;

import org.springframework.util.Assert;

import com.jcohy.framework.starter.sms.SmsException;
import com.jcohy.framework.starter.sms.SmsRequest;
import com.jcohy.framework.utils.JsonUtils;
import com.jcohy.framework.utils.StringUtils;
import com.jcohy.framework.utils.constant.StringPools;

/**
 * 描述: 将系统的请求转换为阿里 api 请求.
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/15/22:10:40
 * @since 2022.0.1
 */
public class AliSmsRequest extends SmsRequest {

    private final String action;

    private final MethodType method;

    private final Map<String, String> queryParams;

    public AliSmsRequest(Builder builder) {
        this.action = Objects.requireNonNull(builder.action);
        this.queryParams = Objects.requireNonNull(builder.queryParams);
        this.method = Objects.requireNonNull(builder.method);
        super.setMessage(builder.message);
        super.setValidate(builder.validate);
        super.setPhones(builder.phones);
        super.setStore(builder.store);
    }

    /**
     * 获取请求动作 {@link ActionType}.
     * @return 请求动作
     */
    public String getAction() {
        return this.action;
    }

    /**
     * 获取请求方法.
     * @return {@link MethodType}
     */
    public MethodType getMethod() {
        return this.method;
    }

    /**
     * 获取请求参数.
     * @return 请求参数
     */
    public Map<String, String> getQueryParams() {
        return this.queryParams;
    }

    /**
     * 根据请求动作 {@link ActionType} 创建 Builder.
     * @param action 请求动作
     * @param method 请求方法
     * @return builder
     */
    public static Builder builder(ActionType action, MethodType method) {
        Assert.notNull(action, "请求动作不能为空");
        Assert.notNull(method, "请求方法不能为空");
        return new Builder(action.getAction(), method);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AliSmsRequest that = (AliSmsRequest) o;
        return Objects.equals(this.action, that.action) && Objects.equals(this.queryParams, that.queryParams);
    }

    @Override
    public int hashCode() {
        int result = (this.action != null) ? this.action.hashCode() : 0;
        result = 31 * result + ((this.queryParams != null) ? this.queryParams.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AliSmsRequest{" + "action='" + this.action + '\'' + ", queryParams=" + this.queryParams + '}';
    }

    public static final class Builder {

        private final String action;

        private final MethodType method;

        private final Map<String, String> queryParams = new HashMap<>();

        private final Map<String, String> templateParams = new HashMap<>();

        // 存储自定义值变量.
        private final Map<String, Supplier<String>> valueSupplier = new HashMap<>();

        // 是否需要验证.
        private boolean validate;

        private String message;

        private String[] phones;

        private String[] signs;

        private final Map<String, Map<String, String>> store = new HashMap<>();

        private Builder(String action, MethodType method) {
            this.action = action;
            this.method = method;
        }

        /**
         * 设置 模板编号.
         * @param templateCode 模板编号
         * @return {@link Builder}
         */
        public Builder templateId(String templateCode) {
            Assert.notNull(templateCode, "模板编号不能为空");
            this.queryParams.put("TemplateCode", templateCode);
            return this;
        }

        /**
         * 设置模板变量参数.
         * @param key 参数名
         * @param value 参数值
         * @return {@link Builder}
         */
        public Builder templateParams(String key, String value) {
            Assert.notNull(key, "模板变量的 key 不能为空");
            Assert.notNull(value, "模板变量的 value 不能为空");
            this.templateParams.put(key, value);
            return this;
        }

        /**
         * 设置模板变量参数.
         * @param params 参数 {@code Map}
         * @return {@link Builder}
         */
        public Builder templateParams(Map<String, String> params) {
            Assert.notNull(params, "params must be set");
            this.templateParams.putAll(params);
            return this;
        }

        public Builder templateParams(String key, Supplier<String> supplier) {
            Assert.notNull(key, "模板变量的 key 不能为空");
            Assert.notNull(supplier, "模板变量的 supplier 不能为空");
            this.templateParams.put(key, supplier.get());
            this.valueSupplier.put(key, supplier);
            return this;
        }

        /**
         * 设置签名。签名数量必须和手机号数量一一对应，切必须通过阿里签名审核才能发送成功.
         * @param signs 签名
         * @return {@link Builder}
         */
        public Builder signs(Collection<String> signs) {
            Assert.notNull(signs, "签名不能为空");
            this.signs = signs.toArray(new String[0]);
            return this;
        }

        /**
         * 设置签名。签名数量必须和手机号数量一一对应，切必须通过阿里签名审核才能发送成功.
         * @param signs 签名
         * @return {@link Builder}
         */
        public Builder signs(String... signs) {
            Assert.notNull(signs, "签名不能为空");
            this.signs = signs;
            return this;
        }

        /**
         * 设置签名。签名数量必须和手机号数量一一对应，切必须通过阿里签名审核才能发送成功.
         * @param signs 签名
         * @return {@link Builder}
         */
        public Builder signs(String signs) {
            Assert.notNull(signs, "签名不能为空");
            this.signs = StringUtils.commaDelimitedListToStringArray(signs);
            return this;
        }

        /**
         * 手机号集合.
         * @param phones 手机号
         * @return {@link Builder}
         */
        public Builder phones(Collection<String> phones) {
            Assert.notNull(phones, "手机号不能为空");
            this.phones = phones.toArray(new String[0]);
            return this;
        }

        /**
         * 不定项参数.
         * @param phones 手机号
         * @return {@link Builder}
         */
        public Builder phones(String... phones) {
            Assert.notNull(phones, "手机号不能为空");
            this.phones = phones;
            return this;
        }

        /**
         * 设置手机号。可以是单个手机号，多个的话需要用逗号隔开.
         * @param phones 电话号码
         * @return {@link Builder}
         */
        public Builder phones(String phones) {
            Assert.notNull(phones, "手机号不能为空");
            this.phones = StringUtils.commaDelimitedListToStringArray(phones);
            return this;
        }

        /**
         * 设置消息.
         * @param message 消息
         * @return {@link Builder}
         */
        public Builder message(String message) {
            Assert.notNull(message, "消息不能为空");
            this.message = message;
            return this;
        }

        /**
         * 设置是否需要验证.
         * @param validate 是否需要验证
         * @return {@link Builder}
         */
        public Builder validate(boolean validate) {
            this.validate = validate;
            return this;
        }

        /**
         * 创建 {@link AliSmsRequest}.
         * @return {@link Builder}
         */
        public AliSmsRequest build() {
            return new AliSmsRequest(processor());
        }

        private Builder processor() {

            int phoneLength = this.phones.length;
            int signLength = this.signs.length;
            // 当有多个签名时，签名数量必须和手机号数量保持一致.
            if (signLength > 1 && signLength != phoneLength) {
                throw new SmsException("签名和手机号不匹配");
            }
            if (phoneLength > 1) {
                // 获取第一个签名，并重复 count 次.
                if (phoneLength != signLength) {
                    this.signs = StringUtils.commaDelimitedListToStringArray(
                            StringUtils.repeat(this.signs[0], StringPools.COMMA, phoneLength));
                }

                List<Map<String, String>> queryParamsList = new ArrayList<>();
                for (String phone : this.phones) {
                    Map<String, String> resultTemplateParams = new HashMap<>();
                    for (Map.Entry<String, String> params : this.templateParams.entrySet()) {
                        String key = params.getKey();
                        String value = params.getKey();
                        if (this.valueSupplier.containsKey(key)) {
                            value = this.valueSupplier.get(key).get();
                        }
                        resultTemplateParams.put(key, value);
                    }
                    // 构建参数对象.
                    this.store.put(phone, resultTemplateParams);
                    queryParamsList.add(resultTemplateParams);
                }

                this.queryParams.put("TemplateParamJson", JsonUtils.toJson(queryParamsList));
                this.queryParams.put("SignNameJson", JsonUtils.toJson(this.signs));
                this.queryParams.put("PhoneNumberJson", JsonUtils.toJson(this.phones));
            }
            else {
                this.store.put(this.phones[0], this.templateParams);
                this.queryParams.put("PhoneNumbers", this.phones[0]);
                this.queryParams.put("SignName", this.signs[0]);
                this.queryParams.put("TemplateParam", JsonUtils.toJson(this.templateParams));
            }
            return this;
        }

    }

}
