package com.taskflow.demo.group;

public class GroupDto {
    
    private String groupName;
    private String email;
    private String groupCode;
    private Boolean autoApprove;
    private Boolean approved;
    private String memberEmail;

    
    protected GroupDto(){}


    public GroupDto(String groupName, String email, String groupCode){
        this.groupName = groupName;
        this.email = email;
        this.groupCode = groupCode;
    }

    public GroupDto(String groupName, String email,String memberEmail, String groupCode){
        this.groupName = groupName;
        this.memberEmail = memberEmail;
        this.groupCode = groupCode;
        this.email = email;
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

    public Boolean isAutoApprove(){
        return this.autoApprove;
    }

    public String getMemberEmail(){
        return this.memberEmail;
    }

    public Boolean isApproved(){
        return this.approved;
    }

}
