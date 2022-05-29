package com.kitcd.share_delivery_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaAuditing // JPA Auditing 활성화
@EnableRedisRepositories(basePackages = "com.kitcd.share_delivery_api.domain.redis")
@EnableJpaRepositories(basePackages = "com.kitcd.share_delivery_api.domain.jpa")
@SpringBootApplication
public class ShareDeliveryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShareDeliveryApiApplication.class, args);
	}

}
