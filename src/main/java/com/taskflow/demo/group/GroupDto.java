package com.taskflow.demo.group;

public class GroupDto {
    
    private String groupName;
    private String email;
    private String groupCode;

    protected GroupDto(){}


    public GroupDto(String groupName, String email, String groupCode){
        this.groupName = groupName;
        this.email = email;
        this.groupCode = groupCode;
    }

    
    //GETTERS
    public String getGroupName(){
        return this.groupName;
    }

    public String getEmail(){
        return this.email;
    }

    public String getGroupCode(){
        return this.groupCode;
    }

}
