package com.example.spring.batch.database;

import com.example.spring.batch.dto.MemberVO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;

@Configuration
@ConditionalOnProperty(name = "spring.batch.job.name", havingValue = "example.database")
public class DatabaseSampleConfiguration {

    private final SqlSessionFactory storeDomainSqlSessionFactory;
    private final SqlSessionFactory backupDomainSqlSessionFactory;

    public DatabaseSampleConfiguration(SqlSessionFactory storeDomainSqlSessionFactory, SqlSessionFactory backupDomainSqlSessionFactory){
        this.storeDomainSqlSessionFactory = storeDomainSqlSessionFactory;
        this.backupDomainSqlSessionFactory = backupDomainSqlSessionFactory;
    }

    @Bean
    public Job job(JobRepository jobRepository, Step databaseStep, JobExecutionListener jobExecutionListener, JobParametersIncrementer jobParametersIncrementer) {
        return new JobBuilder("example.database.job", jobRepository)
                .incrementer(jobParametersIncrementer)
                .start(databaseStep)
                .listener(jobExecutionListener)
                .build();
    }

    @Bean
    public Step databaseStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("example.database.step", jobRepository)
                .<MemberVO, MemberVO>chunk(10, transactionManager)
                .reader(sampleMybatisUserItemReader())
                .processor(sampleItemProcessor())
                .writer(sampleMybatisUserBatchItemWriter())
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public MyBatisPagingItemReader<MemberVO> sampleMybatisUserItemReader() {
        return new MyBatisPagingItemReaderBuilder<MemberVO>()
                .sqlSessionFactory(storeDomainSqlSessionFactory)
                .queryId("com.example.spring.batch.repository.store.MemberMapper.findAll")
                .build();
    }

    @Bean
    public ItemProcessor<MemberVO, MemberVO> sampleItemProcessor() {
        return readUser -> {
            readUser.setCreatedTime(LocalDateTime.now());
            readUser.setUpdatedTime(LocalDateTime.now());
            return readUser;
        };
    }

    @Bean
    public MyBatisBatchItemWriter<MemberVO> sampleMybatisUserBatchItemWriter() {
        return new MyBatisBatchItemWriterBuilder<MemberVO>()
                .sqlSessionFactory(backupDomainSqlSessionFactory)
                .statementId(
                        "com.example.spring.batch.repository.backup.BackupMemberMapper.insertMember")
                .assertUpdates(false)
                .build();
    }



}
