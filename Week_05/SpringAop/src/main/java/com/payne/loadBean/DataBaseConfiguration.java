package com.payne.loadBean;

import com.payne.entity.Davis;
import com.payne.entity.DruidDataSource;
import com.payne.entity.James;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 通过 @Profile 条件化 bean
 * @author Payne
 * @date 2020/11/18
 */
@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class DataBaseConfiguration {

    @Value("${datasource.driverClassName}")
    private String driverClassName;

    @Value("${datasource.url}")
    private String url;

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.password}")
    private String password;

    @Value("${datasource.initialSize}")
    private int initialSize;

    @Value("${datasource.maxActive}")
    private int maxActive;

    @Value("${datasource.maxWait}")
    private int maxWait;

    @Bean(name = "getMySQL")
    @Profile("prod")
    public DruidDataSource getMySQL() {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(driverClassName);
        ds.setUrl(url);
        ds.setUserName(username);
        ds.setPassword(password);
        ds.setInitialSize(initialSize);
        ds.setMaxActive(maxActive);
        ds.setMaxWait(maxWait);
        return ds;
    }

    @Bean(name = "getJames")
    @Profile("dev")
    public James getJames() {
        return new James(6, "James", "Miani Heat");
    }

}