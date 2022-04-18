package com.kitcd.share_delivery_api.listener;

import com.kitcd.share_delivery_api.domain.account.Account;
import com.kitcd.share_delivery_api.domain.account.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "property.test")
@Component
@AllArgsConstructor
public class Test implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {


    }
}
