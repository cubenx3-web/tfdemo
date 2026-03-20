package com.taskflow.demo.config;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
 
    private static final String SECRET = "mysecretkeymysecretkeymysecretkey";

    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public static String tokenGen(String email){
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis()+60000))
            .signWith(key)
            .compact()    
        ;
    }

    public static boolean validateToken(String token){

        try{
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

                return true;
        
        }catch (Exception e){
            return false;
        }

        
    }
    
}
