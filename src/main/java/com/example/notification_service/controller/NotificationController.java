package com.example.notification_service.controller;

import com.example.notification_service.dto.NotificationRequest;
import com.example.notification_service.dto.StatRequest;
import com.example.notification_service.model.Notification;
import com.example.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public String sendNotification(@RequestBody NotificationRequest request) {
        try {
            return notificationService.sendNotification(request);
        }
        catch (Exception e){
            return "No such user present";
        }

    }

    @GetMapping("/stats")
    public Object getNotifications(@RequestBody StatRequest request){
        return notificationService.getNotificationsBasedOnDateRange(request.getStartTime(),request.getEndTime());
    }
}