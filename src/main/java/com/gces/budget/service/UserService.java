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

/**
 * Created by minamrosh on 11/19/15.
 */
@Repository
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    @Qualifier("bcryptEncoder")
    private PasswordEncoder passwordEncoder;

    @Autowired

    private UserRepository userRepository;



    public User registerNewUserByDto(UserDTO userDTO){
        User user = new User();
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setPassword(encryptedPassword);
        user.setEmail(userDTO.getEmail());
        user.setAuthority("ROLE_ADMIN");
        user.setEnabled(true);

        userRepository.save(user);

        log.info("New User Created : {}", user);

        return user;
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
}
