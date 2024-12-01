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
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

import java.util.Objects;

import static com.example.spring.batch.config.datasource.StoreDataSource.BATCH_DATASOURCE;

@Configuration
@MapperScan(
        basePackages = {"com.example.spring.batch.repository.store"},
        sqlSessionFactoryRef = "storeDomainSqlSessionFactory",
        annotationClass = Mapper.class
)
public class StoreMybatisConfig {
    @Bean
    @Primary
    public SqlSessionFactory storeDomainSqlSessionFactory(
            @Qualifier(BATCH_DATASOURCE) final DataSource storeDomainDataSource,
            final ApplicationContext applicationContext
    ) throws Exception {
        final var factory = new SqlSessionFactoryBean();

        factory.setDataSource(storeDomainDataSource);
        factory.setVfs(SpringBootVFS.class);
        factory.setTypeAliasesPackage(
                "com.example.spring.batch.dto.**, com.example.spring.batch.entity.store.**");
        factory.setConfigLocation(applicationContext.getResource("classpath:store-mybatis-config.xml"));
        factory.setMapperLocations(
                applicationContext.getResources("classpath:mybatis-mapper/store/**/*.xml")
        );
        Objects.requireNonNull(factory.getObject())
                .getConfiguration()
                .setMapUnderscoreToCamelCase(true);

        return factory.getObject();
    }
}
