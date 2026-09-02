package com.ehmjamiu.learn.exceptionHandler;

public class TodoNotFoundException extends RuntimeException{
    public TodoNotFoundException() {
        super();
    }

    public TodoNotFoundException(String message) {
        super(message);
    }

    public TodoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}