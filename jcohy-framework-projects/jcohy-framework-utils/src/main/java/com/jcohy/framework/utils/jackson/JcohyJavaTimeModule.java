package com.jcohy.framework.utils.jackson;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import com.jcohy.framework.utils.DateTimeUtils;

/**
 * 描述: .
 *
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 3/9/22:15:57
 * @since 2022.0.1
 */
public class JcohyJavaTimeModule extends SimpleModule {

    public JcohyJavaTimeModule() {
        super(PackageVersion.VERSION);
        this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeUtils.DATETIME_FORMAT));
        this.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeUtils.DATE_FORMAT));
        this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeUtils.TIME_FORMAT));
        this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeUtils.DATETIME_FORMAT));
        this.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeUtils.DATE_FORMAT));
        this.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeUtils.TIME_FORMAT));
    }

}
