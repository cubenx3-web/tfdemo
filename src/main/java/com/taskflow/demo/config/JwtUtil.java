package com.taskflow.demo.config;

import java.security.Key;
import java.util.Date;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
 
    private static final String SECRET = "mysecretkeymysecretkeymysecretkey";

    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());


    public static String tokenGen(String email, Boolean isAdmin){
      
        return Jwts.builder()
            .setSubject(email)
            .claim("admin", isAdmin)
            .setIssuedAt(new Date())
            .setExpiration((new Date(System.currentTimeMillis()+3600000)))
            .signWith(key)
            .compact()    
        ;
    }

    public static String extractEmail(String token){

         try{
           return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
                    
        }catch (Exception e){
            return null;
        }

        
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
