package com.taskflow.demo.admin;


public class AdminDto {

    private String email;
    
    protected AdminDto(){

    }

    public AdminDto(String email){
        this.email = email;
    }


    public String getEmail(){
        return this.email;
    }

}
