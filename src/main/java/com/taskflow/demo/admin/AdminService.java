package com.taskflow.demo.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.taskflow.demo.group.GroupDto;
import com.taskflow.demo.group.GroupEntity;
import com.taskflow.demo.group.GroupRepo;
import com.taskflow.demo.user.UserEntity;
import com.taskflow.demo.user.UserRepo;

@Service
public class AdminService {
    
    private final AdminRepo adminRepo;
    private final UserRepo userRepo;
    private final GroupRepo groupRepo;

    public AdminService(AdminRepo adminRepo, UserRepo userRepo, GroupRepo groupRepo){
        this.adminRepo = adminRepo;
        this.userRepo = userRepo;
        this.groupRepo = groupRepo;
    }
    

    //CREATE ADMIN
    public Boolean createAdmin(UserEntity user){
        
        if(isAdmin(user)){
            return true;
        }
        else{
            adminRepo.save(new AdminEntity(user));
            return true;
        }
    
    }


    //IS ADMIN
    public Boolean isAdmin(UserEntity user){

        Boolean isAdmin;
        // adminRepo.findByAdmin(user)==null
        // isAdmin = ()? false : true;
        //AdminEntity admin = adminRepo.findByAdmin(user);

        try {
            isAdmin = !(user.getAdmin().getAdminName() == null)? true : false;
            System.out.println("\n\n\n Admin: ");
            System.out.println(user.getAdmin().getAdminName());

        } catch (Exception e) {
            isAdmin = false;
        }
            
        return isAdmin;

    }


    //GET GROUPS
    public ResponseEntity<?> getGroups(AdminDto adminDto){

        UserEntity user = userRepo.findByEmail(adminDto.getEmail());
        AdminEntity admin;
        List <GroupDto> myGroups = new ArrayList<>();
        String message;

        if(!isAdmin(user)){
            message = "invalid Query";
            myGroups.clear();
            return ResponseEntity.status(401).body(
            Map.of(
                
                    "groups", myGroups,
                    "message", message
            )       
         );

        }
        else{
            try{
            admin = adminRepo.findByAdmin(user);    
            myGroups = admin.getGroup()
                                        .stream()
                                        .map( group ->new GroupDto(
                                            group.getGroupName(),
                                            group.getAdmin().getAdminName(),
                                            group.getGroupCode()
                                        )
                                        ).toList();
            message = "success";                        
            
            }catch(Exception e){
                myGroups = null;
                message = "No Groups";
            }



            return ResponseEntity.ok(
                Map.of(
                
                "groups", myGroups,
                "message",message
                )    
            );

        }

        
        
        
       

        
    }


    //DELETE GROUP
    public ResponseEntity<?> deleteGroup(GroupDto groupDto){


        AdminEntity admin = adminRepo.findByAdmin(userRepo.findByEmail(groupDto.getEmail()));

        for ( GroupEntity group: admin.getGroup()) {
            if (group.getGroupName().equals(groupDto.getGroupName()) ){
                
                

                groupRepo.delete(group);
                
                deletAdmin(admin);

                
            }
        }


        return ResponseEntity.ok(
            Map.of(
                "message","Group Deleted"
            )
        );
        
    }


    // DELETE ADMIN
    public void deletAdmin(AdminEntity admin){

        System.out.println("\n\n\n\n number of groups: ");
        System.out.println(admin.getGroup().size());

        if(admin.getGroup().isEmpty() || admin.getGroup().size() <=1 ){
            
            
            UserEntity user = admin.getAdmin();
            user.setAdmin(null);
            userRepo.save(user);
            
            

            //adminRepo.delete(admin);
        }
    }


}
