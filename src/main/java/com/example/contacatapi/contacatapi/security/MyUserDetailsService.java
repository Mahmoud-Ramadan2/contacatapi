package com.example.contacatapi.contacatapi.security;

import com.example.contacatapi.contacatapi.model.User;
import com.example.contacatapi.contacatapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email);
        return new org.springframework.security.core.userdetails.User
                (email,user.getPassword(), Collections.emptyList());

    }
}
