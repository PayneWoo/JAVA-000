package com.payne.shardingspherejdbc.service.impl;


import com.payne.shardingspherejdbc.model.User;
import com.payne.shardingspherejdbc.mapper.UserMapper;
import com.payne.shardingspherejdbc.service.UserService;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author payne
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> list() {
        return userMapper.selectAll();
    }

    @Override
    public String save(User user) {

        userMapper.insert(user);
        return "保存成功";
    }
}
