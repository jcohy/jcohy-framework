package com.jcohy.framework.utils.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/10/22:10:22
 * @since 2022.0.1
 */
public class ResultTest {

    private Person person;

    @BeforeEach
    void setUp() {
        this.person = new Person().setAge(23).setName("jcohy");
    }

    @Test
    void successWithData() {
        Result<Person> result = Result.success(this.person);
        assertThat(result.getCode()).isEqualTo(10000);
        assertThat(result.getMessage()).isEqualTo("操作成功");
        assertThat(result.getData().getAge()).isEqualTo(23);
        assertThat(result.getData().getName()).isEqualTo("jcohy");
        assertThat(result.isSuccess()).isTrue();
    }

    @Test
    void successWithHttpStatusCode() {
        Result<Person> result = Result.success(HttpStatusCode.SUCCESS);
        assertThat(result.getCode()).isEqualTo(200);
        assertThat(result.getMessage()).isEqualTo("操作成功");
        assertThat(result.getData()).isNull();
        assertThat(result.isSuccess()).isTrue();
    }

    @Test
    void successWithHttpStatusAndData() {
        Result<Person> result = Result.success(HttpStatusCode.SUCCESS, this.person);
        assertThat(result.getCode()).isEqualTo(200);
        assertThat(result.getMessage()).isEqualTo("操作成功");
        assertThat(result.getData().getAge()).isEqualTo(23);
        assertThat(result.getData().getName()).isEqualTo("jcohy");
        assertThat(result.isSuccess()).isTrue();
    }

    @Test
    void successWithMessage() {
        Result<Person> result = Result.success("成功");
        assertThat(result.getCode()).isEqualTo(10000);
        assertThat(result.getMessage()).isEqualTo("成功");
        assertThat(result.isSuccess()).isTrue();
    }

    @Test
    void successWithCodeAndMessage() {
        Result<Person> result = Result.success(HttpStatusCode.SUCCESS, "成功");
        assertThat(result.getCode()).isEqualTo(200);
        assertThat(result.getMessage()).isEqualTo("成功");
        assertThat(result.isSuccess()).isTrue();
    }

    @Test
    void data() {
        Result<Person> result = Result.data(this.person);
        assertThat(result.getCode()).isEqualTo(10000);
        assertThat(result.getMessage()).isEqualTo("操作成功");
        assertThat(result.getData().getAge()).isEqualTo(23);
        assertThat(result.getData().getName()).isEqualTo("jcohy");
        assertThat(result.isSuccess()).isTrue();
    }

    @Test
    void dataWithMessage() {
        Result<Person> result = Result.data("成功", this.person);
        assertThat(result.getCode()).isEqualTo(10000);
        assertThat(result.getMessage()).isEqualTo("成功");
        assertThat(result.getData().getAge()).isEqualTo(23);
        assertThat(result.getData().getName()).isEqualTo("jcohy");
        assertThat(result.isSuccess()).isTrue();
    }

    @Test
    void dataWithCodeAndMessage() {
        Result<Person> result = Result.data(300, "成功", this.person);
        assertThat(result.getCode()).isEqualTo(300);
        assertThat(result.getMessage()).isEqualTo("成功");
        assertThat(result.getData().getAge()).isEqualTo(23);
        assertThat(result.getData().getName()).isEqualTo("jcohy");
        assertThat(result.isSuccess()).isFalse();
    }

    @Test
    void fail() {
        Result<Person> result = Result.fail("操作失败");
        assertThat(result.getCode()).isEqualTo(99999);
        assertThat(result.getMessage()).isEqualTo("操作失败");
        assertThat(result.isSuccess()).isFalse();
    }

    @Test
    void failWithHttpCode() {
        Result<Person> result = Result.fail(HttpStatusCode.NOT_FOUND);
        assertThat(result.getCode()).isEqualTo(404);
        assertThat(result.getMessage()).isEqualTo("404 没找到请求");
        assertThat(result.isSuccess()).isFalse();
    }

    @Test
    void failWithCodeAndMessage() {
        Result<Person> result = Result.fail(300, HttpStatusCode.NOT_FOUND.getMessage());
        assertThat(result.getCode()).isEqualTo(300);
        assertThat(result.getMessage()).isEqualTo("404 没找到请求");
        assertThat(result.isSuccess()).isFalse();
    }

    @Test
    void failWithHttpCodeAndMessage() {
        Result<Person> result = Result.fail(HttpStatusCode.NOT_FOUND, "参数错误");
        assertThat(result.getCode()).isEqualTo(404);
        assertThat(result.getMessage()).isEqualTo("参数错误");
        assertThat(result.isSuccess()).isFalse();
    }

}
