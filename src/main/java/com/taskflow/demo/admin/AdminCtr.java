package com.taskflow.demo.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.demo.group.GroupDto;

@RestController
@RequestMapping("api/v1/admin")
public class AdminCtr {

    private final AdminService adminService;
    private final AdminRepo adminRepo;

    public AdminCtr(AdminService adminService, AdminRepo adminRepo){
        this.adminService = adminService;
        this.adminRepo = adminRepo;
    }   


    //GET GROUP
    @GetMapping("groups")
    public ResponseEntity<?> getGroups(@RequestBody AdminDto adminDto){
        return adminService.getGroups(adminDto);
    }

    @DeleteMapping("group")
    public ResponseEntity<?> deletGroup(@RequestBody GroupDto groupDto){
        return adminService.deleteGroup(groupDto);
    }

    //CREATE IS ADMIN (TEMPORARY)
    @GetMapping("isadmin")
    public ResponseEntity<?> isAdmin(@RequestBody AdminDto adminDto){
        
        return ResponseEntity.ok(adminRepo.existsByAdminEmail(adminDto.getEmail()));
    }
    

}
