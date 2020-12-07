package com.payne.week07.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.payne.week07.mapper.slave.SlaveUserMapper;
import com.payne.week07.model.slave.SlaveUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Payne
 * @date 2020/12/6
 */
@Slf4j
@Service
public class SlaveUserServiceImpl implements SlaveUserService {

    private final SlaveUserMapper slaveUserMapper;
    @Autowired
    public SlaveUserServiceImpl(SlaveUserMapper slaveUserMapper) {
        this.slaveUserMapper = slaveUserMapper;
    }

    /**
     * 根据id查询用户
     * @param id 用户编号
     * @return 用户信息
     */
    public List<SlaveUser> getSlaveUserById(int id) {
        return slaveUserMapper.selectAll(new QueryWrapper<SlaveUser>().eq("id" , id));
    }
}
