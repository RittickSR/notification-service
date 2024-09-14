package com.example.notification_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StatRequest{
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
