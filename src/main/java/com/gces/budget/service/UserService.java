package com.gces.budget.service;

//import com.fasterxml.jackson.annotation.JacksonInject;

import com.gces.budget.domain.dto.UserDTO;
import com.gces.budget.domain.entity.User;
import com.gces.budget.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.util.List;

/**
 * Created by minamrosh on 11/19/15.
 */
@Repository
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    @Qualifier("bcryptEncoder")
    private PasswordEncoder passwordEncoder;


    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }



    public User registerNewUser(UserDTO userDTO){
        User user = new User();

        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setPassword(encryptedPassword);
        user.setEmail(userDTO.getEmail());
        user.setAuthority("ROLE_ADMIN");
        user.setEnabled(true);

        User newuser = userRepository.save(user);

        log.debug("User Service: New User Created : {}", newuser);

        return newuser;
    }

    public User updateUserInfo(UserDTO userDTO){
        User user = userRepository.findOne(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encryptedPassword);
        user.setEmail(userDTO.getEmail());

        return userRepository.save(user);
    }

    public User registerNewUser(String username, String password, String email){

        User newUser = new User();

        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setUsername(username);
        newUser.setPassword(encryptedPassword);
        newUser.setEmail(email);
        newUser.setEnabled(true);
        newUser.setAuthority("ROLE_ADMIN");

        userRepository.save(newUser);
        log.debug("New User Created : {}", newUser);

        return newUser;
    }

    public User updateExistingUser(User user){
        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public List<User> listAllUser(){
        return userRepository.findAll();
    }

    public String getCurrentUserId(Principal principal){
//        log.info(username);
        return userRepository.findOneByUsername(principal.getName()).getId();
    }

    public User getUserByUsername(String username){
        return userRepository.findOneByUsername(username);
    }


}
