package com.taskflow.demo.user;

import java.util.List;

import com.taskflow.demo.admin.AdminEntity;
import com.taskflow.demo.group.GroupEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username")
    private String username;

    @Column (unique = true, name = "email")
    private String email;
    
    @Column(name="password")
    private String password;

    @ManyToMany(mappedBy = "members")
    private List <GroupEntity> joinedGroups;


    @OneToOne(mappedBy = "admin")
    private AdminEntity admin;


    protected UserEntity(){}

    public UserEntity(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }


    // SETTER 
    public void setAdmin(AdminEntity admin){
        this.admin = admin;
    }


    // GETTER
    public String getUsername(){
        return this.username;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public Long getId(){
        return this.id;
    }

    public AdminEntity getAdmin(){
        return this.admin;
    }


    

}
