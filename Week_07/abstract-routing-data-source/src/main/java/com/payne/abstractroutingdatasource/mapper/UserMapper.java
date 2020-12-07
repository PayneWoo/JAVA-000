package com.payne.abstractroutingdatasource.mapper;

import com.payne.abstractroutingdatasource.entity.User;

import java.util.List;

/**
 * @author Kairou Zeng
 */
public interface UserMapper {

    List<User> findAll();
}
