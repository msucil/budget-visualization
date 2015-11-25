package com.gces.budget.user;

import com.gces.budget.BudgetVisualizationApplication;
import com.gces.budget.domain.dto.UserDTO;
import com.gces.budget.domain.entity.User;
import com.gces.budget.repository.UserRepository;
import com.gces.budget.service.MailService;
import com.gces.budget.service.UserService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;

/**
 * Test Class for user management
 * Created by minamrosh on 11/21/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BudgetVisualizationApplication.class)
@Profile(value = "dev")
//@SpringApplicationConfiguration
public class UserManagementTesting {

    private final Logger log = LoggerFactory.getLogger(UserManagementTesting.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void contextLoad(){

    }

    @Test
    public void selectAllUsers(){
        List<User> users = userRepository.findAll();
        log.info("user list prepared");
        assertNotNull(users);
        for(User user: users){
            System.out.println(user);
        }
    }

    @Test
    public void findUserByUsername(){
        User user = userRepository.findOneByUsername("msushil");
        assertNotNull(user);
        log.info("User with username {} extracted \n{}", "msushil", user);
        assertTrue(user.getUsername().equalsIgnoreCase("msushil"));
    }

    @Test
    public void findUserByEmail(){
        User user = userRepository.findOneByEmail("np.msushil@gmail.com");
        assertNotNull(user);
        log.info("User with email '{}' found", "np.msushil@gmail.com");
        TestCase.assertTrue(user.getEmail().equals("np.msushil@gmail.com"));
        System.out.println(user);
    }

    @Test
    public void addThenDeleteUser(){
        UserDTO newUser = new UserDTO("testUser","testPassword","testEmail@email.com");
        userService.registerNewUser(newUser);
        User user = userRepository.findOneByUsername(newUser.getUsername());
        assertNotNull(user);
        log.info("New user fetched");
        assertTrue(user.getEmail().equals(newUser.getEmail()));
        log.info("User detail : {}", user);
        userRepository.delete(user);
    }

    @Test
    public void testMail(){
        new MailService().sedMail();
    }

}
