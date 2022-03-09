package com.jcohy.framework.starter.oss.minio;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import io.minio.BucketExistsArgs;
import io.minio.CopyObjectArgs;
import io.minio.CopySource;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.RemoveObjectsArgs;
import io.minio.SetBucketPolicyArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteObject;

import org.springframework.web.multipart.MultipartFile;

import com.jcohy.framework.starter.oss.OssFile;
import com.jcohy.framework.starter.oss.OssRules;
import com.jcohy.framework.starter.oss.OssTemplate;
import com.jcohy.framework.starter.oss.props.BucketScope;
import com.jcohy.framework.starter.oss.props.OssProperties;
import com.jcohy.framework.utils.DateTimeUtils;
import com.jcohy.framework.utils.StringUtils;
import com.jcohy.framework.utils.constant.StringPools;

/**
 * 描述: .
 *
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:16:17
 * @since 2022.0.1
 */
public class MinioOssTemplate implements OssTemplate {

	/**
	 * MinIO客户端.
	 */
	private final MinioClient client;

	/**
	 * 存储桶命名规则.
	 */
	private final OssRules rules;

	/**
	 * 配置类.
	 */
	private final OssProperties ossProperties;

	public MinioOssTemplate(MinioClient client, OssRules rules, OssProperties ossProperties) {
		this.client = client;
		this.rules = rules;
		this.ossProperties = ossProperties;
	}

