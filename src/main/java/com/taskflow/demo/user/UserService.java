package com.taskflow.demo.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepo userRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }
    
    //          REGISTER USERS
    public Map<String, String> userReg(UserRDto user){
        
        Boolean isReg = (userRepo.findByEmail(user.getEmail()) == null)? true: false;

        if(!isReg){
            return Map.of(
                "Status", "exists"
            );
        } 

        else{
            userRepo.save(new UserEntity(
            user.getUsername(),
            user.getEmail(),
            passwordEncoder.encode(user.getPassword())
            ));
        
            return Map.of(
            "Status","registered"
            );
        }
    }


    

}
