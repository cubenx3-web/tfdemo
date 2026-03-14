package com.taskflow.demo.user;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
@CrossOrigin
public class UserCtr {
    
    private UserService userService;

    private UserCtr(UserService userService){
        this.userService = userService;
    }


    @PostMapping("register")
    public Map<String, String> register(@RequestBody UserRDto user){
        return userService.userReg(user);
    }
    
}
