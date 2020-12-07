package com.payne.shardingspherejdbc.mapper;

import com.payne.shardingspherejdbc.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author payne
 */
@Mapper
public interface UserMapper {

    /**
     * 插入一条记录
     *
     * @param record 实体对象
     * @return 更新条目数
     */
    int insert(User record);

    /**
     * 获取所有用户
     */
    List<User> selectAll();

}