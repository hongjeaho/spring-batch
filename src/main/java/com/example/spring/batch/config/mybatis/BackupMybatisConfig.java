package com.example.spring.batch.config.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Objects;

import static com.example.spring.batch.config.datasource.BackupDataSource.BACKUP_DATASOURCE;
import static com.example.spring.batch.config.datasource.StoreDataSource.BATCH_DATASOURCE;

@Configuration
@MapperScan(
        basePackages = {"com.example.spring.batch.repository.backup"},
        sqlSessionFactoryRef = "backupDomainSqlSessionFactory",
        annotationClass = Mapper.class
)
public class BackupMybatisConfig {
    @Bean
    public SqlSessionFactory backupDomainSqlSessionFactory(
            @Qualifier(BACKUP_DATASOURCE) final DataSource backupDomainDataSource,
            final ApplicationContext applicationContext
    ) throws Exception {
        final var factory = new SqlSessionFactoryBean();

        factory.setDataSource(backupDomainDataSource);
        factory.setVfs(SpringBootVFS.class);
        factory.setTypeAliasesPackage(
                "com.example.spring.batch.dto.**, com.example.spring.batch.entity.backup.**");
        factory.setConfigLocation(applicationContext.getResource("classpath:backup-mybatis-config.xml"));
        factory.setMapperLocations(
                applicationContext.getResources("classpath:mybatis-mapper/backup/**/*.xml")
        );
        Objects.requireNonNull(factory.getObject())
                .getConfiguration()
                .setMapUnderscoreToCamelCase(true);

        return factory.getObject();
    }
}
