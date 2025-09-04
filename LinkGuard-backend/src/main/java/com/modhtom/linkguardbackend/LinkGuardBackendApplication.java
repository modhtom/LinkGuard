package com.modhtom.linkguardbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LinkGuardBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinkGuardBackendApplication.class, args);
    }

}
