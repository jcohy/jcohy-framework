package com.jcohy.framework.starter.oss.qiniu;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;

import org.springframework.web.multipart.MultipartFile;

import com.jcohy.framework.starter.oss.OssFile;
import com.jcohy.framework.starter.oss.OssRules;
import com.jcohy.framework.starter.oss.OssTemplate;
import com.jcohy.framework.starter.oss.props.OssProperties;
import com.jcohy.framework.utils.CollectionUtils;
import com.jcohy.framework.utils.StringUtils;
import com.jcohy.framework.utils.constant.NumberConstant;
import com.jcohy.framework.utils.constant.StringPools;

/**
 * 描述: .
 *
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:16:19
 * @since 2022.0.1
 */
public class QiniuOssTemplate implements OssTemplate {

    private final Auth auth;

    private final UploadManager uploadManager;

    private final BucketManager bucketManager;

    private final OssProperties ossProperties;

    private final OssRules rules;

    public QiniuOssTemplate(Auth auth, UploadManager uploadManager, BucketManager bucketManager,
            OssProperties ossProperties, OssRules rules) {
        this.auth = auth;
        this.uploadManager = uploadManager;
        this.bucketManager = bucketManager;
        this.ossProperties = ossProperties;
        this.rules = rules;
    }

    @Override
    public void createBucket(String bucket) {
        try {
            if (!CollectionUtils.contains(this.bucketManager.buckets(), getBucketName(bucket, this.rules))) {
                this.bucketManager.createBucket(getBucketName(bucket, this.rules), Zone.autoZone().getRegion());
            }
        }
        catch (QiniuException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    @Deprecated
    public void deleteBucket(String bucket) {

    }

    @Override

    public boolean bucketExists(String bucket) {
        try {
            return CollectionUtils.contains(this.bucketManager.buckets(), getBucketName(bucket, this.rules));
        }
        catch (QiniuException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    @Deprecated
    public boolean fileExists(String bucket, String key) {
        return false;
    }

    @Override
    public void copyFile(String srcBucket, String key, String targetBucket) {
        try {
            this.bucketManager.copy(getBucketName(srcBucket, this.rules), key, getBucketName(targetBucket, this.rules),
                    key);
        }
        catch (QiniuException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void copyFile(String srcBucket, String srcKey, String targetBucket, String targetKey) {
        try {
            this.bucketManager.copy(getBucketName(srcBucket, this.rules), srcKey,
                    getBucketName(targetBucket, this.rules), targetKey);
        }
        catch (QiniuException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public OssFile statFile(String key) {
        return statFile(this.ossProperties.getBucket(), key);
    }

    @Override
    public OssFile statFile(String bucket, String key) {
        FileInfo stat = null;
        OssFile ossFile = new OssFile();
        try {
            stat = this.bucketManager.stat(getBucketName(bucket, this.rules), key);
            ossFile.setName(StringUtils.isNotEmpty(stat.key) ? stat.key : key);
            ossFile.setLink(fileLink(ossFile.getName()));
            ossFile.setHash(stat.hash);
            ossFile.setLength(stat.fsize);
            ossFile.setPutTime(new Date(stat.putTime / NumberConstant.TEN_THOUSAND));
            ossFile.setContentType(stat.mimeType);
        }
        catch (QiniuException ex) {
            ex.printStackTrace();
        }
        return ossFile;
    }

    @Override
    public String filePath(String key) {
        return getBucketName(this.ossProperties.getBucket(), this.rules).concat(StringPools.SLASH).concat(key);
    }

    @Override
    public String filePath(String bucket, String key) {
        return getBucketName(bucket, this.rules).concat(StringPools.SLASH).concat(key);
    }

    @Override
    public String fileLink(String key) {
        return this.ossProperties.getEndpoint().concat(StringPools.SLASH).concat(key);
    }

    @Override
    public String fileLink(String bucket, String key) {
        return this.ossProperties.getEndpoint().concat(StringPools.SLASH).concat(key);
    }

    @Override
    public OssFile putFile(MultipartFile file) throws IOException {
        return putFile(this.ossProperties.getBucket(), file.getOriginalFilename(), file);
    }

    @Override
    public OssFile putFile(String key, MultipartFile file) throws IOException {
        return putFile(this.ossProperties.getBucket(), key, file);
    }

    @Override
    public OssFile putFile(String bucket, String key, MultipartFile file) throws IOException {
        return putFile(bucket, key, file.getInputStream());
    }

    @Override
    public OssFile putFile(String key, InputStream stream) throws IOException {
        return putFile(this.ossProperties.getBucket(), key, stream);
    }

    @Override
    public OssFile putFile(String bucket, String key, InputStream stream) throws IOException {
        return put(bucket, stream, key, false);
    }

    public OssFile put(String bucketName, InputStream stream, String key, boolean cover) {

        createBucket(bucketName);
        String originalName = key;
        key = getFileName(key, this.rules);
        try {
            // 覆盖上传
            if (cover) {
                this.uploadManager.put(stream, key, getUploadToken(bucketName, key), null, null);
            }
            else {
                Response response = this.uploadManager.put(stream, key, getUploadToken(bucketName), null, null);
                int retry = 0;
                int retryCount = 4;
                while (response.needRetry() && retry < retryCount) {
                    response = this.uploadManager.put(stream, key, getUploadToken(bucketName), null, null);
                    retry++;
                }
            }
        }
        catch (QiniuException ex) {
            ex.printStackTrace();
        }
        OssFile file = new OssFile();
        file.setOriginalName(originalName);
        file.setName(key);
        file.setDomain(getOssHost(bucketName));
        file.setLink(fileLink(bucketName, key));
        return file;
    }

    @Override
    public void deleteFile(String key) {
        try {
            this.bucketManager.delete(getBucketName(this.ossProperties.getBucket(), this.rules), key);
        }
        catch (QiniuException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteFile(String bucket, String key) {
        try {
            this.bucketManager.delete(getBucketName(bucket, this.rules), key);
        }
        catch (QiniuException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteFiles(List<String> keys) {
        keys.forEach(this::deleteFile);
    }

    @Override
    public void deleteFiles(String bucket, List<String> keys) {
        keys.forEach((fileName) -> deleteFile(getBucketName(bucket, this.rules), fileName));
    }

    @Override
    public String getOssHost(String bucket) {
        return this.ossProperties.getEndpoint();
    }

    /**
     * 获取上传凭证，普通上传.
     * @param bucketName 存储桶名称
     * @return /
     */
    public String getUploadToken(String bucketName) {
        return this.auth.uploadToken(getBucketName(bucketName, this.rules));
    }

    /**
     * 获取上传凭证，覆盖上传.
     * @param bucketName bucketName
     * @param key key
     * @return string
     */
    private String getUploadToken(String bucketName, String key) {
        return this.auth.uploadToken(getBucketName(bucketName, this.rules), key);
    }

}
