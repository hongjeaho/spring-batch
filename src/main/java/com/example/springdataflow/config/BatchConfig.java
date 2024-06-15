package com.example.springdataflow.config;

import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BatchProperties.class)
public class BatchConfig {

    @Bean
    @ConditionalOnMissingBean
    public JobLauncherApplicationRunner jobLauncherApplicationRunner(
            JobRepository jobRepository,
            JobLauncher jobLauncher,
            JobExplorer jobExplorer) {

        return new JobLauncherApplicationRunner(jobLauncher, jobExplorer, jobRepository);
    }
}
