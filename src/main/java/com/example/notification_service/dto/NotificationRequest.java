package com.example.notification_service.dto;

import lombok.*;


@Getter
@Setter
public class NotificationRequest {
    private String type;
    private String message;
    private Long userid;
}

