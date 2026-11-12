package com.ehmjamiu.learn.exceptionHandler;

public class UserAlreadyExist extends RuntimeException{
    public UserAlreadyExist(String message) {
        super(message);
    }
}