package com.ebench.notificationservice.email;


import com.ebench.notificationservice.email.provider.AWSEmailServiceProvider;
import com.ebench.notificationservice.enums.Provider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceProviderFactory {

    private final AWSEmailServiceProvider awsEmailServiceProvider;

    @Autowired
    public EmailServiceProviderFactory(AWSEmailServiceProvider awsEmailServiceProvider){this.awsEmailServiceProvider = awsEmailServiceProvider;}


    public IEmailServiceProvider getProvider(Provider provider){

        switch(provider){
            case AWS:
            default: return awsEmailServiceProvider;
        }

    }
}
