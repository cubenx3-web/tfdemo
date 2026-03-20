package com.taskflow.demo.config;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
 
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String tokenGen(String email){
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis()+86400000))
            .signWith(key)
            .compact()    
        ;
    }
    
}
