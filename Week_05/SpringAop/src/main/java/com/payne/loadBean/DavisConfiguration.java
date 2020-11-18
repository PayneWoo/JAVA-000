package com.payne.loadBean;

import com.payne.entity.Davis;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Payne
 * @date 2020/11/18
 */
@Configuration
public class DavisConfiguration {

    @Value("#{config.name}")
    public static String name;

    public String say () {
        return name;
    }

    @Bean(name = "getDavisFromProd")
    @Profile("prod")
    public Davis getDavisFromProd() {
        return new Davis(23, "AD");
    }


    @Bean(name = "getDavisFromDev")
    @Profile("dev")
    public Davis getDavisFromDev() {
        return new Davis(23, "AD钙");
    }
}
