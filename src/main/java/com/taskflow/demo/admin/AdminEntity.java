package com.taskflow.demo.admin;

import java.util.HashSet;
import java.util.Set;

import com.taskflow.demo.group.GroupEntity;
import com.taskflow.demo.user.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Admin")
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity admin;

    private String adminName;

    private String adminEmail;

    @OneToMany(mappedBy = "admin")
    private Set<GroupEntity> groups = new HashSet<>();

    protected AdminEntity (){}
    
    public AdminEntity (UserEntity admin){

        this.admin = admin;
        this.adminName = this.admin.getUsername();
        this.adminEmail = this.admin.getEmail();

    }



    // SETTER
    public void setAdmin(UserEntity user){
        this.admin = user;
    }



    //GETTERS
    
    public UserEntity getAdmin(){
        return this.admin;
    }

    public String getAdminName(){
        return this.adminName;
    }

    public String getAdminEmail(){
        return this.adminEmail;
    }

    public Set<GroupEntity> getGroup(){
        return this.groups;
    }



}
