package com.example.notification_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.notification_service.dto.UserCreate;
import com.example.notification_service.model.Notification;
import com.example.notification_service.model.User;
import com.example.notification_service.respository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void addUser(UserCreate request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

    }

}

