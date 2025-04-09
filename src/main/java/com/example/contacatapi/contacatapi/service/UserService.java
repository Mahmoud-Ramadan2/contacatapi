package com.example.contacatapi.contacatapi.service;

import com.example.contacatapi.contacatapi.exception.UserNotFoundException;
import com.example.contacatapi.contacatapi.model.User;
import com.example.contacatapi.contacatapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email){

        return userRepository.getUserByEmail(email).orElseThrow(()->new UserNotFoundException("user with Email: " + email +" does`t exist"));
    }

    public User addNewUser(User user){
        return userRepository.save(user);

    }

}
