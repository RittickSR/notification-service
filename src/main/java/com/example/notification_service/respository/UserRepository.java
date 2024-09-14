package com.example.notification_service.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.notification_service.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
}
