package com.taskflow.demo.user;


import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.taskflow.demo.group.GroupDto;
import com.taskflow.demo.group.GroupEntity;
import com.taskflow.demo.group.GroupRepo;


@Service
public class UserService {

    private final UserRepo userRepo;
    private final GroupRepo groupRepo;
        
    public UserService(UserRepo userRepo, GroupRepo groupRepo){
        this.userRepo = userRepo;
        this.groupRepo = groupRepo;
    }
    

    //  VALIDATE USER
    public Boolean isUser(String email){
        //If users exists false
        return (userRepo.findByEmail(email) == null)? false: true;
    }

    //  GET JOINED GROUP
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
                                      "groupCode",group.getGroupCode(),
                                      "projects", (new Random()).nextInt(100)
                                    )
                                   ).toList();
            var waitingApproval = user.getPendingJoin()
                                   .stream()
                                   .map( group -> Map.of(
                                        "groupName", group.getGroupName(),
                                        "groupCode", group.getGroupCode()
                                   )
                                    
                                   ).toList();

            return ResponseEntity.ok(
            Map.of(
                "message", "success",
                "joinedGroups", joinedGroups,
                "waitingApproval", waitingApproval,
                "total", joinedGroups.size()
            )
        );                        
        }

    }

    //  USER SUMMARY
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

    //  EXIT FROM GROUP REMOVE MEMBER/ LEAVE GROUP
    public ResponseEntity<?> removeMember(GroupDto groupDto){

        String email = groupDto.getEmail();
        String groupCode = groupDto.getGroupCode();

        if(!userRepo.existsByEmail(email) || !groupRepo.existsByGroupCode(groupCode) ){
            return ResponseEntity.status(401).body(
                Map.of(
                "message","invalid query"
                )
            );
        }
        else{

            GroupEntity group = groupRepo.findByGroupCode(groupCode);
            group.removeMember(userRepo.findByEmail(email));
            groupRepo.save(group);
        }



        return ResponseEntity.ok(
            Map.of(
                "message","You have left the Group Group"
            )
        );
    }


    //  CANCEL REQUEST
    public ResponseEntity<?> cancelRequest(GroupDto groupDto){
        String email = groupDto.getEmail();
        String groupCode = groupDto.getGroupCode();

        if(!userRepo.existsByEmail(email) || !groupRepo.existsByGroupCode(groupCode) || !groupRepo.findByGroupCode(groupCode).getPendingRequest().contains(userRepo.findByEmail(email)) ){
            return ResponseEntity.status(401).body(
                Map.of(
                "message","invalid query"
                )
            );
        }
        else{

            GroupEntity group = groupRepo.findByGroupCode(groupCode);
            group.removePendingRequest(userRepo.findByEmail(email));
            groupRepo.save(group);
            
            
            
            return ResponseEntity.ok(
                Map.of(
                    "message","Request Cancelled"
                )
            );
        }
    }
    
}
