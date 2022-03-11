package com.jcohy.framework.starter.oss.props;

import java.util.Objects;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.jcohy.framework.utils.maps.CaseInsensitiveChainMap;

/**
 * 描述: .
 *
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:11:44
 * @since 2022.0.1
 */
@ConfigurationProperties(prefix = "jcohy.oss")
public class OssProperties {

    /**
     * 是否启用.
     */
    private Boolean enable;

    /**
     * OSS 服务商 ali,tencent,qinniu....
     */
    private String name;

    /**
     * 对象存储服务的URL.
     */
    private String endpoint;

    /**
     * accessKey.
     */
    private String accessKey;

    /**
     * secretKey.
     */
    private String secretKey;

    /**
     * 应用ID TencentCOS 需要.
     */
    private String appId;

    /**
     * 默认 bucket 名称.
     */
    private String bucket;

    /**
     * 是否开启租户模式.
     */
    private Boolean tenantMode = false;

    private BucketScope scope = BucketScope.PRIVATE;

    private CaseInsensitiveChainMap payload;

    public Boolean getEnable() {
        return this.enable;
    }

    public OssProperties setEnable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public OssProperties setName(String name) {
        this.name = name;
        return this;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public OssProperties setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public String getAccessKey() {
        return this.accessKey;
    }

    public OssProperties setAccessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public OssProperties setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public String getBucket() {
        return this.bucket;
    }

    public OssProperties setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    public CaseInsensitiveChainMap getPayload() {
        return this.payload;
    }

    public OssProperties setPayload(CaseInsensitiveChainMap payload) {
        this.payload = payload;
        return this;
    }

    public String getAppId() {
        return this.appId;
    }

    public OssProperties setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public BucketScope getScope() {
        return this.scope;
    }

    public OssProperties setScope(BucketScope scope) {
        this.scope = scope;
        return this;
    }

    public Boolean getTenantMode() {
        return this.tenantMode;
    }

    public OssProperties setTenantMode(Boolean tenantMode) {
        this.tenantMode = tenantMode;
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
        OssProperties that = (OssProperties) o;
        return Objects.equals(getEnable(), that.getEnable()) && Objects.equals(getName(), that.getName())
                && Objects.equals(getEndpoint(), that.getEndpoint())
                && Objects.equals(getAccessKey(), that.getAccessKey())
                && Objects.equals(getSecretKey(), that.getSecretKey()) && Objects.equals(getAppId(), that.getAppId())
                && Objects.equals(getBucket(), that.getBucket()) && Objects.equals(this.tenantMode, that.tenantMode)
                && getScope() == that.getScope() && Objects.equals(getPayload(), that.getPayload());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEnable(), getName(), getEndpoint(), getAccessKey(), getSecretKey(), getAppId(),
                getBucket(), this.tenantMode, getScope(), getPayload());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OssProperties{");
        sb.append("enable=").append(this.enable);
        sb.append(", name='").append(this.name).append('\'');
        sb.append(", endpoint='").append(this.endpoint).append('\'');
        sb.append(", accessKey='").append(this.accessKey).append('\'');
        sb.append(", secretKey='").append(this.secretKey).append('\'');
        sb.append(", appId='").append(this.appId).append('\'');
        sb.append(", bucket='").append(this.bucket).append('\'');
        sb.append(", tenantMode=").append(this.tenantMode);
        sb.append(", scope=").append(this.scope);
        sb.append(", payload=").append(this.payload);
        sb.append('}');
        return sb.toString();
    }

}
