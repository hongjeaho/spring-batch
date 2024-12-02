package com.example.spring.store.datasource.config;

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
public class StoreDataSource {

    public final static String STORE_BATCH_DATASOURCE = "storeBatchDatasource";
    public final static String STORE_BATCH_DATASOURCE_MANAGER = "storeBatchTransactionManager";
    public static final String STORE_DOMAIN_JDBC_TEMPLATE = "storeDomainJdbcTemplate";
    public static final String STORE_DOMAIN_NAMED_PARAMETER_JDBC_OPERATIONS = "storeDomainNamedParameterJdbcOperations";


    @Bean(STORE_BATCH_DATASOURCE)
    @ConfigurationProperties("store.domain.datasource")
    public DataSource storeBatchDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(STORE_BATCH_DATASOURCE_MANAGER)
    public PlatformTransactionManager getStoreBatchTransactionManager(@Qualifier(STORE_BATCH_DATASOURCE) final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = STORE_DOMAIN_NAMED_PARAMETER_JDBC_OPERATIONS)
    public NamedParameterJdbcOperations storeDomainNamedParameterJdbcOperations() {
        return new NamedParameterJdbcTemplate(storeBatchDataSource());
    }

    @Bean(name = STORE_DOMAIN_JDBC_TEMPLATE)
    public JdbcTemplate storeDomainJdbcTemplate(
            @Qualifier(STORE_BATCH_DATASOURCE) final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
