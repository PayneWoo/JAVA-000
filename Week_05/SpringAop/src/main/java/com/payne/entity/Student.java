package com.payne.entity;

import lombok.Data;

/**
 * @author Payne
 * @date 2020/11/17
 */
@Data
public class Student {

    private int id;

    private String name;

    public Student() {};

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
