package com.payne.entity;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 条件
 * @author Payne
 * @date 2020/11/18
 */
public class TestCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {

        Environment environment = conditionContext.getEnvironment();
        String property = environment.getProperty("os.name");
        if (property.equals("Mac OS X")) {
            System.out.println("条件成立，开始创建 bean ");
            return true;
        }
        return false;
//        return environment.containsProperty("getConditionBean");
    }
}
