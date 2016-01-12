package com.gces.budget.domain.dto;

import com.gces.budget.domain.customvalidator.annotation.PasswordMatches;
import com.gces.budget.domain.customvalidator.annotation.ValidEmail;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * UserDTO Representing User Entity
 * Created by minamrosh on 11/19/15.
 */
@PasswordMatches
public class UserDTO {

    @Pattern(regexp = "^[a-z0-9]*$")
    @NotNull(message = "Username is required")
    @NotEmpty(message = "Username is required")
    @Size(min = 5, max = 25, message = "Username should be between 5 and 25 character")
    private String username;

    @NotNull
    @NotEmpty(message = "Password is required")
    @Size(min = 7, max = 100,message = "Password must contain more than 7 character")
    private String password;

    private String matchingPassword;
    

    @NotNull(message = "Email field can not be blank")
    @NotEmpty(message = "Email field can not be blank")
    @ValidEmail(message = "Please enter valid email address")
    private String email;

    private String id;

    public UserDTO(){};

    public UserDTO(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public void setMatchingPassword(String matchingPassword){
        this.matchingPassword = matchingPassword;
    }

    public String getMatchingPassword(){
        return this.matchingPassword;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    @Override
    public String toString(){
        return "UserDTO {\t"+
                "Id : " + this.id + "\n" +
                "username : " + username + "\n"+
                "password : " + password + "\n"+
                "email : " + email + "\n}";
    }

}
