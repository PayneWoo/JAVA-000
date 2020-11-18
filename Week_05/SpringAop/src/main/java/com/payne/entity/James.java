package com.payne.entity;

import lombok.Data;

/**
 * @author Payne
 * @date 2020/11/17
 */
@Data
public class James {

    private int id;

    private String name;

    private String team;

    public James() {}

    public James(int id, String name, String team) {
        this.id = id;
        this.name = name;
        this.team = team;
    }
}
