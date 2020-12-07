package com.payne.abstractroutingdatasource;

import com.payne.abstractroutingdatasource.service.CourseService;
import com.payne.abstractroutingdatasource.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 测试动态获取数据源
 * @author payne
 */
@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

    private UserService userService;
    private CourseService courseService;
    @Autowired
    public Application(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        // 动态获取数据源
        log.info("从第一个数据源获取用户列表:{}", userService.findAll());
        log.info("从第二个数据源获取课程列表:{}", courseService.findAll());
    }
}
