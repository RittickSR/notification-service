package com.example.notification_service.respository;

import com.example.notification_service.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findBySentTimeBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT DATE(n.sentTime) AS date, n.type, n.status, COUNT(n) AS count " +
            "FROM Notification n " +
            "WHERE n.sentTime BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE(n.sentTime), n.type, n.status")
    List<Object[]> findGroupedByDay(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
