package com.gces.budget.service;

import org.springframework.security.access.annotation.Secured;

/**
 * Created by minamrosh on 11/20/15.
 */
public interface TestInterface {

    @Secured(value = "authenticated")
    public void sayHello();
}
