package com.payne.loadBean;

import com.payne.entity.TestCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author Payne
 * @date 2020/11/18
 */
@Configuration
public class ConditionBeanConfiguration {

    /**
     * 当 TestCondition 条件成立时，就会创建 bean: getConditionBean
     *         注解 @Conditional 可以用在带有 @Bean 注解的方法上，如果给定的条件计算结果为 true,就会创建这个 bean
     */
    @Bean(value = "getConditionBean")
    @Conditional(TestCondition.class)
    public ConditionBeanConfiguration getConditionBean() {
        return new ConditionBeanConfiguration();
    }
}
