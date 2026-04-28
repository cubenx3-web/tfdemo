package com.taskflow.demo.user;

public class UserDto {
    private String username;
    private String email;
    private String password;
    private Long id;


    protected UserDto(){}


    public UserDto(Long id, String username, String email, String password){
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserDto(Long id, String username, String email){
        this.id = id;
        this.username = username;
        this.email = email;
    }
    
    public UserDto(String username, String email){
        this.username = username;
        this.email = email;
    }
    

    //GETTERS
    public Long getId(){
        return this.id;
    }

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
