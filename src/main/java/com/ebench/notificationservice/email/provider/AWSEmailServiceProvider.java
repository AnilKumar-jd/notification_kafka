package com.ebench.notificationservice.email.provider;


import com.ebench.notificationservice.email.IEmailServiceProvider;
import com.ebench.notificationservice.email.Templates.BaseEmailTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
@Slf4j
public class AWSEmailServiceProvider implements IEmailServiceProvider {

    @Value("${aws.email.from}")
    private String FROM;

    @Value("${aws.email.from-name}")
    private String FROM_NAME;

    @Value("${aws.email.smtp.username}")
    private String SMTP_USERNAME;

    @Value("${aws.email.smtp.password}")
    private String SMTP_PASSWORD;

    @Value("${aws.email.ses.smtp.port}")
    private String SES_PORT;

    @Value("${aws.email.ses.hostname}")
    private String HOSTNAME;

    @Autowired
    public AWSEmailServiceProvider() {
    }

    @Override
    public Boolean sendEmail(String TO, String SUBJECT, String BODY) throws Exception {


        log.info("Email Subject:" + SUBJECT);

        // Create a Properties object to contain connection configuration information.

        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", SES_PORT);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties.
        Session session = Session.getDefaultInstance(props);

//        Uncomment to check debug logs
//        session.setDebug(true);

        // Create a message with the specified information.
        String charset = "UTF-8";
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM, FROM_NAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT, charset);
        System.out.println(BODY);
        Object data[] = new String[]{BODY};
        BODY = BODY.replaceAll("(\r\n|\n)", "<br />");
        String body = BaseEmailTemplate.getBaseEmail() + BODY + BaseEmailTemplate.getBaseEmail2();
        msg.setContent(body, "text/html; charset=utf-8");

//        msg.setContent(BaseEmailTemplate.getBaseEmail(), "text/html; charset=utf-8");

        // Create a transport.
        Transport transport = session.getTransport();

        // Connect to Amazon SES using the SMTP username and password you specified above.
        log.info("Connecting to Mail Server...");
        transport.connect(HOSTNAME, SMTP_USERNAME, SMTP_PASSWORD);

        // Send the email.
        log.info("Sending Mail to Recipient...");
        transport.sendMessage(msg, msg.getAllRecipients());
        log.info("Email sent Successfully!");

        // Close the transport
        transport.close();

        return true;
    }
}
