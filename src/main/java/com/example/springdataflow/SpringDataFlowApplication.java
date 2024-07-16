package com.example.springdataflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDataFlowApplication {

    public static void main(String[] args) {
        final var exitStatus = SpringApplication.exit(
            SpringApplication.run(SpringDataFlowApplication.class, args));

        System.exit(exitStatus);
    }
}
