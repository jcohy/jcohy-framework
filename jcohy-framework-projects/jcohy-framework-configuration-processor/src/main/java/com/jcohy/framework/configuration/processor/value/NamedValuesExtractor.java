package com.jcohy.framework.configuration.processor.value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2022/2/11:11:09
 * @since 2022.0.1
 */
public class NamedValuesExtractor extends AbstractValueExtractor {

	private final Set<String> names;

	NamedValuesExtractor(String... names) {
		this.names = new HashSet<>(Arrays.asList(names));
	}

	@Override
	public List<Object> getValues(AnnotationMirror annotation) {
		List<Object> result = new ArrayList<>();
		Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotation.getElementValues();
		annotation.getElementValues().forEach((key, value) -> {
			if (this.names.contains(key.getSimpleName().toString())) {
				extractValues(value).forEach(result::add);
			}
		});
		return result;
	}

}
