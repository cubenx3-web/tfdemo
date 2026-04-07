package com.taskflow.demo.group;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/group")
public class GroupCtr {
    
    private final GroupService groupService;
    private final GroupRepo groupRepo;
    
    public GroupCtr(GroupService groupService, GroupRepo groupRepo){
        this.groupService = groupService;
        this.groupRepo = groupRepo;
        
    }


    //CREATE GROUP
    @PostMapping("create")
    public ResponseEntity<?> createGroup(@RequestBody GroupDto groupDto){

        // return ResponseEntity.ok(groupDto);

        return groupService.createGroup(groupDto);
        
    }

    //UPDATE GROUP NAME
    @PutMapping("update")
    public ResponseEntity<?> updateGroupName(){

        return ResponseEntity.ok(
            Map.of(
                "message", "Updated name"
            )
        );

    }
    

    //USER JOIN GROUP
    @PutMapping("join")
    public ResponseEntity<?> joinGroup(@RequestBody GroupDto groupDto){

        return groupService.joinGroup(groupDto);

    }
    

    //DELETE GROUP
    @DeleteMapping
    public ResponseEntity<?> deletGroup(){

        return ResponseEntity.ok(
            Map.of(
                "message", "Group deleted"
            )
        );

    }


    @GetMapping
    public ResponseEntity<?> totalGroups(){
        return ResponseEntity.ok(
            Map.of(
                "message", "success",
                "total", groupRepo.findGroupCount()/100
            )   
        );
    }

}
