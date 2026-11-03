package com.ehmjamiu.learn.exceptionHandler;

public class CustomBadRequest extends RuntimeException{
    public CustomBadRequest(String message) {
        super(message);
    }
}