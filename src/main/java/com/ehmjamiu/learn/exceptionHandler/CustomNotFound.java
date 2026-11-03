package com.ehmjamiu.learn.exceptionHandler;

public class CustomNotFound extends RuntimeException{
    public CustomNotFound(String message) {
        super(message);
    }
}