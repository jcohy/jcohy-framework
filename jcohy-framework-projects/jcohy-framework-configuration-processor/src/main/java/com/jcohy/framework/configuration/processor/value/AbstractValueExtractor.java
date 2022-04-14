package com.jcohy.framework.configuration.processor.value;

import java.util.List;
import java.util.stream.Stream;

import javax.lang.model.element.AnnotationValue;
import javax.lang.model.type.DeclaredType;

import com.jcohy.framework.configuration.processor.utils.Elements;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 2022/2/11:11:00
 * @since 2022.0.1
 */
public abstract class AbstractValueExtractor implements ValueExtractor {

    /**
     * 提取值.
     * @param annotationValue 表示注解类型元素的值。
     * @return /
     */
    @SuppressWarnings("unchecked")
    protected Stream<Object> extractValues(AnnotationValue annotationValue) {
        if (annotationValue == null) {
            return Stream.empty();
        }

        Object value = annotationValue.getValue();

        if (value instanceof List) {
            return ((List<AnnotationValue>) value).stream().map((annotation) -> extractValue(annotation.getValue()));
        }

        return Stream.of(value);
    }

    private Object extractValue(Object value) {
        if (value instanceof DeclaredType) {
            return Elements.getQualifiedName(((DeclaredType) value).asElement());
        }
        return value;
    }

}
