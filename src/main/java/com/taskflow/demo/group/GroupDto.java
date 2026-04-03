package com.taskflow.demo.group;

public class GroupDto {
    
    private String groupName;
    private String email;


    protected GroupDto(){}

    public GroupDto(String groupName, String email){
        this.groupName = groupName;
        this.email = email;
    }

    
    //GETTERS
    public String getGroupName(){
        return this.groupName;
    }

    public String getEmail(){
        return this.email;
    }

}
