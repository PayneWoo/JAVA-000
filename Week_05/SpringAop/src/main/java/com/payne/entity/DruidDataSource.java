package com.payne.entity;

import lombok.Data;

/**
 * @author Payne
 * @date 2020/11/18
 */
@Data
public class DruidDataSource {

    private String driverClassName;
    private String url;
    private String userName;
    private String password;
    private int initialSize;
    private int maxActive;
    private int maxWait;
}
