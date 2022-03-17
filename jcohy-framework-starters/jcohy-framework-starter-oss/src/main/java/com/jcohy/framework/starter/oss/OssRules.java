package com.jcohy.framework.starter.oss;

import com.jcohy.framework.utils.DateTimeUtils;
import com.jcohy.framework.utils.FileUtils;
import com.jcohy.framework.utils.constant.StringPools;

/**
 * 描述: OSS 文件规则.
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:14:28
 * @since 2022.0.1
 */
public interface OssRules {

    /**
     * 默认前缀.
     */
    String PREFIX = "jcohy-";

    /**
     * 存储桶生成规则.
     * @param bucket 存储桶名
     * @return /
     */
    String bucketRule(String bucket);

    /**
     * 文件名生成规则.
     * @param fileName 文件名
     * @return /
     */
    String fileRule(String fileName);


    /**
     * 默认文件名生成规则.
     * @param fileName 文件名
     * @return /
     */
    default String defaultFileRule(String fileName) {
        return DateTimeUtils.simpleToday() + StringPools.SLASH + DateTimeUtils.toMilliseconds() + StringPools.DOT
                + FileUtils.getFileExtension(fileName);
    }

    /**
     * 默认存储桶生成规则.
     * @param bucket bucket
     * @return /
     */
    default String defaultBucketRule(String bucket) {
        return PREFIX + bucket;
    }

}
