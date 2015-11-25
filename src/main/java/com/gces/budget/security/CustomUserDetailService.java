package com.gces.budget.security;

import com.gces.budget.domain.entity.User;
import com.gces.budget.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom User Detail Service
 * Used to retrieve user credidentals
 * while authenticating user
 * Created by minamrosh on 11/20/15.
 */
public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOneByUsername(username);
        if(user != null){
            List <GrantedAuthority> authority = new ArrayList<GrantedAuthority>();
            authority.add(new SimpleGrantedAuthority(user.getAuthority()));

            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(),authority);
        }

        throw new UsernameNotFoundException("User '"+username+"' not found!");
    }
}
