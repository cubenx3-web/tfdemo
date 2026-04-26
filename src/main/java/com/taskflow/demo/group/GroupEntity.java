package com.taskflow.demo.group;


import java.util.HashSet;
import java.util.Set;

import com.taskflow.demo.admin.AdminEntity;
import com.taskflow.demo.user.UserEntity;

import jakarta.persistence.Column;
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

    @Column(name ="group_code", unique = true)
    private String groupCode;

    @ManyToOne
    @JoinColumn(name = "admin")
    private AdminEntity admin;
    
    @ManyToMany
    @JoinTable(
        name = "pending_request",
        joinColumns = @JoinColumn(name = "group_id"),
        inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Set <UserEntity> pendingRequest  = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "group_members",
        joinColumns = @JoinColumn(name ="group_id"),
        inverseJoinColumns = @JoinColumn(name = "member_id")
    )

    private Set <UserEntity> members = new HashSet<>();

    
    
    protected GroupEntity(){}

    public GroupEntity( String groupName, AdminEntity admin, String groupCode){
        this.groupName = groupName;
        this.admin = admin;
        this.groupCode = groupCode;
    }

    
    // SETTERS
    public void setAdmin(AdminEntity admin){
        this.admin = admin;
    }

    public void addMember(UserEntity member){
        this.members.add(member);
    }

    public void removeMember(UserEntity member){
        this.members.remove(member);
    }


    //GETTERS
    public String getGroupName(){
        return this.groupName;
    }    

    public AdminEntity getAdmin(){
        return this.admin;
    }

    public Set <UserEntity> getMembers(){
        return this.members;
    }

    public String getGroupCode(){
        return this.groupCode;
    }

}
