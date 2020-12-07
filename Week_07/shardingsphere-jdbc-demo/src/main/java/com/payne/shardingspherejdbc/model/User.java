package com.payne.shardingspherejdbc.model;

import lombok.Data;

/**
 * 用户实体类
 * @author payne
 */
@Data
public class User {

    private Integer id;
    private String name;
    private Integer age;

    public User() {}

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

}
