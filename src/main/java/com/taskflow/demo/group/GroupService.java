package com.taskflow.demo.group;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.taskflow.demo.admin.AdminEntity;
import com.taskflow.demo.admin.AdminRepo;
import com.taskflow.demo.admin.AdminService;
import com.taskflow.demo.config.LinkGen;
import com.taskflow.demo.user.UserEntity;
import com.taskflow.demo.user.UserRepo;
import com.taskflow.demo.user.UserService;

@Service
public class GroupService {
    
    private final UserService userService;
    private final GroupRepo groupRepo;
    private final AdminService adminService;
    private final UserRepo userRepo;
    private final AdminRepo adminRepo;
    private final LinkGen linkGen;

    public GroupService (UserService userService, GroupRepo groupRepo, AdminService adminService, UserRepo userRepo, AdminRepo adminRepo, LinkGen linkGen){
        this.userService = userService;
        this.groupRepo = groupRepo;
        this.adminService = adminService;
        this.userRepo = userRepo;
        this.adminRepo = adminRepo;
        this.linkGen = linkGen;
    }


    //CREATE GROUP
    public ResponseEntity<?> createGroup(GroupDto groupDto){


        UserEntity user = userRepo.findByEmail(groupDto.getEmail());
        AdminEntity admin;

        if(!userRepo.existsByEmail(groupDto.getEmail())){
            return ResponseEntity.status(404).body(
                Map.of(
                    "message","Error no user"
                )   
            );
        }

        else{

            if(!adminRepo.existsByAdmin(user)){
                adminService.createAdmin(user);

                admin = adminRepo.findByAdmin(user);
            }

            else{

                admin = adminRepo.findByAdmin(user);
            }

            //CREATE GROUP
            groupRepo.save(new GroupEntity(groupDto.getGroupName(), admin, linkGen.codeGen()));
            
            return ResponseEntity.ok(
                Map.of(
                "message", "Created Group"
                )
            );

            
        }

        
    }

    //DELETE GROUP
    public ResponseEntity<?> deleteGroup(GroupDto groupDto){


        return ResponseEntity.ok("Group Deleted");


    }

    //JOIN MEMBER
    public ResponseEntity<?> joinGroup(GroupDto groupDto){

        if(userService.isUser(groupDto.getEmail())){
            return ResponseEntity.status(401).body( 
                Map.of(
                    "message","not user Alert"
                )
            );
        }

        else if(!groupRepo.existsByGroupCode(groupDto.getGroupCode())){
                return ResponseEntity.status(401).body(
                    Map.of(
                        "message", "Link Expired or Group Doesn't exist"
                    )
                );
        }

        else{

            GroupEntity group = groupRepo.findByGroupCode(groupDto.getGroupCode());
            group.addMember(userRepo.findByEmail(groupDto.getEmail()));
            
            groupRepo.save(group);

            return ResponseEntity.ok(
                Map.of(
                    "message", "joined"
                )
            );

        }
        
       

    }

    //EXIT FROM GROUP REMOVE MEMBER
    public ResponseEntity<?> removeMember(GroupDto groupDto){

        if(!userRepo.existsByEmail(groupDto.getEmail()) || !groupRepo.existsByGroupCode(groupDto.getGroupCode()) ){
            return ResponseEntity.status(401).body(
                Map.of(
                "message","invalid query"
                )
            );
        }
        else{

            GroupEntity group = groupRepo.findByGroupCode(groupDto.getGroupCode());
            group.removeMember(userRepo.findByEmail(groupDto.getEmail()));
            groupRepo.save(group);
        }



        return ResponseEntity.ok(
            Map.of(
                "message","removed"
            )
        );
    }



}
