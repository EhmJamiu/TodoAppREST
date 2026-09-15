package com.ehmjamiu.learn.service;

import com.ehmjamiu.learn.entity.TodoUser;
import com.ehmjamiu.learn.repo.TodoUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final TodoUserRepository todoUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(TodoUserRepository todoUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.todoUserRepository = todoUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }



    public TodoUser register(TodoUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return todoUserRepository.save(user);
    }


}