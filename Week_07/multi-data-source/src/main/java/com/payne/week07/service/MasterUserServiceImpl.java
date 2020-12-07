package com.payne.week07.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.payne.week07.mapper.master.MasterUserMapper;
import com.payne.week07.model.master.MasterUser;
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
public class MasterUserServiceImpl implements MasterUserService {

    private final MasterUserMapper masterUserMapper;
    @Autowired
    public MasterUserServiceImpl(MasterUserMapper masterUserMapper) {
        this.masterUserMapper = masterUserMapper;
    }

    /**
     * 根据id查询用户
     * @param id 用户编号
     * @return 用户信息
     */
    public List<MasterUser> getMasterUserById(int id) {
        return masterUserMapper.selectAll(new QueryWrapper<MasterUser>().eq("id" , id));
    }
}
