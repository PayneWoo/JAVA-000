package com.payne.shardingspherejdbc.service;



import com.payne.shardingspherejdbc.model.User;

import java.util.List;


/**
 * @author payne
 */
public interface UserService {

    /**
     * 获取所有用户信息
     * @return 用户信息
     */
    List<User> list();


    /**
     * 保存用户信息
     * @param user 用户信息
     * @return 保存结果
     */
    String save(User user);

}