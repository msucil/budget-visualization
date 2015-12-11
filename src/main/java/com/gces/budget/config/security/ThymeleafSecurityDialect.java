package com.gces.budget.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;


/**
 * Dialect Configuration for accessing
 * security object directly in thymeleaf template
 * Created by minamrosh on 12/9/15.
 */
@Configuration
public class ThymeleafSecurityDialect {

    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }


}
