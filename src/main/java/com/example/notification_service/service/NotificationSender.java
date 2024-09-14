package com.example.notification_service.service;

import com.example.notification_service.model.User;

public interface NotificationSender {
    String sendNotification(String message, User user);
}


