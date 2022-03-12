package com.jcohy.framework.configuration.processor.value;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;

/**
 * 描述: 属性提取器.
 * <p>
 *     Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 * @author jiac
 * @version 2022.0.1 2022/2/11:11:01
 * @since 2022.0.1
 */
@FunctionalInterface
public interface ValueExtractor {

    /**
     * 指定注解的属性列表，后续会从此属性中提取值.
     * @param names 属性列表
     * @return valueExtractor
     */
    static ValueExtractor allFrom(String... names) {
        return new NamedValuesExtractor(names);
    }

    /**
     * 获取注解的值.
     * @param annotation 注解
     * @return 值
     */
    List<Object> getValues(AnnotationMirror annotation);

}
