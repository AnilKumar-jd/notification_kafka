package com.ebench.notificationservice.kafkaListener;

/*
   @created by kamal 
   @created on on 04/11/20
   @project notification-service
 */

import com.ebench.notificationservice.NotificationService;
import com.ebench.notificationservice.email.EmailServiceProviderFactory;
import com.ebench.notificationservice.email.IEmailServiceProvider;
import com.ebench.notificationservice.enums.Status;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
@Slf4j
public class EmailNotificationListener {

    private final NotificationService notificationService;
    private final EmailServiceProviderFactory emailServiceProviderFactory;

    @Autowired
    public EmailNotificationListener(NotificationService notificationService, EmailServiceProviderFactory emailServiceProviderFactory){
        this.notificationService = notificationService;
        this.emailServiceProviderFactory = emailServiceProviderFactory;
    }

    @KafkaListener(topics = Topics.EMAIL_NOTIFICATION, groupId = GroupIDs.EMAIL_NOTIFICATION_GROUP)
    public void listenToEmailNotification(@Valid @Payload ConsumerRecord<String, String> record)  {
        ObjectMapper objectMapper = new ObjectMapper();
        NotificationDTO notificationDto = null;
        try {
            notificationDto = objectMapper.readValue(record.value(), NotificationDTO.class);
        } catch (Exception e) {

            log.info(e.getMessage());
        }
        log.info("Consumed message!");

        if (notificationDto == null)
            return;

        //Notification notification = notificationService.createNotificationEntity(notificationDto);
        //if(notification.getStatus() == Status.FAILED)
          //  return;

        //log.info("Prepared Notification Entity! ");

       /* IEmailServiceProvider emailService = emailServiceProviderFactory.getProvider(notification.getProvider());
        try {
            if(emailService.sendEmail(notification.getEmail(),notification.getSubject(),notification.getBody()))
                notification.setStatus(Status.SUCCESS);
            else
                notification.setStatus(Status.FAILED);
        } catch (Exception e) {
            log.info("The email was not sent.");
            log.info("Error message: ");
            log.info(e.toString());
            notification.setStatus(Status.FAILED);
        }

        notificationService.updateStatus(notification);
*/
    }
}
