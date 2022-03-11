package com.jcohy.framework.starter.oss;

/**
 * 描述: .
 *
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:15:42
 * @since 2022.0.1
 */
public enum OssEnum {

    /**
     * minio.
     */
    MINIO("minio", 1),

    /**
     * qiniu.
     */
    QINIU("qiniu", 2),

    /**
     * ali.
     */
    ALI("ali", 3),

    /**
     * tencent.
     */
    TENCENT("tencent", 4),

    /**
     * huawei.
     */
    HUAWEI("huawei", 5);

    /**
     * 名称.
     */
    final String name;

    /**
     * 类型.
     */
    final int category;

    OssEnum(String name, int category) {
        this.name = name;
        this.category = category;
    }

    /**
     * 匹配枚举值.
     * @param name 名称
     * @return {OssEnum}
     */
    public static OssEnum of(String name) {
        if (name == null) {
            return null;
        }
        OssEnum[] values = OssEnum.values();
        for (OssEnum ossEnum : values) {
            if (ossEnum.name.equals(name)) {
                return ossEnum;
            }
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public int getCategory() {
        return this.category;
    }

}
