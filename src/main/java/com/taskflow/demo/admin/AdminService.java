package com.taskflow.demo.admin;

import org.springframework.stereotype.Service;

import com.taskflow.demo.user.UserEntity;

@Service
public class AdminService {
    
    private final AdminRepo adminRepo;

    public AdminService(AdminRepo adminRepo){
        this.adminRepo = adminRepo;
    }
    

    //CREATE ADMIN
    public Boolean createAdmin(UserEntity user){
        
        if(isAdmin(user)){
            return true;
        }
        else{
            adminRepo.save(new AdminEntity(user));
            return true;
        }
    
    }




    //IS ADMIN
    public Boolean isAdmin(UserEntity user){

        Boolean isAdmin = (adminRepo.findByAdmin(user)==null)? false : true;

        return isAdmin;

    }


    //GET GROUP

}
