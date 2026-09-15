package com.ehmjamiu.learn.service;


import com.ehmjamiu.learn.entity.TodoUser;
import com.ehmjamiu.learn.model.TodoUserPrincipal;
import com.ehmjamiu.learn.repo.TodoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TodoUserDetailsService implements UserDetailsService {

    private TodoUserRepository todoUserRepository;

    @Autowired
    public TodoUserDetailsService(TodoUserRepository todoUserRepository) {
        this.todoUserRepository = todoUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TodoUser user = todoUserRepository.findByUsername(username);
        if(user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User Not Found");
        }

        return new TodoUserPrincipal(user);
    }
}