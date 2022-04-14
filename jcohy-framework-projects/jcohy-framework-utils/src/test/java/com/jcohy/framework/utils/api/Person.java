package com.jcohy.framework.utils.api;

/**
 * 描述: .
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.0.1 3/10/22:18:15
 * @since 2022.0.1
 */
public class Person {

    String name;

    Integer age;

    public String getName() {
        return this.name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return this.age;
    }

    public Person setAge(Integer age) {
        this.age = age;
        return this;
    }

}
