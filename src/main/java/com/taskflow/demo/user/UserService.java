package com.taskflow.demo.user;


import java.util.ArrayList;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.taskflow.demo.group.GroupDto;

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
    public ResponseEntity<?> joinedGroups( UserDto userDto){
        
        UserEntity user = userRepo.findByEmail(userDto.getEmail()) ;
        // List <GroupDto> joinedGroups = new ArrayList<>();


        if(!userRepo.existsByEmail(userDto.getEmail())){
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
                                .map( group -> new GroupDto(
                                      group.getGroupName(), 
                                      user.getEmail(), 
                                      group.getGroupCode()
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
    
    
}
