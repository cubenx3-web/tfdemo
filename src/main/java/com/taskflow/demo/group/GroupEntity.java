package com.taskflow.demo.group;


import java.util.List;

import com.taskflow.demo.admin.AdminEntity;
import com.taskflow.demo.user.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "groups")
public class GroupEntity {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private AdminEntity admin;
    

    @ManyToMany
    @JoinTable(
        name = "group_members",
        joinColumns = @JoinColumn(name ="group_id"),
        inverseJoinColumns = @JoinColumn(name = "member_id")

    )
    private List <UserEntity> members;

    
    
    protected GroupEntity(){}

    public GroupEntity( String groupName, AdminEntity admin){
        this.groupName = groupName;
        this.admin = admin;
    }



    // SETTERS
    public void setAdmin(AdminEntity admin){
        this.admin = admin;
    }


    //GETTERS
    public String getGroupName(){
        return this.groupName;
    }    

    public AdminEntity getAdmin(){
        return this.admin;
    }

    public List <UserEntity> getMembers(){
        return this.members;
    }

}
