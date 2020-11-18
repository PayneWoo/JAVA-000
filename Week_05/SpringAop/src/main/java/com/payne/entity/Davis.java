package com.payne.entity;

import lombok.Data;

/**
 * @author Payne
 * @date 2020/11/18
 */
@Data
public class Davis {
    private int id;
    private String name;

    public Davis(){}

    public Davis(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
