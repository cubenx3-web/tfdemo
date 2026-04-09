package com.taskflow.demo.user;


import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.demo.config.JwtUtil;
import com.taskflow.demo.group.GroupDto;
import com.taskflow.demo.group.GroupService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("api/v1/user")
@CrossOrigin
public class UserCtr {
    
    private final UserRepo userRepo;
    private final UserService userService;
    private final GroupService groupService;

    public UserCtr(GroupService groupService, UserRepo userRepo, UserService userService){
        this.userRepo = userRepo;
        this.groupService = groupService;
        this.userService = userService;
    }

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

    // LEAVE GROUP

    @PutMapping("leave-group")
    public ResponseEntity<?> leaveGroup(@RequestBody GroupDto groupDto){
        return groupService.removeMember(groupDto);
    }
    

    // GET JOINED GROUPS
    @GetMapping("joined-group")
    public ResponseEntity<?> joinedGroups(@RequestBody UserRDto user){
        return userService.joinedGroups(user);
    }
    
    
}
