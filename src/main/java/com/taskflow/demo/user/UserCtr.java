package com.taskflow.demo.user;


import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.demo.config.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("api/v1/user")
@CrossOrigin
public class UserCtr {
    
    
    @GetMapping
    public ResponseEntity<?> hellow(HttpServletRequest request){
        
        String token = request.getHeader("Authorization").substring(7);
        if(JwtUtil.validateToken(token)){
            return  ResponseEntity.ok(
                Map.of("message","valid")
            );
        }

        else {
            return ResponseEntity.ok(


            Map.of("message", "invalid")
            );
        }
    }

    

    
    
}
