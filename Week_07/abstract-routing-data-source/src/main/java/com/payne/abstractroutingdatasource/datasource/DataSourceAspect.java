package com.payne.abstractroutingdatasource.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 定义动态切换数据源的aop
 * @author Payne
 */
@Slf4j
@Aspect
@Component
public class DataSourceAspect {

    @Before("execution(* com.payne.abstractroutingdatasource.service.UserService.*(..))")
    public void setFirstDataSource() {
        log.info("设置新数据源:{}", DatabaseType.FIRST_MYSQL.name());
        DataSourceHolder.setDatabaseType(DatabaseType.FIRST_MYSQL);
    }

    @Before("execution(* com.payne.abstractroutingdatasource.service.CourseService.*(..))")
    public void setSecondDataSource() {
        log.info("设置新数据源:{}", DatabaseType.SECOND_MYSQL.name());
        DataSourceHolder.setDatabaseType(DatabaseType.SECOND_MYSQL);
    }
}
