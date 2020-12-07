package com.payne.week07.mapper.slave;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.payne.week07.model.slave.SlaveUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Payne
 * @date 2020/12/3
 */
@Repository
public interface SlaveUserMapper extends BaseMapper<SlaveUser> {

    /**
     * 自定义查询
     * @param wrapper 条件构造器
     * @return 查询结果
     */
    List<SlaveUser> selectAll(@Param(Constants.WRAPPER) Wrapper<SlaveUser> wrapper);
}
