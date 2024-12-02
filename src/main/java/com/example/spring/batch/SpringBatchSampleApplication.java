package com.example.spring.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.example.spring.batch",
        "com.example.spring.store.datasource",
        "com.example.spring.backup.datasource"
})
public class SpringBatchSampleApplication {

    public static void main(String[] args) {
        final var exitStatus = SpringApplication.exit(
            SpringApplication.run(SpringBatchSampleApplication.class, args));

        System.exit(exitStatus);
    }
}
