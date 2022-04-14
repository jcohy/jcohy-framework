package com.jcohy.framework.starter.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import com.jcohy.framework.starter.mongodb.convert.JsonNodeToMongoDocumentConverter;
import com.jcohy.framework.starter.mongodb.convert.MongoObjectToJsonNodeConverter;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 4/6/22:11:04
 * @since 2022.0.1
 */
public class MongoConfiguration {

	@Bean
	public MongoCustomConversions customConversions() {
		List<Converter<?, ?>> converters = new ArrayList<>(2);
		converters.add(MongoObjectToJsonNodeConverter.INSTANCE);
		converters.add(JsonNodeToMongoDocumentConverter.INSTANCE);
		return new MongoCustomConversions(converters);
	}
}
