package com.payne.loadBean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * 通过 @ComponentScan 启动了组件扫描
 *     注解 @ComponentScan 默认扫描与配置类相同的包，查找带有 @Component 注解的类，并会在 Spring 中自动为其创建一个 bean
 * @author Payne
 * @date 2020/11/18
 */
@Configuration
@ComponentScan(value = "com.payne.entity")
public class KyrieConfiguration {


}
