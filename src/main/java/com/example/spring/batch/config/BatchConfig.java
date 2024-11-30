package com.example.spring.batch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(BatchProperties.class)
@RequiredArgsConstructor
public class BatchConfig {

    @Bean
    public JobParametersIncrementer jobParametersIncrementer() {
        return new RunIdIncrementer(); // 고유한 RunId로 파라미터 생성
    }

    @Bean
    @ConditionalOnMissingBean
    public JobLauncherApplicationRunner jobLauncherApplicationRunner(
            JobRepository jobRepository,
            JobLauncher jobLauncher,
            JobExplorer jobExplorer) {

        return new JobLauncherApplicationRunner(jobLauncher, jobExplorer, jobRepository);
    }
}
