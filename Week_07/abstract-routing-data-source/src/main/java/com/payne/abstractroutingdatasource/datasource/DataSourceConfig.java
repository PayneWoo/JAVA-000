package com.payne.abstractroutingdatasource.datasource;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置类
 * @author payne
 */
@Slf4j
@Configuration
@PropertySource("classpath:application.yml")
@MapperScan(basePackages = "com.payne.abstractroutingdatasource.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {

    @Bean(name = "firstDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.first")
    public DataSource getFirstDataSource() {
        log.info("第一个数据源");
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "secondDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.second")
    public DataSource getSecondDataSource() {
        log.info("第二个数据源");
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDataSource")
    public DynamicDataSource setDataSource(@Qualifier("firstDataSource") DataSource firstDataSource,
                                           @Qualifier("secondDataSource") DataSource secondDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseType.FIRST_MYSQL, firstDataSource);
        targetDataSources.put(DatabaseType.SECOND_MYSQL, secondDataSource);

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 数据源集合
        dynamicDataSource.setTargetDataSources(targetDataSources);
        // 设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(firstDataSource);

        log.info("动态获取数据源, 数据源集合是 : {}", targetDataSources);
        return dynamicDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:mybatis/mappers/*.xml"));

        log.info("SqlSessionFactoryBean!");
        return sqlSessionFactoryBean.getObject();
    }
}
