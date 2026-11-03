package com.ehmjamiu.learn.controller;

import com.ehmjamiu.learn.dto.AuthResponse;
import com.ehmjamiu.learn.dto.LoginRequest;
import com.ehmjamiu.learn.dto.RegisterRequest;
import com.ehmjamiu.learn.entity.TodoUser;
import com.ehmjamiu.learn.service.UserService;
import com.ehmjamiu.learn.util.ResponseHandler;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest user, HttpServletResponse response) {
        AuthResponse authResponse =  userService.register(user, response);
        return ResponseHandler.handleResponse("response", HttpStatus.CREATED, "User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest user, HttpServletResponse response){
        AuthResponse authResponse=userService.login(user, response);
        return ResponseHandler.handleResponse(authResponse, HttpStatus.OK, "User successfully login");
    }

}