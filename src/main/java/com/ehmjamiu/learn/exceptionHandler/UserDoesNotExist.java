package com.ehmjamiu.learn.exceptionHandler;

public class UserDoesNotExist extends RuntimeException{
    public UserDoesNotExist(String message) {
        super(message);
    }
}