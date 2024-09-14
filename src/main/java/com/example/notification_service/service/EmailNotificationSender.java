package com.example.notification_service.service;

import com.example.notification_service.model.User;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationSender implements NotificationSender {

    @Override
    public String sendNotification(String message, User user){
        String emailID = user.getEmail();
        System.out.println("Sending Email: " + message + " at "+emailID);
        try{
        //Implement calling third party API
        return "Email \""+message+"\" sent to "+emailID;
        }
        catch (Exception e){
            return "Exception caused in Third party API";
        }
    }
}
