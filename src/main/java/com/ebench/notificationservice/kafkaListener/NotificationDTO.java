package com.ebench.notificationservice.kafkaListener;

/*
   @created by kamal 
   @created on on 04/11/20
   @project notification-service
 */



import com.ebench.notificationservice.enums.NotificationAlertType;
import com.ebench.notificationservice.enums.NotificationCategory;
import com.ebench.notificationservice.enums.NotificationType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    String recipient;
    NotificationType notificationType;
    Long userId;
    Long templateId;
    String notificationName;
    String[] notificationData;
    NotificationCategory notificationCategory;
    NotificationAlertType alertType;
}
