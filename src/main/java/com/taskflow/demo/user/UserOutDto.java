package com.taskflow.demo.user;

public class UserOutDto {
    private Long id;
    private String username;
    private String email;

    protected UserOutDto(){}

    public UserOutDto(Long id, String username, String email){
        this.id = id;
        this.username = username;
        this.email = email;
    }
    
    //  GETTER
    public Long getId(){
        return this.id;
    }

    public String getUsername(){
        return this.username;
    }   

    public String getEmail(){
        return this.email;
    }

}
