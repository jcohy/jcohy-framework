package com.jcohy.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcohy.framework.commons.JcohyFrameworkVersion;
import com.jcohy.framework.utils.constant.StringPools;
import com.jcohy.framework.utils.exceptions.Exceptions;
import com.jcohy.framework.utils.jackson.JcohyJavaTimeModule;

/**
 * 描述: .
 * <p>
 *     Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 * @author jiac
 * @version 2022.0.1 3/9/22:15:52
 * @since 2022.0.1
 */
@SuppressWarnings("unchecked")
public final class JsonUtils {

    private JsonUtils() {
    }

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * 将对象序列化成json字符串.
     * @param value javaBean
     * @param <T> t
     * @return t jsonString json字符串
     * @since 2022.0.1
     */
    public static <T> String toJson(T value) {
        try {
            return getInstance().writeValueAsString(value);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * 将对象序列化成 json byte 数组.
     * @param object javaBean
     * @return jsonString json字符串
     * @since 2022.0.1
     */
    public static byte[] toJsonAsBytes(Object object) {
        try {
            return getInstance().writeValueAsBytes(object);
        }
        catch (JsonProcessingException ex) {
            throw Exceptions.unchecked(ex);
        }
    }

    /**
     * 将json反序列化成对象.
     * @param content content
     * @param valueType class
     * @param <T> t 泛型标记
     * @return bean
     * @since 2022.0.1
     */
    public static <T> T parse(String content, Class<T> valueType) {
        try {
            return getInstance().readValue(content, valueType);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * 将json反序列化成对象.
     * @param content content
     * @param typeReference 泛型类型
     * @param <T> t 泛型标记
     * @return bean
     * @since 2022.0.1
     */
    public static <T> T parse(String content, TypeReference<?> typeReference) {
        try {
            return (T) getInstance().readValue(content, typeReference);
        }
        catch (IOException ex) {
            throw Exceptions.unchecked(ex);
        }
    }

    /**
     * 将json byte 数组反序列化成对象.
     * @param bytes json bytes
     * @param valueType class
     * @param <T> t 泛型标记
     * @return bean
     * @since 2022.0.1
     */
    public static <T> T parse(byte[] bytes, Class<T> valueType) {
        try {
            return getInstance().readValue(bytes, valueType);
        }
        catch (IOException ex) {
            throw Exceptions.unchecked(ex);
        }
    }

    /**
     * 将json反序列化成对象.
     * @param bytes bytes
     * @param typeReference 泛型类型
     * @param <T> t 泛型标记
     * @return bean
     * @since 2022.0.1
     */
    public static <T> T parse(byte[] bytes, TypeReference<?> typeReference) {
        try {
            return (T) getInstance().readValue(bytes, typeReference);
        }
        catch (IOException ex) {
            throw Exceptions.unchecked(ex);
        }
    }

    /**
     * 将json反序列化成对象.
     * @param in inputStream
     * @param valueType class
     * @param <T> t 泛型标记
     * @return bean
     * @since 2022.0.1
     */
    public static <T> T parse(InputStream in, Class<T> valueType) {
        try {
            return getInstance().readValue(in, valueType);
        }
        catch (IOException ex) {
            throw Exceptions.unchecked(ex);
        }
    }

    /**
     * 将json反序列化成对象.
     * @param in inputStream
     * @param typeReference 泛型类型
     * @param <T> t 泛型标记
     * @return bean
     * @since 2022.0.1
     */
    public static <T> T parse(InputStream in, TypeReference<?> typeReference) {
        try {
            return (T) getInstance().readValue(in, typeReference);
        }
        catch (IOException ex) {
            throw Exceptions.unchecked(ex);
        }
    }

    /**
     * 将json反序列化成List对象.
     * @param content content
     * @param valueTypeRef class
     * @param <T> t 泛型标记
     * @return t
     * @since 2022.0.1
     */
    public static <T> List<T> parseArray(String content, Class<T> valueTypeRef) {
        try {

            if (!StringUtils.startsWithIgnoreCase(content, StringPools.BRACKET_START)) {
                content = StringPools.BRACKET_START + content + StringPools.BRACKET_END;
            }

            List<Map<String, Object>> list = (List<Map<String, Object>>) getInstance().readValue(content,
                    new TypeReference<List<T>>() {
                    });
            List<T> result = new ArrayList<>();
            for (Map<String, Object> map : list) {
                result.add(toPojo(map, valueTypeRef));
            }
            return result;
        }
        catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static Map<String, Object> toMap(String content) {
        try {
            return getInstance().readValue(content, Map.class);
        }
        catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static <T> Map<String, T> toMap(String content, Class<T> valueTypeRef) {
        try {
            Map<String, Map<String, Object>> map = (Map<String, Map<String, Object>>) getInstance().readValue(content,
                    new TypeReference<Map<String, T>>() {
                    });
            Map<String, T> result = new HashMap<>(16);
            for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
                result.put(entry.getKey(), toPojo(entry.getValue(), valueTypeRef));
            }
            return result;
        }
        catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static <T> T toPojo(Map<String, Object> fromValue, Class<T> toValueType) {
        return getInstance().convertValue(fromValue, toValueType);
    }

    /**
     * 将json字符串转成 jsonNode.
     * @param jsonString jsonString
     * @return jsonString json字符串
     * @since 2022.0.1
     */
    public static JsonNode readTree(String jsonString) {
        try {
            return getInstance().readTree(jsonString);
        }
        catch (IOException ex) {
            throw Exceptions.unchecked(ex);
        }
    }

    /**
     * 将json字符串转成 jsonNode.
     * @param in inputStream
     * @return jsonString json字符串
     * @since 2022.0.1
     */
    public static JsonNode readTree(InputStream in) {
        try {
            return getInstance().readTree(in);
        }
        catch (IOException ex) {
            throw Exceptions.unchecked(ex);
        }
    }

    /**
     * 将json字符串转成 jsonNode.
     * @param content content
     * @return jsonString json字符串
     * @since 2022.0.1
     */
    public static JsonNode readTree(byte[] content) {
        try {
            return getInstance().readTree(content);
        }
        catch (IOException ex) {
            throw Exceptions.unchecked(ex);
        }
    }

    /**
     * 将json字符串转成 jsonNode.
     * @param jsonParser jsonParser
     * @return jsonString json字符串
     * @since 2022.0.1
     */
    public static JsonNode readTree(JsonParser jsonParser) {
        try {
            return getInstance().readTree(jsonParser);
        }
        catch (IOException ex) {
            throw Exceptions.unchecked(ex);
        }
    }

    /**
     * 将json byte 数组反序列化成对象.
     * @param content json bytes
     * @param valueType class
     * @param <T> 泛型标记
     * @return 泛型对象
     * @since 2022.0.1
     */
    public static <T> T readValue(byte[] content, Class<T> valueType) {
        if (ObjectUtils.isEmpty(content)) {
            return null;
        }
        try {
            return getInstance().readValue(content, valueType);
        }
        catch (IOException ex) {
            throw Exceptions.unchecked(ex);
        }
    }

    /**
     * 范型readValue json ==&gt; Pager&lt;MyBean&gt;: readValue(json, Pager.class,
     * MyBean.class)<br>
     * json ==&gt; List&lt;Set&lt;Integer&gt;&gt;: readValue(json, List.class,
     * Integer.class)<br>
     * .
     * @param json json字符串
     * @param parametersFor class
     * @param parameterClasses class
     * @param parametrized class
     * @param <T> t
     * @return t
     * @since 2022.0.1
     */
    @SuppressWarnings("deprecation")
    public static <T> T readValue(String json, Class<?> parametrized, Class<?> parametersFor,
            Class<?>... parameterClasses) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        JavaType type;
        if (parameterClasses == null || parameterClasses.length == 0) {
            type = getInstance().getTypeFactory().constructParametrizedType(parametrized, parametrized, parametersFor);
        }
        else {
            type = getInstance().getTypeFactory().constructParametrizedType(parametrized, parametersFor,
                    parameterClasses);
        }

        try {
            return getInstance().readValue(json, type);
        }
        catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @SuppressWarnings("rawtypes")
    public static <T> T readMap(String content, Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        try {
            return getInstance().readValue(content,
                    getInstance().getTypeFactory().constructMapType(mapClass, keyClass, valueClass));
        }
        catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static <T> List<T> readList(String content, Class<?> collectionClass, Class<T> elementClass) {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        try {
            return getInstance().readValue(content, getInstance().getTypeFactory()
                    .constructParametricType((collectionClass != null) ? collectionClass : List.class, elementClass));
        }
        catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public static <T> List<T> readList(String content, Class<T> elementClass) {
        return readList(content, null, elementClass);
    }

    /**
     * 判断是否可以序列化.
     * @param value 对象
     * @return 是否可以序列化
     * @since 2022.0.1
     */
    public static boolean canSerialize(Object value) {
        if (value == null) {
            return true;
        }
        return getInstance().canSerialize(value.getClass());
    }

    public static ObjectMapper getInstance() {
        return JacksonHolder.INSTANCE;
    }

    private static class JacksonHolder {

        private static final ObjectMapper INSTANCE = new JacksonObjectMapper();

    }

    private static class JacksonObjectMapper extends ObjectMapper {

        private static final long serialVersionUID = JcohyFrameworkVersion.SERIAL_VERSION_UID;

        private static final Locale CHINA = Locale.CHINA;

        JacksonObjectMapper() {
            super();
            // 设置地点为中国
            super.setLocale(CHINA);
            // 去掉默认的时间戳格式
            super.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            // 设置为中国上海时区
            super.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            // 序列化时，日期的统一格式
            super.setDateFormat(new SimpleDateFormat(DateTimeUtils.PATTERN_DATETIME, Locale.CHINA));
            // 单引号
            super.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            // 允许JSON字符串包含非引号控制字符（值小于32的ASCII字符，包含制表符和换行符）
            super.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
            super.configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(), true);
            super.findAndRegisterModules();
            // 失败处理
            super.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            super.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 单引号处理
            super.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            // 反序列化时，属性不存在的兼容处理s
            super.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            // 日期格式化
            super.registerModule(new JcohyJavaTimeModule());
            super.findAndRegisterModules();
        }

        @Override
        public ObjectMapper copy() {
            return super.copy();
        }

    }

}
