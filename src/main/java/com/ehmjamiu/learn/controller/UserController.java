package com.ehmjamiu.learn.controller;

import com.ehmjamiu.learn.entity.TodoUser;
import com.ehmjamiu.learn.service.UserService;
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
    public TodoUser register(@RequestBody TodoUser user) {
        return userService.register(user);
    }
}