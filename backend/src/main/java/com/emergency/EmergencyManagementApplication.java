package com.emergency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EmergencyManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmergencyManagementApplication.class, args);
    }
}
