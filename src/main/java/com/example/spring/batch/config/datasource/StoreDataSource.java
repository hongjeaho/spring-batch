package com.example.spring.batch.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.DatabaseType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class StoreDataSource {

    public final static String BATCH_DATASOURCE = "STORE_BATCH_DATASOURCE";
    public final static String BATCH_DATASOURCE_MANAGER = "STORE_BATCH_DATASOURCE_MANAGER";

    @Bean(BATCH_DATASOURCE)
    @Primary
    @ConfigurationProperties("store.domain.datasource")
    public DataSource batchDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(BATCH_DATASOURCE_MANAGER)
    @Primary
    public PlatformTransactionManager getTransactionManager() {
        final var dataSourceTransactionManager = new DataSourceTransactionManager(batchDataSource());
        dataSourceTransactionManager.setGlobalRollbackOnParticipationFailure(false);

        return dataSourceTransactionManager;
    }

    @Bean
    @Primary
    public JobRepository getJobRepository() throws Exception {
        final var factoryBean = new JobRepositoryFactoryBean();
        factoryBean.setDatabaseType(DatabaseType.MYSQL.name());
        factoryBean.setDataSource(batchDataSource());
        factoryBean.setTransactionManager(getTransactionManager());
        factoryBean.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }


    @Bean
    @Primary
    public JobLauncher getJobLauncher() throws Exception {
        final var jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(getJobRepository());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

}
