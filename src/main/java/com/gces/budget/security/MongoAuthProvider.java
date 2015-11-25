package com.gces.budget.security;

import com.gces.budget.domain.entity.User;
import com.gces.budget.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom Auth Provider using mongodb
 * Created by minamrosh on 11/20/15.
 */
@Service
public class MongoAuthProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String userNotFoundEncodedPassword;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if(authentication.getCredentials() == null) {
            this.logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            String presentedPassword = authentication.getCredentials().toString();
            this.logger.info("input password : "+presentedPassword);
            this.logger.info("user password : "+userDetails.getPassword());
            this.logger.info("encrypted input password :" + passwordEncoder.encode(presentedPassword));
            if(!this.passwordEncoder.matches(presentedPassword,userDetails.getPassword())) {
                this.logger.debug("Authentication failed: password does not match stored value");
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UserDetails loadedUser;
        try{
            User user = userRepository.findOneByUsername(username);
            List<GrantedAuthority> authority = new ArrayList<GrantedAuthority>();
            authority.add(new SimpleGrantedAuthority(user.getAuthority()));

            loadedUser = new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    authority);
        }
        catch (UsernameNotFoundException userNameNofFoundEx){
//            if(authentication.getCredentials() != null) {
//                String presentedPassword = authentication.getCredentials().toString();
//                this.passwordEncoder.matches(this.userNotFoundEncodedPassword, presentedPassword);
//            }
            throw userNameNofFoundEx;
        }
        catch (Exception repositoryProblem){
            throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(),repositoryProblem);
        }

        if(loadedUser == null){
            throw new InternalAuthenticationServiceException("Service Voilation, Retured Null User Detail");
        }
        else {

            return loadedUser;
        }
    }
}
