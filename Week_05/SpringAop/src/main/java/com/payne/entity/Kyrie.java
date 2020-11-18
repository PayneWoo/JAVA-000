package com.payne.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Payne
 * @date 2020/11/18
 */
@Data
@Component
public class Kyrie {

    private int id = 11;

    private String name = "Kyrie Irving";

    private String team = "Brooklyn Nets";

    public Kyrie(){}

    public String say() {
        return "I am " + name + ",from " + team;
    }
}
