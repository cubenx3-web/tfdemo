package com.taskflow.demo.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskflow.demo.user.UserEntity;

public interface AdminRepo extends JpaRepository<AdminEntity, Long>{

    AdminEntity findByAdminEmail(String email);
    AdminEntity findByAdmin(UserEntity user);
} 
