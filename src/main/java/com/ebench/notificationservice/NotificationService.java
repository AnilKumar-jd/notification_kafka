package com.ebench.notificationservice;


import com.ebench.notificationservice.enums.Provider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;



@Service
@Slf4j
public class NotificationService {
    @Value("${email.provider}")
    private Provider emailProvider;

    @Value("${message.provider}")
    private Provider messageProvider;

    /*private final DataAccessService dataAccessService;

    @Autowired
    public NotificationService(DataAccessService dataAccessService) {
        this.dataAccessService = dataAccessService;
    }

    public Notification createNotificationEntity(NotificationDTO notificationDto) {

        Notification notification = new Notification();

        notification.setStatus(Status.INIT);
        notification.setUserId(notificationDto.getUserId());
        notification.setNotificationType(notificationDto.getNotificationType());
        notification.setNotificationCategory(Objects.isNull(notificationDto.getNotificationCategory()) ? null : notificationDto.getNotificationCategory());
        notification.setNotificationAlertType(Objects.isNull(notificationDto.getAlertType()) ? null : notificationDto.getAlertType());

        switch (notificationDto.getNotificationType()) {
            case EMAIL:
                notification.setEmail(notificationDto.getRecipient());
                notification.setProvider(emailProvider);
                break;
            case MESSAGE:
                notification.setPhoneNumber(notificationDto.getRecipient());
                notification.setProvider(messageProvider);
                break;
            case PHONE:
                notification.setApmId(notificationDto.getRecipient());
                notification.setFcmId(notificationDto.getRecipient());
                notification.setProvider(Provider.FIREBASE);
                break;
        }


        Object[] data = notificationDto.getNotificationData();
        HashMap<String, Object> param = new HashMap<>();
        param.put("templateName", notificationDto.getNotificationName());
        List<Template> templateList = (List<Template>) dataAccessService.readNative(Template.class,
                GET_NOTIFICATION_TEMPLATE_BY_NAME, param);
        Template template = templateList.get(0);
        if (Objects.isNull(template)) {
            log.info("Template ID Not Present");
            notification.setStatus(Status.FAILED);
            return notification;
        }
        if (template.getNotificationType() != notification.getNotificationType()) {
            log.info("Notification Type Mismatch");
            notification.setStatus(Status.FAILED);
            return notification;
        }

        String templateBody = template.getTemplateBody();
        String templateSubject = template.getTemplateSubject();

        try {
            notification.setSubject(templateSubject);
            notification.setBody(String.format(templateBody, data));
        } catch (Exception ex) {
            log.info("Template and Data mismatch:" + ex.toString());
            notification.setStatus(Status.FAILED);
            return notification;
        }


        notification = (Notification) dataAccessService.create(notification);
        return notification;
    }

    public void updateStatus(Notification notification) {
        dataAccessService.update(notification);
    }

    public Object getInternalNotifications(Long userId, NotificationCategory notificationCategory, NotificationAlertType notificationAlertType) {
        List<NotificationResponseDTO> notificationResponseDTOList = new ArrayList<>();
        PrettyTime prettyTime = new PrettyTime();
        HashMap<String, Object> param = new HashMap<>();
        if (Objects.isNull(notificationCategory))
            param.put("notificationCategory", Arrays.stream(NotificationCategory.values()).collect(Collectors.toList()));
        else
            param.put("notificationCategory", Collections.singletonList(notificationCategory));

        if (Objects.isNull(notificationAlertType))
            param.put("notificationAlertType", Arrays.stream(NotificationAlertType.values()).collect(Collectors.toList()));
        else
            param.put("notificationAlertType", Collections.singletonList(notificationAlertType));

        param.put("userId", userId);

        List<Notification> notificationList = (List<Notification>) dataAccessService.read(GET_NOTIFICATION_FOR_A_USER, param);
        for (Notification notification : notificationList) {
            NotificationResponseDTO notificationResponseDTO = new NotificationResponseDTO();
            notificationResponseDTO.setDescription(notification.getBody());
            notificationResponseDTO.setNotificationCategory(notification.getNotificationCategory());
            notificationResponseDTO.setNotificationAlertType(notification.getNotificationAlertType());
            notificationResponseDTO.setNotificationTime(prettyTime.format(Timestamp.valueOf(notification.getCreatedAt())));
            notificationResponseDTOList.add(notificationResponseDTO);
        }

        return notificationResponseDTOList;
    }

    public Boolean updateFcmToken(Long userId, String fcmToken) {
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("fcmToken", fcmToken);
            UserFcmToken userFcmToken = (UserFcmToken) dataAccessService.readNative(UserFcmToken.class, UPDATE_FCM_TOKEN, params);
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            log.error("error" + e);
            if (e.getMessage().compareTo("org.hibernate.exception.GenericJDBCException: could not extract ResultSet") == 0) {
                return true;
            }
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), FCM_UPDATE_FAILED);
        }
        return true;
    }

    public Object getFcmTokenFromUserId(Long userId) {
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            System.out.println(userId);
            List<UserFcmToken> userFcmToken = (List<UserFcmToken>) dataAccessService.readNative(UserFcmToken.class,
                    GET_FCM_BY_USERID, params);
            UserFcmToken userFcmToken1 = userFcmToken.get(0);
            return userFcmToken1.getFcmToken();
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            if (e.getMessage().compareTo("org.hibernate.exception.GenericJDBCException: could not extract ResultSet") == 0) {
                return true;
            }
            log.error("error" + e);
            throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "User not found");
        }
    }*/
}
