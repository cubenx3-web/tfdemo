package com.taskflow.demo.user;

public class UserRDto {
    private String username;
    private String email;
    private String password;
    
    protected UserRDto(){

    }

    public UserRDto(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }


    // GETTERS
    public String getUsername(){
        return this.username;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

}
