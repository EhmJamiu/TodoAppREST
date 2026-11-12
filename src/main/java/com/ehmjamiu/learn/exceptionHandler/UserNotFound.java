package com.ehmjamiu.learn.exceptionHandler;

public class UserNotFound extends RuntimeException{
    public UserNotFound(String message) {
        super(message);
    }
}