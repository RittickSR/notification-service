package com.example.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ShortStatsResponse {
    private List<ShortStats> notifications;
    private Long count;
}
