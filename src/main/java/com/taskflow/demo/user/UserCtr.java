package com.taskflow.demo.user;


import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.demo.group.GroupDto;



@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserCtr {
    
    private final UserService userService;

    public UserCtr(UserService userService){
        this.userService = userService;
    }

    // TEMPORARY TESTING CODE
    @GetMapping
    public ResponseEntity<?> hellow(){
        
            return  ResponseEntity.ok(
                Map.of("message","Hello")
            );
       
    }

    // LEAVE GROUP
    @PutMapping("/leave-group")
    public ResponseEntity<?> leaveGroup(@RequestBody GroupDto groupDto){
        return userService.removeMember(groupDto);
    }
    
    // CANCEL REQUEST
    @PutMapping("/cancel-request")
    public ResponseEntity<?> cancelRequest(@RequestBody GroupDto groupDto){
        return userService.cancelRequest(groupDto);
    }

    // GET JOINED GROUPS
    @GetMapping("/groups")
    public ResponseEntity<?> joinedGroups(@RequestParam String email){
        return userService.joinedGroups(email);
    }
    
    // GET USER SUMMARY
    @GetMapping("/summary")
    public ResponseEntity<?>summary (@RequestParam String email){
        return userService.userSummary(email);
    }
}
