package com.security.springsecurity;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        initializeEnvironmentVariables();
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    private static void initializeEnvironmentVariables() {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach((entry) -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
    }

}
