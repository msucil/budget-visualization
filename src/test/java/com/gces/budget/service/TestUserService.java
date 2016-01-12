package com.gces.budget.service;

import com.gces.budget.BudgetVisualizationApplication;
import com.gces.budget.domain.dto.UserDTO;
import com.gces.budget.domain.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by minamrosh on 1/11/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BudgetVisualizationApplication.class)
public class TestUserService {

    private Logger log = LoggerFactory.getLogger(TestUserService.class);
    @Autowired
    UserService userService;

    @Test
    public void shouldSaveExistingUser(){
        User user = userService.getUserByUsername("msushil");
        log.info("user" + user.toString());
        UserDTO newInfo = new UserDTO();
        newInfo.setId(user.getId());
        newInfo.setUsername(user.getUsername());
        newInfo.setEmail(user.getEmail());
        log.info("user info " + newInfo.toString());

        newInfo.setPassword("msushil");

        User updatedInfo = userService.updateUserInfo(newInfo);
        log.info(updatedInfo.toString());
    }
}
