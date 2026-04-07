package com.taskflow.demo.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupRepo extends JpaRepository<GroupEntity, Long>{

    @Query( 
            value = "SELECT COUNT(*) FROM groups", 
            nativeQuery = true
        )
    long findGroupCount();
 
    GroupEntity findByGroupCode(String groupCode);
    
    Boolean existsByGroupCode(String groupCode);
    
} 
