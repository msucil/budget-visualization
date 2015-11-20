package com.gces.budget.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User Controller
 * handles related to user management
 * Created by minamrosh on 11/20/15.
 */

@Controller
public class UserController {

    @RequestMapping(value = "/")
    public String home(){
        return "index";
    }

    @RequestMapping(value = "/user/login")
    public String login(){
        return "login";
    }


}
