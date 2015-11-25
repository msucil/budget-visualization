package com.gces.budget.domain.dto;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * UserDTO Representing User Entity
 * Created by minamrosh on 11/19/15.
 */
public class UserDTO {

    @Pattern(regexp = "^[a-z0-9]*$")
    @NotNull
    @Size(min = 5, max = 25)
    private String username;

    @NotNull
    @Size(min = 7, max = 100)
    private String password;

    @Email
    @NotNull
    private String email;

    public UserDTO(){};

    public UserDTO(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
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

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    @Override
    public String toString(){
        return "UserDTO {\t"+
                "username : " + username + "\n"+
                "password : " + password + "\n"+
                "email : " + email + "\n}";
    }

}
