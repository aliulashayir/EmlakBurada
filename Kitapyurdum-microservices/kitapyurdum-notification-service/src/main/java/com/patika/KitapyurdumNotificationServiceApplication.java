package com.patika;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class KitapyurdumNotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KitapyurdumNotificationServiceApplication.class, args);
    }
}
