package com.payne.abstractroutingdatasource.service;

import com.payne.abstractroutingdatasource.entity.User;
import com.payne.abstractroutingdatasource.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Kairou Zeng
 */
@Component
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> findAll() {
        return userMapper.findAll();
    }
}
