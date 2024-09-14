package com.example.notification_service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AggregatedStats {
    private String date;
    private List<ShortStats> notifications;
    private Long count;
}
