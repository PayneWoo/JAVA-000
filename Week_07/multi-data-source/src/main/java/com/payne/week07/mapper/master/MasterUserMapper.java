package com.payne.week07.mapper.master;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.payne.week07.model.master.MasterUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Payne
 * @date 2020/12/3
 */
@Repository
public interface MasterUserMapper extends BaseMapper<MasterUser> {

    /**
     * 自定义查询
     * @param wrapper 条件构造器
     * @return 查询结果
     */
    List<MasterUser> selectAll(@Param(Constants.WRAPPER) Wrapper<MasterUser> wrapper);
}
