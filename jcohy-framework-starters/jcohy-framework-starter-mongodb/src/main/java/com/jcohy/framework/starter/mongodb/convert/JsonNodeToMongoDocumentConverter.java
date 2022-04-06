package com.jcohy.framework.starter.mongodb.convert;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bson.Document;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.Nullable;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 4/6/22:11:06
 * @since 2022.0.1
 */
@WritingConverter
public enum JsonNodeToMongoDocumentConverter implements Converter<ObjectNode, Document> {

	/**
	 * 实例.
	 */
	INSTANCE;

	@Override
	public Document convert(@Nullable ObjectNode source) {
		return (source != null) ? Document.parse(source.toString()) : null;
	}

}
