package com.maace.connectEtec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ConnectEtecApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConnectEtecApplication.class, args);
    }
}
