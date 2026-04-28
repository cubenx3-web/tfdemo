package com.taskflow.demo.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.taskflow.demo.user.UserEntity;
import com.taskflow.demo.user.UserRepo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserRepo userRepo;


    public JwtFilter(UserRepo userRepo){ 
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(
       HttpServletRequest request, 
       HttpServletResponse response, 
       FilterChain filterChain )
       throws ServletException, IOException {

            String authHeader = request.getHeader("Authorization");
            String token = null;
            String email = null;            

            if(authHeader != null && authHeader.startsWith("Bearer ")){
                token = authHeader.substring(7);
                email = JwtUtil.extractEmail(token);
                UserEntity user = userRepo.findByEmail(email);
            
                //VALIDATE TOKENS AND SET AUTHENTICATIONS
                if( JwtUtil.validateToken(token) && email != null && user != null ){
                    
                    UsernamePasswordAuthenticationToken auth = 
                        new UsernamePasswordAuthenticationToken(
                            user.getEmail(), 
                            user.getPassword(), 
                            List.of(new SimpleGrantedAuthority("USER"))
                        );
                    
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println("Authenticated");

                }   
            }

            filterChain.doFilter(request, response);
            System.out.println(request.getServletPath() + " Validated: "+ response.getStatus());
     }
   
   
}


