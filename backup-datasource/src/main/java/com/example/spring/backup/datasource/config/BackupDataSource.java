package com.example.spring.backup.datasource.config;

import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableTransactionManagement
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        MybatisAutoConfiguration.class})
@Configuration
public class BackupDataSource {

    public final static String BACKUP_DATASOURCE = "backupBatchDatasource";
    public final static String BACKUP_DATASOURCE_MANAGER = "backupBatchTransactionManager";
    public static final String BACKUP_DOMAIN_JDBC_TEMPLATE = "backupDomainJdbcTemplate";
    public static final String BACKUP_DOMAIN_NAMED_PARAMETER_JDBC_OPERATIONS = "backupDomainNamedParameterJdbcOperations";

    @Bean(BACKUP_DATASOURCE)
    @ConfigurationProperties("backup.domain.datasource")
    public DataSource backupBatchDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(BACKUP_DATASOURCE_MANAGER)
    public PlatformTransactionManager getBackupBatchTransactionManager(@Qualifier(BACKUP_DATASOURCE) final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = BACKUP_DOMAIN_NAMED_PARAMETER_JDBC_OPERATIONS)
    public NamedParameterJdbcOperations backupDomainNamedParameterJdbcOperations() {
        return new NamedParameterJdbcTemplate(backupBatchDataSource());
    }

    @Bean(name = BACKUP_DOMAIN_JDBC_TEMPLATE)
    public JdbcTemplate backupDomainJdbcTemplate(
            @Qualifier(BACKUP_DATASOURCE) final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
