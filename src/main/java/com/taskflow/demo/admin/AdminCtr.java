package com.taskflow.demo.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskflow.demo.user.UserEntity;
import com.taskflow.demo.user.UserRepo;

@RestController
@RequestMapping("api/v1/admin")
public class AdminCtr {

    private final AdminService adminService;
    private final UserRepo userRepo;

    public AdminCtr(AdminService adminService, UserRepo userRepo){
        this.adminService = adminService;
        this.userRepo = userRepo;
    }



    //GET GROUP
    @GetMapping("/groups")
    public ResponseEntity<?> getGroups(){
        return ResponseEntity.ok("my groups");
    }



    //CREATE IS ADMIN (TEMPORARY)
    @GetMapping("isadmin")
    public ResponseEntity<?> isAdmin(@RequestBody AdminDto adminDto){
        
        UserEntity admin = userRepo.findByEmail(adminDto.getEmail());

        return ResponseEntity.ok(adminService.isAdmin(admin));
    }
    

}
