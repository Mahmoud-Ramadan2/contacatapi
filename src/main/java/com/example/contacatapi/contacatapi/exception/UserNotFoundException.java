package com.example.contacatapi.contacatapi.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message){
         super(message);
    }
}
