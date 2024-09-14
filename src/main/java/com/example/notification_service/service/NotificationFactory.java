package com.example.notification_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationFactory {

    @Autowired
    private EmailNotificationSender emailSender;

    @Autowired
    private SMSNotificationSender smsSender;

    @Autowired
    private IVRNotificationSender ivrSender;

    public NotificationSender getNotificationSender(String type){
        return switch (type.toLowerCase()) {
            case "email" -> emailSender;
            case "sms" -> smsSender;
            case "ivr" -> ivrSender;
            default -> throw new IllegalArgumentException("Unknown notification method");
        };
    }
}
