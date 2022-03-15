package com.jcohy.framework.starter.sms.props;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/15/22:10:30
 * @since 2022.0.1
 */
@ConfigurationProperties(prefix = "saga.sms", ignoreInvalidFields = true)
public class SmsProperties {

    /**
     * 短信运营商 ali,tencent,baidu....
     */
    private String name;

    /**
     * accessKey.
     */
    private String accessKey;

    /**
     * secretKey.
     */
    private String secretKey;

    /**
     * 短信签名.
     */
    private String signName;

    /**
     * 缓存 key 的前缀，此选项只有在开启了验证才有效。默认为 SAGA:SMS:.
     */
    private String cacheKey = "SAGA:SMS:";

    /**
     * 缓存 key 失效时间，此选项只有在开启了验证才有效.默认为 15 分钟.
     */
    private Duration timeout = Duration.ofMinutes(15);

    /**
     * regionId.
     */
    private String regionId;

    /**
     * 端点.
     */
    private String endpoint;

    private Map<String, String> payload;

    /**
     * 腾讯的Appid.
     */
    private String smsSdkAppId;

    public String getEndpoint() {
        return this.endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getSmsSdkAppId() {
        return this.smsSdkAppId;
    }

    public void setSmsSdkAppId(String smsSdkAppId) {
        this.smsSdkAppId = smsSdkAppId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessKey() {
        return this.accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSignName() {
        return this.signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public Map<String, String> getPayload() {
        return this.payload;
    }

    public void setPayload(Map<String, String> payload) {
        this.payload = payload;
    }

    public String getCacheKey() {
        return this.cacheKey;
    }

    public SmsProperties setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
        return this;
    }

    public Duration getTimeout() {
        return this.timeout;
    }

    public SmsProperties setTimeout(Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SmsProperties that = (SmsProperties) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getAccessKey(), that.getAccessKey())
                && Objects.equals(getSecretKey(), that.getSecretKey())
                && Objects.equals(getSignName(), that.getSignName())
                && Objects.equals(getCacheKey(), that.getCacheKey()) && Objects.equals(getTimeout(), that.getTimeout())
                && Objects.equals(getRegionId(), that.getRegionId())
                && Objects.equals(getSmsSdkAppId(), that.getSmsSdkAppId())
                && Objects.equals(getPayload(), that.getPayload());
    }

    @Override
    public int hashCode() {
        int result = (getName() != null) ? getName().hashCode() : 0;
        result = 31 * result + ((getAccessKey() != null) ? getAccessKey().hashCode() : 0);
        result = 31 * result + ((getSecretKey() != null) ? getSecretKey().hashCode() : 0);
        result = 31 * result + ((getSignName() != null) ? getSignName().hashCode() : 0);
        result = 31 * result + ((getCacheKey() != null) ? getCacheKey().hashCode() : 0);
        result = 31 * result + ((getTimeout() != null) ? getTimeout().hashCode() : 0);
        result = 31 * result + ((getRegionId() != null) ? getRegionId().hashCode() : 0);
        result = 31 * result + ((getPayload() != null) ? getPayload().hashCode() : 0);
        result = 31 * result + ((getSmsSdkAppId() != null) ? getSmsSdkAppId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SmsProperties{" + "name='" + this.name + '\'' + ", accessKey='" + this.accessKey + '\''
                + ", secretKey='" + this.secretKey + '\'' + ", signName='" + this.signName + '\'' + ", cacheKey='"
                + this.cacheKey + '\'' + ", timeout=" + this.timeout + ", regionId='" + this.regionId + '\''
                + ", payload=" + this.payload + ", smsSdkAppId='" + this.smsSdkAppId + '\'' + '}';
    }

}
