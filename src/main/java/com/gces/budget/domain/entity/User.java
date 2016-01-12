package com.gces.budget.domain.entity;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by minamrosh on 11/19/15.
 */
@Document(collection = "user")
public class User {
    @Id
    private String id;

    @NotNull(message = "Username is required")
    @Size(max = 25, min = 5)
    private String username;

    @NotNull(message = "Password required")
    private String password;

    @NotNull
    @Email
    private String email;

    @NotNull
    private boolean enabled;

    @NotNull
    private String authority;

    public User(){}

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return  this.id;
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

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }

    public boolean getEnabled(){
        return this.enabled;
    }

    public void setAuthority(String authority){
        this.authority = authority;
    }

    public String getAuthority(){
        return this.authority;
    }

    @Override
    public String toString(){
        return "User {" +
                "username : " + username + "\n " +
                "password : " + password + "\n" +
                "email : " + email + "\n " +
                "enabled : " + enabled + "\n" +
                "authority : " + authority + "\n}";
    }

}
