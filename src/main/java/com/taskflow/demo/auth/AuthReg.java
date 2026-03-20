package com.taskflow.demo.auth;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.demo.user.UserEntity;
import com.taskflow.demo.user.UserRDto;
import com.taskflow.demo.user.UserRepo;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin
public class AuthReg {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthReg(UserRepo userRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    
    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody UserRDto user){
        return userReg(user);
    }

    //          REGISTER USERS
    public ResponseEntity<?> userReg(UserRDto user){
        
        Boolean isReg = (userRepo.findByEmail(user.getEmail()) == null)? true: false;

        // If USER EXISTS
        if(!isReg){
            return ResponseEntity.status(409).body(Map.of(
                "message", "Account already exists"
            ));
        } 

        else{
            userRepo.save(new UserEntity(
            user.getUsername(),
            user.getEmail(),
            passwordEncoder.encode(user.getPassword())
            ));
        
            return ResponseEntity.status(201).body(Map.of(
            "message","registered"
            ));
        }
    }

}
