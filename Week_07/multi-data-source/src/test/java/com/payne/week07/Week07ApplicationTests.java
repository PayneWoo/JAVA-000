package com.payne.week07;

import com.payne.week07.model.master.MasterUser;
import com.payne.week07.model.slave.SlaveUser;
import com.payne.week07.service.MasterUserServiceImpl;
import com.payne.week07.service.SlaveUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class Week07ApplicationTests {

    @Autowired
    MasterUserServiceImpl masterUserService;

    @Autowired
    SlaveUserServiceImpl slaveUserService;

    /**
     * 【动态切换数据源版本 1.0】
     *      多数据源-配置多个数据源（master，slave）.不同的service注入不同的数据源。（此处注入 master 数据源）
     *
     */
    @Test
    void queryMasterUser() {
        List<MasterUser> list = masterUserService.getMasterUserById(1);
        System.out.println(list);
    }


    /**
     * 【动态切换数据源版本 1.0】
     *      多数据源-配置多个数据源（master，slave）.不同的service注入不同的数据源。（此处注入 slave 数据源）
     *
     */
    @Test
    void querySlaveUser() {
        List<SlaveUser> list = slaveUserService.getSlaveUserById(1);
        System.out.println(list);
    }

}
