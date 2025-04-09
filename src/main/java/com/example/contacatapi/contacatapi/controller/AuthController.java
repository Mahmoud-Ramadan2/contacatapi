package com.example.contacatapi.contacatapi.controller;

import com.example.contacatapi.contacatapi.DTO.SignInRequest;
import com.example.contacatapi.contacatapi.DTO.SignUpRequest;
import com.example.contacatapi.contacatapi.model.User;
import com.example.contacatapi.contacatapi.security.JWTUtil;
import com.example.contacatapi.contacatapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> loginHandler(@Valid @RequestBody SignInRequest signInRequest) {

        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword());
            Authentication authentication =
                    authManager.authenticate(authenticationToken);
            String token = jwtUtil.generateToken(signInRequest.getEmail());

            return ResponseEntity.ok(Collections.singletonMap("jwt-token", token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid Login Credentials"));
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> registerHandler(@Valid @RequestBody SignUpRequest signUpRequest) {

        String encodedPass = passwordEncoder.encode(signUpRequest.getPassword());
        signUpRequest.setPassword(encodedPass);
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        userService.addNewUser(user);

        String token = jwtUtil.generateToken(signUpRequest.getEmail());

        return ResponseEntity.ok(Collections.singletonMap("User Created successfully with token ", token));
    }
}
