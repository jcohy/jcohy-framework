package com.jcohy.framework.starter.oss;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * 描述: .
 *
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:15:36
 * @since 2022.0.1
 */
public interface OssTemplate {

	/**
	 * 创建 bucket.
	 * @param bucket 存储桶名称
	 */
	void createBucket(String bucket);

	/**
	 * 删除 bucket.
	 * @param bucket 存储桶名称
	 */
	void deleteBucket(String bucket);

	/**
	 * 判断 bucket 是否存在.
	 * @param bucket 存储桶名称
	 * @return {@code true} bucket 存在，否则返回 {@code false}
	 */
	boolean bucketExists(String bucket);

	/**
	 * 判断文件是否存在.
	 * @param bucket 存储桶名称
	 * @param key key 也即文件全路径
	 * @return {@code true} bucket 存在，否则返回 {@code false}
	 */
	boolean fileExists(String bucket, String key);

	/**
	 * 文件复制.
	 * @param srcBucket 源存储桶名称
	 * @param key key 也即文件全路径
	 * @param targetBucket 目标存储桶名称
	 */
	void copyFile(String srcBucket, String key, String targetBucket);

	/**
	 *
	 * 文件复制.
	 * @param srcBucket 源存储桶名称
	 * @param srcKey key 也即文件全路径
	 * @param targetBucket 目标存储桶名称
	 * @param targetKey 目标文件名
	 */
	void copyFile(String srcBucket, String srcKey, String targetBucket, String targetKey);

	/**
	 * 获取文件信息.
	 * @param key key 也即文件全路径
	 * @return /
	 */
	OssFile statFile(String key);

	/**
	 * 获取文件信息.
	 * @param bucket 存储桶名称
	 * @param key key 也即文件全路径
	 * @return inputStream
	 */
	OssFile statFile(String bucket, String key);

	/**
	 * 获取文件相对路径.
	 * @param key key 也即文件全路径
	 * @return string
	 */
	String filePath(String key);

	/**
	 * 获取文件相对路径.
	 * @param bucket 存储桶名称
	 * @param key key 也即文件全路径
	 * @return string
	 */
	String filePath(String bucket, String key);

	/**
	 * 获取文件地址.
	 * @param key key 也即文件全路径
	 * @return string
	 */
	String fileLink(String key);

	/**
	 * 获取文件地址.
	 * @param bucket 存储桶名称
	 * @param key key 也即文件全路径
	 * @return string
	 */
	String fileLink(String bucket, String key);

	/**
	 * 上传文件.
	 * @param file 上传文件类
	 * @return {@link OssFile}
	 * @throws IOException 文件上传异常
	 */
	OssFile putFile(MultipartFile file) throws IOException;

	/**
	 * 上传文件.
	 * @param file 上传文件类
	 * @param key key 也即文件全路径
	 * @return {@link OssFile}
	 * @throws IOException 文件上传异常
	 */
	OssFile putFile(String key, MultipartFile file) throws IOException;

	/**
	 * 上传文件.
	 * @param bucket 存储桶名称
	 * @param key key 也即文件全路径
	 * @param file 上传文件类
	 * @return {@link OssFile}
	 * @throws IOException 文件上传异常
	 */
	OssFile putFile(String bucket, String key, MultipartFile file) throws IOException;

	/**
	 * 上传文件.
	 * @param key key 也即文件全路径
	 * @param stream 文件流
	 * @return {@link OssFile}
	 * @throws IOException 文件上传异常
	 */
	OssFile putFile(String key, InputStream stream) throws IOException;

	/**
	 * 上传文件.
	 * @param bucket 存储桶名称
	 * @param key key 也即文件全路径
	 * @param stream 文件流
	 * @return {@link OssFile}
	 * @throws IOException 文件上传异常
	 */
	OssFile putFile(String bucket, String key, InputStream stream) throws IOException;

	/**
	 * 删除文件.
	 * @param key key 也即文件全路径
	 */
	void deleteFile(String key);

	/**
	 * 删除文件.
	 * @param bucket 存储桶名称
	 * @param key key 也即文件全路径
	 */
	void deleteFile(String bucket, String key);

	/**
	 * 批量删除文件.
	 * @param keys key 也即文件全路径
	 */
	void deleteFiles(List<String> keys);

	/**
	 * 批量删除文件.
	 * @param bucket 存储桶名称
	 * @param keys key 也即文件全路径
	 */
	void deleteFiles(String bucket, List<String> keys);

	/**
	 * 获取 bucket 域名.
	 * @param bucket bucket
	 * @return /
	 */
	String getOssHost(String bucket);

	/**
	 * 根据规则生成文件名称规则.
	 * @param bucket 存储桶名
	 * @param rules oss 规则
	 * @return string
	 */
	default String getBucketName(String bucket, OssRules rules) {
		return rules.bucketRule(bucket);
	}

	/**
	 * 根据规则生成文件名称规则.
	 * @param originalFilename 原始文件名
	 * @param rules oss 规则
	 * @return string
	 */
	default String getFileName(String originalFilename, OssRules rules) {
		return rules.fileRule(originalFilename);
	}

}
