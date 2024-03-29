package com.jcohy.framework.starter.oss;

import java.util.Date;

/**
 * 描述: Oss 文件信息.
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:15:37
 * @since 2022.0.1
 */
public class OssFile {

    /**
     * 文件 hash 值.
     */
    public String hash;

    /**
     * 域名地址.
     */
    private String domain;

    /**
     * 初始文件名.
     */
    private String originalName;

    /**
     * 附件表 ID.
     */
    private Long attachId;

    /**
     * 文件地址.
     */
    private String link;

    /**
     * 文件名.
     */
    private String name;

    /**
     * 文件大小.
     */
    private long length;

    /**
     * 文件上传时间.
     */
    private Date putTime;

    /**
     * 文件 contentType.
     */
    private String contentType;

    public String getDomain() {
        return this.domain;
    }

    public OssFile setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public OssFile setOriginalName(String originalName) {
        this.originalName = originalName;
        return this;
    }

    public Long getAttachId() {
        return this.attachId;
    }

    public OssFile setAttachId(Long attachId) {
        this.attachId = attachId;
        return this;
    }

    public String getHash() {
        return this.hash;
    }

    public OssFile setHash(String hash) {
        this.hash = hash;
        return this;
    }

    public String getLink() {
        return this.link;
    }

    public OssFile setLink(String link) {
        this.link = link;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public OssFile setName(String name) {
        this.name = name;
        return this;
    }

    public long getLength() {
        return this.length;
    }

    public OssFile setLength(long length) {
        this.length = length;
        return this;
    }

    public Date getPutTime() {
        return this.putTime;
    }

    public OssFile setPutTime(Date putTime) {
        this.putTime = putTime;
        return this;
    }

    public String getContentType() {
        return this.contentType;
    }

    public OssFile setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    @Override
    public String toString() {
        return "OssFile{" + "hash='" + this.hash + '\'' + ", link='" + this.link + '\'' + ", name='" + this.name + '\''
                + ", length=" + this.length + ", putTime=" + this.putTime + ", contentType='" + this.contentType + '\''
                + '}';
    }

}
