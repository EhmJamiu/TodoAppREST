package com.ehmjamiu.learn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String greet() {
        return "Hello, Docker, Railway and everyone. " +
                "It's me here! Just wanted let you know that " +
                "I just deployed my first backend RESTful Web Service " +
                "on Railway. It's not been an easy task." +
                "After so many time of crashes that I have lost count." +
                "Finally, now it is working. ALhamdulillah. " +
                "The application is up and running now. " +
                "Thank you for checking this out. " +
                " " +
                "~ FROM " +
                "Ehm Jamiu";
    }

}