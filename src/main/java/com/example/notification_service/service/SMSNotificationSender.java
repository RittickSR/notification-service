package com.example.notification_service.service;

import com.example.notification_service.model.User;
import org.springframework.stereotype.Component;

@Component
public class SMSNotificationSender implements NotificationSender {
    @Override
    public String sendNotification(String message, User user){
        String phone = user.getPhone();
        System.out.println("Sendind SMS: "+message+" at "+phone);
        try {
            //Implement calling third party API
            return "SMS \"" + message + "\" sent to " + phone;
        }
        catch (Exception e){
            return "Exception caused in Third party API";
        }
    }
}
