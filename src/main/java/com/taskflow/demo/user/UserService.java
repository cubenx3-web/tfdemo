package com.taskflow.demo.user;


import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import org.springframework.http.ResponseEntity;
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
        return (userRepo.findByEmail(email) == null)? false: true;
    }

    //JOINED GROUP
    public ResponseEntity<?> joinedGroups( String email){
        
        UserEntity user = userRepo.findByEmail(email) ;
        // List <GroupDto> joinedGroups = new ArrayList<>();


        if(!userRepo.existsByEmail(email)){
           return ResponseEntity.status(401).body(
                Map.of(
                    "message", "invalid query",
                    "joinedGroups", new ArrayList<>()
                )
            );
        }

        else{

           var joinedGroups = user.getJoinedGroups()
                                .stream()
                                .map( group -> Map.of(
                                      "groupName",group.getGroupName(), 
                                      "groupCode",group.getGroupCode()
                                    )
                                   ).toList();


            return ResponseEntity.ok(
            Map.of(
                "message", "success",
                "joinedGroups", joinedGroups,
                "total", joinedGroups.size()
            )
        );                        
        }

    }

    
    // USER SUMMARY
    public ResponseEntity<?> userSummary(String email){
        UserEntity user = userRepo.findByEmail(email) ;
        // List <GroupDto> joinedGroups = new ArrayList<>();


        if(!userRepo.existsByEmail(email)){
           return ResponseEntity.status(401).body(
                Map.of(
                    "message", "invalid query",
                    "joinedGroups", new ArrayList<>()
                )
            );
        }

        else{
        
            

           var joinedGroups = user.getJoinedGroups().size();
           var waitingApproval = user.getPendingJoin().size();            
           var projects = (new Random()).nextInt(100);
           var tasks = (new Random()).nextInt(200); 


            return ResponseEntity.ok(
            Map.of(
                "message", "success",
                "joinedGroups", joinedGroups,
                "waitingApproval", waitingApproval,
                "projects", projects,
                "tasks", tasks
            )
        );                        
        }
    }

    
}
