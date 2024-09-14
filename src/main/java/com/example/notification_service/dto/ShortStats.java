package com.example.notification_service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ShortStats {
    private Long id;
    private String type;
    private String status;
    private LocalDateTime sentTime;
    private String message;
}
