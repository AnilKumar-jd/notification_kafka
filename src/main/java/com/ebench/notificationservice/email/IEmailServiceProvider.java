package com.ebench.notificationservice.email;

/*
   @created by kamal 
   @created on on 04/11/20
   @project notification-service
 */

import org.springframework.stereotype.Service;


@Service
public interface IEmailServiceProvider {
    Boolean sendEmail(String TO,String SUBJECT,String BODY) throws Exception;

}
