package com.jcohy.framework.starter.sms.request;

import java.util.List;

import org.apache.commons.lang3.Validate;

import com.jcohy.framework.starter.sms.SmsAction;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/16/22:18:33
 * @since 2022.0.1
 */
public class SmsTagRequest extends SmsRequest {

    private String resourceType;

    private String regionId;

    private String prodCode;

    private List<SmsTag> tags;

    private List<String> resourcesId;

    private String nextToken;

    private Integer pageSize;

    private boolean all;

    public SmsTagRequest(SmsAction action) {
        super(action);
        Validate.notNull(action, "action 不能为空 !");
    }

    public String getResourceType() {
        return this.resourceType;
    }

    public SmsTagRequest resourceType(String resourceType) {
        this.resourceType = resourceType;
        return this;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public SmsTagRequest regionId(String regionId) {
        this.regionId = regionId;
        return this;
    }

    public String getProdCode() {
        return this.prodCode;
    }

    public SmsTagRequest prodCode(String prodCode) {
        this.prodCode = prodCode;
        return this;
    }

    public List<SmsTag> getTags() {
        return this.tags;
    }

    public SmsTagRequest tags(List<SmsTag> tags) {
        this.tags = tags;
        return this;
    }

    public List<String> getResourcesId() {
        return this.resourcesId;
    }

    public SmsTagRequest resourcesId(List<String> resourcesId) {
        this.resourcesId = resourcesId;
        return this;
    }

    public String getNextToken() {
        return this.nextToken;
    }

    public SmsTagRequest nextToken(String nextToken) {
        this.nextToken = nextToken;
        return this;
    }

    public Integer pageSize() {
        return this.pageSize;
    }

    public SmsTagRequest setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public boolean isAll() {
        return this.all;
    }

    public SmsTagRequest all(boolean all) {
        this.all = all;
        return this;
    }

    public static class SmsTag {

        String key;

        String value;

        public String getKey() {
            return this.key;
        }

        public SmsTag key(String key) {
            this.key = key;
            return this;
        }

        public String getValue() {
            return this.value;
        }

        public SmsTag value(String value) {
            this.value = value;
            return this;
        }

    }

}
