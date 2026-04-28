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
import com.taskflow.demo.user.UserDto;
import com.taskflow.demo.user.UserRepo;
import com.taskflow.demo.user.UserService;

@RestController
@RequestMapping("api/v1/auth/register")
@CrossOrigin
public class AuthReg {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AuthReg(UserRepo userRepo, PasswordEncoder passwordEncoder, UserService userService){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    
    @PostMapping
    public ResponseEntity<?> register(@RequestBody UserDto user){
        return userReg(user);
    }

    //          REGISTER USERS
    public ResponseEntity<?> userReg(UserDto user){
        
        Boolean isReg = userService.isUser(user.getEmail());
        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();


        // If USER EXISTS
        if(isReg){
            return ResponseEntity.status(409).body(Map.of(
                "message", "Account already exists"
            ));
        } 

        else{
            if(username != null && email != null && password != null){
                userRepo.save(new UserEntity(
                user.getUsername(),
                user.getEmail(),
                passwordEncoder.encode(user.getPassword())
               ));
        
                return ResponseEntity.status(200).body(Map.of(
                "message","registered"
                ));
            }
            else{
                return ResponseEntity.status(400).body(Map.of(
                    "message", "missing input"
                ));
            }
        }
    }

}
