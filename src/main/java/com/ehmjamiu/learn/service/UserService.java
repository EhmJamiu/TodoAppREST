package com.ehmjamiu.learn.service;

import com.ehmjamiu.learn.dto.AuthResponse;
import com.ehmjamiu.learn.dto.LoginRequest;
import com.ehmjamiu.learn.dto.RegisterRequest;
import com.ehmjamiu.learn.dto.UserInfo;
import com.ehmjamiu.learn.entity.TodoUser;
import com.ehmjamiu.learn.exceptionHandler.UserAlreadyExist;
import com.ehmjamiu.learn.exceptionHandler.UserNotFound;
import com.ehmjamiu.learn.repo.TodoUserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final TodoUserRepository todoUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenService tokenService;

    public UserService(TodoUserRepository todoUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, TokenService tokenService) {
        this.todoUserRepository = todoUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.tokenService = tokenService;
    }



    public AuthResponse register(@Valid RegisterRequest user, HttpServletResponse response) {
        Optional<TodoUser> existingUser = todoUserRepository.findTodoUserByUsername(user.username());
        if (existingUser.isPresent()){
            throw new UserAlreadyExist(existingUser.get().getUsername() + "is an existing user.");
        }

        TodoUser newUser = new TodoUser();

        newUser.setUsername(user.username());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.password()));

        todoUserRepository.save(newUser);

        String accessToken;
        accessToken = jwtService.generateAccessToken(user.username());
        handleTokenGeneration(newUser, response);
        return new AuthResponse(accessToken, new UserInfo(newUser.getId(), newUser.getUsername()));
    }


    public AuthResponse login(LoginRequest user, HttpServletResponse response) {
        TodoUser existingUser = todoUserRepository.findTodoUserByUsername(user.username()).orElseThrow(()
                -> new UserNotFound("This user is yet to register"));
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.username(), user.password()));
            if (authentication.isAuthenticated()) {
                String accessToken = jwtService.generateAccessToken(existingUser.getUsername());
                handleTokenGeneration(existingUser, response);
                return new AuthResponse(accessToken, new UserInfo(existingUser.getId(),existingUser.getUsername()));
            }else {
                return null;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public void handleTokenGeneration(TodoUser user, HttpServletResponse response) {
        tokenService.revokeAndDeleteTokenForUser(user.getId());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());
        tokenService.saveToken(refreshToken,user);
        //savedTokenOnCookies(refreshToken,response);

    }
}