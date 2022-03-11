package com.jcohy.framework.starter.oss.ali;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectResult;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jcohy.framework.starter.oss.OssFile;
import com.jcohy.framework.starter.oss.OssRules;
import com.jcohy.framework.starter.oss.OssTemplate;
import com.jcohy.framework.starter.oss.props.BucketScope;
import com.jcohy.framework.starter.oss.props.OssProperties;
import com.jcohy.framework.utils.JsonUtils;
import com.jcohy.framework.utils.constant.NumberConstant;
import com.jcohy.framework.utils.constant.StringPools;

/**
 * 描述: .
 *
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:15:44
 * @since 2022.0.1
 */
public class AliOssTemplate implements OssTemplate {

    private final OSSClient client;

    private final OssProperties ossProperties;

    private final OssRules rule;

    public AliOssTemplate(OSSClient client, OssProperties ossProperties, OssRules rule) {
        this.client = client;
        this.ossProperties = ossProperties;
        this.rule = rule;
    }

    @Override
    public void createBucket(String bucket) {
        if (!bucketExists(bucket)) {
            CreateBucketRequest request = new CreateBucketRequest(getBucketName(bucket));
            request.setCannedACL(BucketScope.getAccessControl(this.ossProperties.getScope()));
            this.client.createBucket(request);
        }
    }

    @Override
    public void deleteBucket(String bucket) {
        this.client.deleteBucket(getBucketName(bucket));
    }

    @Override
    public boolean bucketExists(String bucket) {
        return this.client.doesBucketExist(getBucketName(bucket));
    }

    @Override
    public boolean fileExists(String bucket, String key) {
        return this.client.doesObjectExist(getBucketName(bucket), key);
    }

    @Override
    public void copyFile(String srcBucket, String key, String targetBucket) {
        this.client.copyObject(getBucketName(srcBucket), key, getBucketName(targetBucket), key);
    }

    @Override
    public void copyFile(String srcBucket, String srcKey, String targetBucket, String targetKey) {
        this.client.copyObject(getBucketName(srcBucket), srcKey, getBucketName(targetBucket), targetKey);
    }

    @Override
    public OssFile statFile(String key) {
        return statFile(this.ossProperties.getBucket(), key);
    }

    @Override
    public OssFile statFile(String bucket, String key) {
        ObjectMetadata stat = this.client.getObjectMetadata(getBucketName(bucket), key);
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
        key = getFileName(key);
        // 覆盖上传
        if (cover) {
            this.client.putObject(getBucketName(bucketName), key, stream);
        }
        else {
            PutObjectResult response = this.client.putObject(getBucketName(bucketName), key, stream);
            int retry = 0;
            int retryCount = 4;
            while (!StringUtils.hasLength(response.getETag()) && retry < retryCount) {
                response = this.client.putObject(getBucketName(bucketName), key, stream);
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
        this.client.deleteObject(getBucketName(), key);
    }

    @Override
    public void deleteFile(String bucket, String key) {
        this.client.deleteObject(getBucketName(bucket), key);
    }

    @Override
    public void deleteFiles(List<String> keys) {
        keys.forEach(this::deleteFile);
    }

    @Override
    public void deleteFiles(String bucket, List<String> keys) {
        keys.forEach((file) -> deleteFile(bucket, file));
    }

    public String getUploadToken() {
        return getUploadToken(this.ossProperties.getBucket());
    }

    /**
     * TODO 过期时间
     * <p>
     * 获取上传凭证，普通上传.
     * @param bucket 存储桶
     * @return /
     */
    public String getUploadToken(String bucket) {
        // 默认过期时间2小时
        return getUploadToken(bucket, this.ossProperties.getPayload().get("expireTime", NumberConstant.ONE_HOUR));
    }

    /**
     * TODO 上传大小限制、基础路径
     * <p>
     * 获取上传凭证，普通上传.
     * @param bucket 存储桶名
     * @param expireTime 实现时间
     * @return /
     */
    public String getUploadToken(String bucket, long expireTime) {
        String baseDir = "upload";

        long expireEndTime = System.currentTimeMillis() + expireTime * NumberConstant.THOUSAND;
        Date expiration = new Date(expireEndTime);

        PolicyConditions policyConds = new PolicyConditions();
        // 默认大小限制10M
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0,
                this.ossProperties.getPayload().get("contentLengthRange", NumberConstant.TEN * NumberConstant.ONE_MB));
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, baseDir);

        String postPolicy = this.client.generatePostPolicy(expiration, policyConds);
        byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = this.client.calculatePostSignature(postPolicy);

        Map<String, String> respMap = new LinkedHashMap<>(16);
        respMap.put("accessid", this.ossProperties.getAccessKey());
        respMap.put("policy", encodedPolicy);
        respMap.put("signature", postSignature);
        respMap.put("dir", baseDir);
        respMap.put("host", getOssHost(bucket));
        respMap.put("expire", String.valueOf(expireEndTime / NumberConstant.THOUSAND));
        return JsonUtils.toJson(respMap);
    }

    private String getBucketName() {
        return this.rule.bucketRule(this.ossProperties.getBucket());
    }

    /**
     * 根据规则生成文件名称规则.
     * @param bucket 存储桶名
     * @return string
     */
    private String getBucketName(String bucket) {
        return this.rule.bucketRule(bucket);
    }

    /**
     * 根据规则生成文件名称规则.
     * @param originalFilename 原始文件名
     * @return string
     */
    private String getFileName(String originalFilename) {
        return this.rule.fileRule(originalFilename);
    }

    /**
     * 获取域名.
     * @param bucketName 存储桶名称
     * @return string
     */
    @Override
    public String getOssHost(String bucketName) {
        String prefix = this.ossProperties.getEndpoint().contains("https://") ? "https://" : "http://";
        return prefix + getBucketName(bucketName) + StringPools.DOT
                + this.ossProperties.getEndpoint().replaceFirst(prefix, StringPools.EMPTY);
    }

    /**
     * 获取域名.
     * @return string
     */
    public String getOssHost() {
        return getOssHost(this.ossProperties.getBucket());
    }

}
