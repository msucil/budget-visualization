package com.gces.budget.config.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Bean Configuration Class for the application
 * Created by minamrosh on 11/20/15.
 */

@Configuration
public class BeanConfiguration {

    @Bean(name = "bcryptEncoder")
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
