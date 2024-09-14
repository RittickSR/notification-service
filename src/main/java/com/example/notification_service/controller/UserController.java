package com.example.notification_service.controller;

import com.example.notification_service.dto.UserCreate;
import com.example.notification_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String sendNotification(@RequestBody UserCreate request) {
            userService.addUser(request);
            return "User created successfully";
    }
}