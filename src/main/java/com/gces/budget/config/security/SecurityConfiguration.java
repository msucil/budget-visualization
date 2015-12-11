package com.gces.budget.config.security;

import com.gces.budget.security.MongoAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security configuration Class
 *
 * Created by minamrosh on 11/20/15.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private MongoAuthProvider authProvider;

//    @Autowired
//    private CustomUserDetailService userDetailService;

    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                    .antMatchers("/","/test","/test/*","/user/signup")
                    .permitAll()
                    .anyRequest()
                .authenticated().and()
                .formLogin()
                    .loginPage("/user/login")
                    .permitAll()
                .and()
                .logout()
                    .logoutUrl("/user/logout")
                .logoutSuccessUrl("/user/login");


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .authenticationProvider(authProvider);
    }

}
