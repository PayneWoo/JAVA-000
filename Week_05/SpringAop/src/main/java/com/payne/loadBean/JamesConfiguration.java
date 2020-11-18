package com.payne.loadBean;

import com.payne.entity.James;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通过 Java 配置类来装配 bean
 * @author Payne
 * @date 2020/11/17
 */
@Configuration
public class JamesConfiguration {

    @Bean (name = "getJames")
    public James getJames() {
        return new James(23, "James", "Lakers");
    }
}
