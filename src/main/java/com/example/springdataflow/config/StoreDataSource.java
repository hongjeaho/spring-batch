package com.example.springdataflow.config;

import com.zaxxer.hikari.HikariDataSource;
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


}
