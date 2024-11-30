package com.example.spring.batch.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BackupDataSource {

    public final static String BACKUP_DATASOURCE = "BACKUP_BATCH_DATASOURCE";
    public final static String BACKUP_DATASOURCE_MANAGER = "BACKUP_BATCH_DATASOURCE_MANAGER";

    @Bean(BACKUP_DATASOURCE)
    @ConfigurationProperties("backup.domain.datasource")
    public DataSource backupBatchDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(BACKUP_DATASOURCE_MANAGER)
    public PlatformTransactionManager getTransactionManager() {
        final var dataSourceTransactionManager = new DataSourceTransactionManager(backupBatchDataSource());
        dataSourceTransactionManager.setGlobalRollbackOnParticipationFailure(false);

        return dataSourceTransactionManager;
    }


}
