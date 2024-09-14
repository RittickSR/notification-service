package com.example.notification_service.service;

import com.example.notification_service.model.User;
import org.springframework.stereotype.Component;

@Component
public class IVRNotificationSender implements NotificationSender {

    @Override
    public String sendNotification(String message, User user){
        String phone = user.getPhone();
        System.out.println("Sending IVR Notification: "+message+" at mobile number "+phone);
        try {
            //Implement calling third party API
            return "IVR Notification \"" + message + "\" sent to " + phone;
        }
        catch (Exception e){
            return "Exception caused in Third party API";
        }
    }
}
