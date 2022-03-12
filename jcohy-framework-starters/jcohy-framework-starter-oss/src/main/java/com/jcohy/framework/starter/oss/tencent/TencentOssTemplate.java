package com.jcohy.framework.starter.oss.tencent;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jcohy.framework.starter.oss.OssFile;
import com.jcohy.framework.starter.oss.OssRules;
import com.jcohy.framework.starter.oss.OssTemplate;
import com.jcohy.framework.starter.oss.props.BucketScope;
import com.jcohy.framework.starter.oss.props.OssProperties;
import com.jcohy.framework.utils.constant.StringPools;

/**
 * 描述: .
 * <p>
 *     Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 * @author jiac
 * @version 2022.0.1 3/9/22:16:23
 * @since 2022.0.1
 */
public class TencentOssTemplate implements OssTemplate {

    private final OssProperties ossProperties;

    private final COSClient cosClient;

    private final OssRules ossRules;

    public TencentOssTemplate(OssProperties ossProperties, COSClient cosClient, OssRules ossRules) {
        this.ossProperties = ossProperties;
        this.cosClient = cosClient;
        this.ossRules = ossRules;
    }

    @Override
    public void createBucket(String bucket) {
        if (!bucketExists(bucket)) {
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(getBucketName(bucket));
            createBucketRequest.setCannedAcl(BucketScope.getTencentAccessControl(this.ossProperties.getScope()));
            this.cosClient.createBucket(createBucketRequest);
        }
    }

    @Override
    public void deleteBucket(String bucket) {
        this.cosClient.deleteBucket(getBucketName(bucket));
    }

    @Override
    public boolean bucketExists(String bucket) {
        return this.cosClient.doesBucketExist(getBucketName(bucket));
    }

    @Override
    public boolean fileExists(String bucket, String key) {
        return this.cosClient.doesObjectExist(getBucketName(bucket), key);
    }

    @Override
    public void copyFile(String srcBucket, String key, String targetBucket) {
        this.cosClient.copyObject(getBucketName(srcBucket), key, getBucketName(targetBucket), key);
    }

    @Override
    public void copyFile(String srcBucket, String srcKey, String targetBucket, String targetKey) {
        this.cosClient.copyObject(getBucketName(srcBucket), srcKey, getBucketName(targetBucket), targetKey);
    }

    @Override
    public OssFile statFile(String key) {
        return statFile(this.ossProperties.getBucket(), key);
    }

    @Override
    public OssFile statFile(String bucket, String key) {
        ObjectMetadata stat = this.cosClient.getObjectMetadata(getBucketName(bucket), key);
        OssFile ossFile = new OssFile();
        ossFile.setName(key);
        ossFile.setLink(fileLink(ossFile.getName()));
        ossFile.setHash(stat.getContentMD5());
        ossFile.setLength(stat.getContentLength());
        ossFile.setPutTime(stat.getLastModified());
        ossFile.setContentType(stat.getContentType());
        return ossFile;
    }

    @Override
    public String filePath(String key) {
        return getOssHost().concat(StringPools.SLASH).concat(key);
    }

    @Override
    public String filePath(String bucket, String key) {
        return getOssHost(bucket).concat(StringPools.SLASH).concat(key);
    }

    @Override
    public String fileLink(String key) {
        return getOssHost().concat(StringPools.SLASH).concat(key);
    }

    @Override
    public String fileLink(String bucket, String key) {
        return getOssHost(bucket).concat(StringPools.SLASH).concat(key);
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
        key = getFileName(key, this.ossRules);
        // 覆盖上传
        if (cover) {
            this.cosClient.putObject(getBucketName(bucketName), key, stream, null);
        }
        else {
            PutObjectResult response = this.cosClient.putObject(getBucketName(bucketName), key, stream, null);
            int retry = 0;
            int retryCount = 4;
            while (!StringUtils.hasLength(response.getETag()) && retry < retryCount) {
                response = this.cosClient.putObject(getBucketName(bucketName), key, stream, null);
                retry++;
            }
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
        this.cosClient.deleteObject(getBucketName(this.ossProperties.getBucket()), key);
    }

    @Override
    public void deleteFile(String bucket, String key) {
        this.cosClient.deleteObject(getBucketName(bucket), key);
    }

    @Override
    public void deleteFiles(List<String> keys) {
        keys.forEach(this::deleteFile);
    }

    @Override
    public void deleteFiles(String bucket, List<String> keys) {
        keys.forEach((fileName) -> deleteFile(getBucketName(bucket), fileName));
    }

    /**
     * 根据规则生成存储桶名称规则.
     * @param bucketName 存储桶名称
     * @return string
     */
    private String getBucketName(String bucketName) {
        return getBucketName(bucketName, this.ossRules).concat(StringPools.DASH).concat(this.ossProperties.getAppId());
    }

    /**
     * 获取域名.
     * @param bucketName 存储桶名称
     * @return string
     */
    @Override
    public String getOssHost(String bucketName) {
        return "http://" + this.cosClient.getClientConfig().getEndpointBuilder()
                .buildGeneralApiEndpoint(getBucketName(bucketName));
    }

    /**
     * 获取域名.
     * @return string
     */
    public String getOssHost() {
        return getOssHost(this.ossProperties.getBucket());
    }

}
