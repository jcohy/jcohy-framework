package com.jcohy.framework.starter.mongodb.convert;

import com.fasterxml.jackson.databind.JsonNode;
import org.bson.BasicBSONObject;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.Nullable;

import com.jcohy.framework.utils.JsonUtils;

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
@ReadingConverter
public enum MongoObjectToJsonNodeConverter implements Converter<BasicBSONObject, JsonNode> {

	/**
	 * 实例.
	 */
	INSTANCE;

	@Override
	public JsonNode convert(@Nullable BasicBSONObject source) {
		if (source == null) {
			return null;
		}
		return JsonUtils.getInstance().valueToTree(source);
	}

}
