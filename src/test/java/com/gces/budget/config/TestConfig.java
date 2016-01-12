package com.gces.budget.config;

import com.gces.budget.service.UserService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by minamrosh on 1/11/16.
 */
@Configuration
@SpringBootApplication
public class TestConfig {
    @Bean
    public UserService getUserService(){
        return new UserService();
    }
}
