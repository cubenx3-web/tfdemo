package com.taskflow.demo.admin;

import java.util.ArrayList;
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
        
        if(adminRepo.existsByAdmin(user)){
            return true;
        }
        else{
            adminRepo.save(new AdminEntity(user));
            return true;
        }
    
    }


    //GET GROUPS
    public ResponseEntity<?> getGroups(AdminDto adminDto){

        UserEntity user = userRepo.findByEmail(adminDto.getEmail());
        AdminEntity admin = adminRepo.findByAdmin(user); ;
        String message = null;
        Long total = null;
        

        if(!adminRepo.existsByAdmin(user)){
            message = "invalid Query";
            return ResponseEntity.status(401).body(
            Map.of(
                
                    "groups", new ArrayList<>(),
                    "message", message
            )       
         );

        }
        else{
            try{
            var myGroups = admin.getGroup()
                                        .stream()
                                        .map( group -> Map.of(
                                           "id", group.getId(),
                                           "groupName", group.getGroupName(),
                                           "groupCode", group.getGroupCode(),
                                           "totalMembers", group.getMembers().size(),
                                           "pendingRequest", group.getPendingRequest().size(),
                                           "autoApprove", group.isAutoApprove()
                                            )
                                        )
                                        .toList();

            message = "success";                        
            total = (long) myGroups.size();
            
            return ResponseEntity.ok(
                Map.of(
                
                "groups", myGroups,
                "message",message,
                "total", total
                )    
            );


            }catch(Exception e){
                var myGroups = new ArrayList<>();
                message = "No Groups";

                return ResponseEntity.ok(
                    Map.of(
                
                    "groups", myGroups,
                    "message",message
                )    
                );
            }



            

        }

        
    }


    // SET AUTO APPROVE
    public ResponseEntity<?> setAutoApprove(GroupDto groupDto){
        GroupEntity group = groupRepo.findByGroupCode(groupDto.getGroupCode());
        AdminEntity admin = adminRepo.findByAdminEmail(groupDto.getEmail());

        if(group !=null && admin !=null && group.getAdmin().equals(admin)){

            group.setAutoApprove(groupDto.isAutoApprove());
            groupRepo.save(group);
            String state = (groupDto.isAutoApprove())?"On": "Off";

            return ResponseEntity.ok(
                Map.of(
                    "message","Auto Approve " + state
                )
            );
        }
        else{
            return ResponseEntity.status(404).body(
                Map.of("message", "Error")
            );
        }



    }

    // APPROVE OR REJECT REQUESTS
    public ResponseEntity<?> handlePendingRequests(GroupDto groupDto){

        GroupEntity group = groupRepo.findByGroupCode(groupDto.getGroupCode());
        AdminEntity admin = adminRepo.findByAdminEmail(groupDto.getEmail());
        UserEntity member = userRepo.findByEmail(groupDto.getMemberEmail());

        if(
            group != null &&
            admin != null &&
            member != null &&
            group.getAdmin().getAdminEmail().equals(admin.getAdminEmail()) &&
            group.getPendingRequest().contains(member)
        ){

            group.removePendingRequest(member);
            if(groupDto.isApproved()){
                group.addMember(member);
            }

            groupRepo.save(group);
            return ResponseEntity.ok(
                Map.of(
                    "message", "Member " + ((groupDto.isApproved()) ? "Accepted":"Rejected" )
                )
            );


        }

        else{
            return ResponseEntity.status(404).body(
                Map.of("message","Error")
            );
        }

        
 
    }

    // GET MEMBERS
    public ResponseEntity<?> getGroupMembers(GroupDto groupDto){

        GroupEntity group = groupRepo.findByGroupCode(groupDto.getGroupCode());

        if( group != null && group.getAdmin().getAdminEmail().equals(groupDto.getEmail())){
            
            var members = group.getMembers().stream().map( g -> Map.of(
                     
                       "username", g.getUsername(),
                        "email", g.getEmail(),
                            "id", g.getId()
                    )
                    

            ).toList();


            return ResponseEntity.ok(
                Map.of(
                    "message" , "sucess",
                    "members" , members,
                    "total", members.size()
                )
            );
        }

        else{
            return ResponseEntity.status(404).body(
                Map.of("message","Error")
            );
        }


    }

     // GET PENDING REQUESTS
    public ResponseEntity<?> getGroupPendingRequest(GroupDto groupDto){

        GroupEntity group = groupRepo.findByGroupCode(groupDto.getGroupCode());

        if( group != null && group.getAdmin().getAdminEmail().equals(groupDto.getEmail())){
            
            var members = group.getPendingRequest().stream().map( g -> Map.of(
                     
                       "username", g.getUsername(),
                        "email", g.getEmail(),
                            "id", g.getId()
                    )
                    

            ).toList();


            return ResponseEntity.ok(
                Map.of(
                    "message" , "sucess",
                    "members" , members,
                    "total", members.size()
                )
            );
        }

        else{
            return ResponseEntity.status(404).body(
                Map.of("message","Error")
            );
        }


    }

    //DELETE GROUP
    public ResponseEntity<?> deleteGroup(GroupDto groupDto){


        AdminEntity admin = adminRepo.findByAdmin(userRepo.findByEmail(groupDto.getEmail()));
        GroupEntity group = groupRepo.findByGroupCode(groupDto.getGroupCode());

    
        
        if (admin != null &&  group != null && admin.getAdminEmail() == group.getAdmin().getAdminEmail() ){
                          
            groupRepo.delete(group);
            deletAdmin(admin);
            
            return ResponseEntity.ok(
                Map.of(
                    "message","Group Deleted"
                )
            );
         
        }
        else{
            return ResponseEntity.status(400).body(
            Map.of(
                "message","Error"
            )
        );
        }
        
    }


    // DELETE ADMIN
    public void deletAdmin(AdminEntity admin){

        System.out.println("\n\n\n\n number of groups: ");
        System.out.println(admin.getGroup().size());

        if(admin.getGroup().isEmpty() || admin.getGroup().size() <=1 ){
            
            adminRepo.delete(admin);

        }
    }


    // UPDATE GROUPNAME
    public ResponseEntity<?> updateGroupName(GroupDto groupDto){

        GroupEntity group = groupRepo.findByGroupCode(groupDto.getGroupCode());
        AdminEntity admin = adminRepo.findByAdminEmail(groupDto.getEmail());
        
        if(group != null && groupDto.getGroupName()!=null && admin !=null && group.getAdmin().equals(admin)){
            group.setGroupName(groupDto.getGroupName());
            groupRepo.save(group);

            return ResponseEntity.ok(
                Map.of(
                    "message","Group Name Updated"
                )
            );
        }

        else{
            return ResponseEntity.status(404).body(
                Map.of("message","Error")
            );
        }

        
    }


}
