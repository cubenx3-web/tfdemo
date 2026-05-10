package com.taskflow.demo.user;


import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.demo.group.GroupService;


@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserCtr {
    
    private final UserService userService;
    private final GroupService groupService;

    public UserCtr(GroupService groupService, UserService userService){
        this.groupService = groupService;
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
    public ResponseEntity<?> leaveGroup(@RequestParam String email, String groupCode){
        return groupService.removeMember(email, groupCode);
    }
    

    // GET JOINED GROUPS
    @GetMapping("/groups")
    public ResponseEntity<?> joinedGroups(@RequestParam String email){
        return userService.joinedGroups(email);
    }
    
    @GetMapping("/summary")
    public ResponseEntity<?>summary (@RequestParam String email){
        return userService.userSummary(email);
    }
}
