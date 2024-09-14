package com.example.notification_service.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.example.notification_service.dto.AggregatedStats;
import com.example.notification_service.dto.NotificationRequest;
import com.example.notification_service.dto.ShortStats;
import com.example.notification_service.dto.ShortStatsResponse;
import com.example.notification_service.model.Notification;
import com.example.notification_service.model.User;
import com.example.notification_service.respository.NotificationRepository;
import com.example.notification_service.respository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;



@Service
public class NotificationService {

    @Autowired
    private NotificationFactory notificationFactory;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String sendNotification(NotificationRequest request) throws Exception {
        Optional<User> userOptional = userRepository.findById(request.getUserid());

        if (userOptional.isPresent()){
            User user = userOptional.get();
            try {
                NotificationSender sender = notificationFactory.getNotificationSender(request.getType());
                String response = sender.sendNotification(request.getMessage(),user);
                Notification notification = new Notification();
                notification.setUser(user);
                notification.setMessage(request.getMessage());
                notification.setType(request.getType());
                notification.setStatus("SENT");
                notification.setSentTime(LocalDateTime.now());
                notificationRepository.save(notification);
                return response;
            }
            catch (IllegalArgumentException e){
                return "No such notifier present";
            }

        }
        else{
            throw new Exception("No such user present");
        }

    }

    @Transactional
    public Object getNotificationsBasedOnDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        boolean isSingleDay = startDate.toLocalDate().equals(endDate.toLocalDate());

        if (isSingleDay) {
            return getShortStatsForSingleDay(startDate, endDate);
        } else {
            return getAggregatedStatsForMultipleDays(startDate, endDate);
        }
    }


    private ShortStatsResponse getShortStatsForSingleDay(LocalDateTime startDate, LocalDateTime endDate) {
        List<Notification> notifications = notificationRepository.findBySentTimeBetween(startDate, endDate);

        List<ShortStats> shortStats = mapToShortStats(notifications);
        return new ShortStatsResponse(shortStats, (long) shortStats.size());
    }


    private List<AggregatedStats> getAggregatedStatsForMultipleDays(LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> groupedResults = notificationRepository.findGroupedByDay(startDate, endDate);

        Map<String, List<ShortStats>> groupedNotifications = new HashMap<>();

        for (Object[] result : groupedResults) {
            String date = result[0].toString();
            Long count = (Long) result[3];


            List<Notification> notifications = notificationRepository.findBySentTimeBetween(
                    LocalDateTime.parse(date + "T00:00:00"), LocalDateTime.parse(date + "T23:59:59")
            );

            groupedNotifications.put(date, mapToShortStats(notifications));
        }

        List<AggregatedStats> aggregatedStatsList = new ArrayList<>();
        for (Map.Entry<String, List<ShortStats>> entry : groupedNotifications.entrySet()) {
            aggregatedStatsList.add(new AggregatedStats(entry.getKey(), entry.getValue(), (long) entry.getValue().size()));
        }

        return aggregatedStatsList;
    }


    private List<ShortStats> mapToShortStats(List<Notification> notifications) {
        List<ShortStats> shortStatsList = new ArrayList<>();
        for (Notification notification : notifications) {
            ShortStats shortStat = new ShortStats(
                    notification.getId(),
                    notification.getType(),
                    notification.getStatus(),
                    notification.getSentTime(),
                    notification.getMessage()
            );
            shortStatsList.add(shortStat);
        }
        return shortStatsList;
    }
}

