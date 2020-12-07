package com.payne.week07.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * 数据源配置类
 * @author Payne
 * @date 2020/12/3
 */
@Slf4j
@Configuration
@PropertySource("classpath:jdbc.properties")
public class DataSourceConfiguration {


    @Bean(name = "master")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource getMysqlMaster() {
        log.info("设置 master 数据源");
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "slave")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource getMysqlSlave() {
        log.info("设置 slave 数据源");
        return DataSourceBuilder.create().build();
    }

}
