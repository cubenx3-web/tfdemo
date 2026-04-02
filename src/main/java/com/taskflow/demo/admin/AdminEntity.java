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
    @JoinColumn(name = "Admin_User_id")
    private UserEntity user;

    @OneToMany(mappedBy = "admin")
    private List<GroupEntity> groups;
    
}
