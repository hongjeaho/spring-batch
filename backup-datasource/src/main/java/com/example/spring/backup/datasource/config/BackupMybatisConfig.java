package com.example.spring.backup.datasource.config;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.util.Objects;

import static com.example.spring.backup.datasource.config.BackupDataSource.BACKUP_DATASOURCE;

@Configuration
@Import(BackupDataSource.class)
@MapperScan(
        basePackages = {"com.example.spring.backup.datasource.repository"},
        sqlSessionFactoryRef = "backupDomainSqlSessionFactory",
        annotationClass = Mapper.class
)
public class BackupMybatisConfig {
    @Bean
    public SqlSessionFactory backupDomainSqlSessionFactory(
            @Qualifier(BACKUP_DATASOURCE) final DataSource backupBatchDataSource,
            final ApplicationContext applicationContext
    ) throws Exception {
        final var factory = new SqlSessionFactoryBean();

        factory.setDataSource(backupBatchDataSource);
        factory.setVfs(SpringBootVFS.class);
        factory.setTypeAliasesPackage(
                "com.example.spring.backup.datasource.dto.**, com.example.spring.backup.datasource.entity.**");
        factory.setConfigLocation(applicationContext.getResource("classpath:backup-mybatis-config.xml"));
        factory.setMapperLocations(
                applicationContext.getResources("classpath:backup-mybatis-mapper/**/*.xml")
        );
        Objects.requireNonNull(factory.getObject())
                .getConfiguration()
                .setMapUnderscoreToCamelCase(true);

        return factory.getObject();
    }
}
