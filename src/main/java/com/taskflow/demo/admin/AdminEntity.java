package com.taskflow.demo.admin;

import java.util.List;

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
    private List<GroupEntity> groups;

    protected AdminEntity (){}
    
    public AdminEntity (UserEntity admin){

        this.admin = admin;
        this.adminName = this.admin.getUsername();
        this.adminEmail = this.admin.getEmail();

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

    public List<GroupEntity> getGroup(){
        return this.groups;
    }



}
