package com.taskflow.demo.config;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import com.taskflow.demo.group.GroupRepo;

@Service
public class LinkGen {
    
    private String codeChar  = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    private long setLength = 5;

    private final GroupRepo groupRepo;

    public LinkGen(GroupRepo groupRepo){
        this.groupRepo  = groupRepo;
    }
    
    private final SecureRandom rand = new SecureRandom();

    public String codeGen(){
        int length = (int)(setLength + (groupRepo.count() / 100));
        StringBuilder code = new StringBuilder(length);

        do{

            for(int i = 0; i < (int)length; i++){
                code.append(codeChar.charAt(rand.nextInt(codeChar.length())));   
            }
        
        }while(groupRepo.existsByGroupCode(code.toString()));
        
        return code.toString();     

    }
 





}
