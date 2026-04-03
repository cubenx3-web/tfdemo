package com.taskflow.demo.user;


import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepo userRepo;
        
    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }
    

    //VALIDATE USER
    public Boolean isUser(String email){


        //If users exists false
        return (userRepo.findByEmail(email) == null)? true: false;
    }

    


    

}