	@Override
	public void createBucket(String bucket) {
		try {
			if (!this.client
					.bucketExists(BucketExistsArgs.builder().bucket(getBucketName(bucket, this.rules)).build())) {
				this.client.makeBucket(MakeBucketArgs.builder().bucket(getBucketName(bucket, this.rules)).build());
				this.client.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(getBucketName(bucket, this.rules))
						.config(getPolicyType(getBucketName(bucket, this.rules), BucketScope.READ)).build());
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void deleteBucket(String bucket) {
		try {
			this.client.removeBucket(RemoveBucketArgs.builder().bucket(getBucketName(bucket, this.rules)).build());
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public boolean bucketExists(String bucket) {
		boolean exists = false;
		try {
			exists = this.client
					.bucketExists(BucketExistsArgs.builder().bucket(getBucketName(bucket, this.rules)).build());
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return exists;
	}

	public Bucket getBucket() {
		return getBucket(getBucketName(this.ossProperties.getBucket(), this.rules));
	}

	public Bucket getBucket(String bucketName) {
		Optional<Bucket> bucketOptional = Optional.empty();
		try {
			bucketOptional = this.client.listBuckets().stream()
					.filter((bucket) -> bucket.name().equals(getBucketName(bucketName, this.rules))).findFirst();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return bucketOptional.orElse(null);
	}

	public List<Bucket> listBuckets() {
		List<Bucket> buckets = new ArrayList<>();
		try {
			buckets = this.client.listBuckets();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return buckets;
	}

	@Override
	public boolean fileExists(String bucket, String key) {
		return false;
		// client.copyObject(getBucketName(bucket,rules), key,
		// getBucketName(bucket,rules));
	}

	@Override
	public void copyFile(String srcBucket, String key, String targetBucket) {
		copyFile(srcBucket, key, targetBucket, key);
	}

	@Override
	public void copyFile(String srcBucket, String srcKey, String targetBucket, String targetKey) {
		try {
			this.client.copyObject(CopyObjectArgs.builder()
					.source(CopySource.builder().bucket(getBucketName(srcBucket, this.rules)).object(srcKey).build())
					.bucket(getBucketName(targetBucket, this.rules)).object(targetKey).build());
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public OssFile statFile(String key) {
		return statFile(this.ossProperties.getBucket(), key);
	}

	@Override
	public OssFile statFile(String bucket, String key) {
		OssFile ossFile = new OssFile();
		try {
			StatObjectResponse stat = this.client
					.statObject(StatObjectArgs.builder().bucket(getBucketName(bucket, this.rules)).object(key).build());
			ossFile.setName(StringUtils.isEmpty(stat.object()) ? key : stat.object());
			ossFile.setLink(fileLink(ossFile.getName()));
			ossFile.setHash(String.valueOf(stat.hashCode()));
			ossFile.setLength(stat.size());
			ossFile.setPutTime(Date.from(DateTimeUtils.toInstant(stat.lastModified().toLocalDateTime())));
			ossFile.setContentType(stat.contentType());
		}
		catch (Exception ex) {
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
		return this.ossProperties.getEndpoint().concat(StringPools.SLASH)
				.concat(getBucketName(this.ossProperties.getBucket(), this.rules)).concat(StringPools.SLASH).concat(key);

	}

	@Override
	public String fileLink(String bucket, String key) {
		return this.ossProperties.getEndpoint().concat(StringPools.SLASH)
				.concat(getBucketName(this.ossProperties.getBucket(), this.rules)).concat(StringPools.SLASH).concat(key);

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
		return putFile(bucket, file.getOriginalFilename(), file.getInputStream());
	}

	@Override
	public OssFile putFile(String key, InputStream stream) throws IOException {
		return putFile(this.ossProperties.getBucket(), key, stream);
	}

	@Override
	public OssFile putFile(String bucket, String key, InputStream stream) throws IOException {
		return putFile(bucket, key, stream, "application/octet-stream");
	}

	public OssFile putFile(String bucketName, String fileName, InputStream stream, String contentType) {
		OssFile file = new OssFile();
		try {
			createBucket(bucketName);
			String originalName = fileName;
			fileName = getFileName(fileName, this.rules);
			this.client.putObject(PutObjectArgs.builder().bucket(getBucketName(bucketName, this.rules)).object(fileName)
					.stream(stream, stream.available(), -1).contentType(contentType).build());
			file.setOriginalName(originalName);
			file.setName(fileName);
			file.setDomain(getOssHost(bucketName));
			file.setLink(fileLink(bucketName, fileName));
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

		return file;
	}

	@Override
	public void deleteFile(String key) {
		deleteFile(this.ossProperties.getBucket(), key);
	}

	@Override
	public void deleteFile(String bucket, String key) {
		try {
			this.client.removeObject(
					RemoveObjectArgs.builder().bucket(getBucketName(bucket, this.rules)).object(key).build());
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void deleteFiles(List<String> keys) {
		deleteFiles(this.ossProperties.getBucket(), keys);
	}

	@Override
	public void deleteFiles(String bucket, List<String> keys) {
		Stream<DeleteObject> stream = keys.stream().map(DeleteObject::new);
		this.client.removeObjects(RemoveObjectsArgs.builder().bucket(getBucketName(bucket, this.rules))
				.objects(stream::iterator).build());
	}

	@Override
	public String getOssHost(String bucket) {
		return this.ossProperties.getEndpoint() + StringPools.SLASH + getBucketName(bucket, this.rules);
	}

	/**
	 * 获取存储桶策略.
	 * @param bucketName 存储桶名称
	 * @param scope 策略枚举
	 * @return string
	 */
	public static String getPolicyType(String bucketName, BucketScope scope) {
		StringBuilder builder = new StringBuilder();
		builder.append("{\n");
		builder.append("    \"Statement\": [\n");
		builder.append("        {\n");
		builder.append("            \"Action\": [\n");

		switch (scope) {
			case WRITE:
				builder.append("                \"s3:GetBucketLocation\",\n");
				builder.append("                \"s3:ListBucketMultipartUploads\"\n");
				break;
			case READ_WRITE:
				builder.append("                \"s3:GetBucketLocation\",\n");
				builder.append("                \"s3:ListBucket\",\n");
				builder.append("                \"s3:ListBucketMultipartUploads\"\n");
				break;
			default:
				builder.append("                \"s3:GetBucketLocation\"\n");
				break;
		}

		builder.append("            ],\n");
		builder.append("            \"Effect\": \"Allow\",\n");
		builder.append("            \"Principal\": \"*\",\n");
		builder.append("            \"Resource\": \"arn:aws:s3:::");
		builder.append(bucketName);
		builder.append("\"\n");
		builder.append("        },\n");
		if (BucketScope.READ.equals(scope)) {
			builder.append("        {\n");
			builder.append("            \"Action\": [\n");
			builder.append("                \"s3:ListBucket\"\n");
			builder.append("            ],\n");
			builder.append("            \"Effect\": \"Deny\",\n");
			builder.append("            \"Principal\": \"*\",\n");
			builder.append("            \"Resource\": \"arn:aws:s3:::");
			builder.append(bucketName);
			builder.append("\"\n");
			builder.append("        },\n");

		}
		builder.append("        {\n");
		builder.append("            \"Action\": ");

		switch (scope) {
			case WRITE:
				builder.append("[\n");
				builder.append("                \"s3:AbortMultipartUpload\",\n");
				builder.append("                \"s3:DeleteObject\",\n");
				builder.append("                \"s3:ListMultipartUploadParts\",\n");
				builder.append("                \"s3:PutObject\"\n");
				builder.append("            ],\n");
				break;
			case READ_WRITE:
				builder.append("[\n");
				builder.append("                \"s3:AbortMultipartUpload\",\n");
				builder.append("                \"s3:DeleteObject\",\n");
				builder.append("                \"s3:GetObject\",\n");
				builder.append("                \"s3:ListMultipartUploadParts\",\n");
				builder.append("                \"s3:PutObject\"\n");
				builder.append("            ],\n");
				break;
			default:
				builder.append("\"s3:GetObject\",\n");
				break;
		}

		builder.append("            \"Effect\": \"Allow\",\n");
		builder.append("            \"Principal\": \"*\",\n");
		builder.append("            \"Resource\": \"arn:aws:s3:::");
		builder.append(bucketName);
		builder.append("/*\"\n");
		builder.append("        }\n");
		builder.append("    ],\n");
		builder.append("    \"Version\": \"2012-10-17\"\n");
		builder.append("}\n");
		return builder.toString();
	}

}
