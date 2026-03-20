package com.taskflow.demo.auth;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.demo.config.JwtUtil;
import com.taskflow.demo.user.UserEntity;
import com.taskflow.demo.user.UserRDto;
import com.taskflow.demo.user.UserRepo;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin
public class AuthLogin {
    
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthLogin(UserRepo userRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    

    //  LOGIN CONTROLLER
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody UserRDto userDto){
        
        return  userLogin(userDto);
    }


    // Login Authentication
    public ResponseEntity<?> userLogin(UserRDto userDto){

        UserEntity user = userRepo.findByEmail(userDto.getEmail());

        //Is not found
        if(user == null){
            return ResponseEntity.status(404).body(Map.of("message","User doesn't exist"));
        }

        //Is invalid password
        else if(
            !passwordEncoder.matches(userDto.getPassword(), user.getPassword())
        ){

            return ResponseEntity.status(401).body(Map.of("message","Invalid password"));
        }

        //valid
        else{

            String token = JwtUtil.tokenGen(user.getEmail());
            
            return ResponseEntity.ok(
                    Map.of(
                        "message","success",
                        "token",  token
                    )
                    );
            
        }

    }

}
