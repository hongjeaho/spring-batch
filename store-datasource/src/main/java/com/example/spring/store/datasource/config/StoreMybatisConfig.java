package com.example.spring.store.datasource.config;

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
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Objects;

import static com.example.spring.store.datasource.config.StoreDataSource.STORE_BATCH_DATASOURCE;

@Configuration
@Import(StoreDataSource.class)
@MapperScan(
        basePackages = {"com.example.spring.store.datasource.repository"},
        sqlSessionFactoryRef = "storeDomainSqlSessionFactory",
        annotationClass = Mapper.class
)
public class StoreMybatisConfig {
    @Bean
    @Primary
    public SqlSessionFactory storeDomainSqlSessionFactory(
            @Qualifier(STORE_BATCH_DATASOURCE) final DataSource storeDomainDataSource,
            final ApplicationContext applicationContext
    ) throws Exception {
        final var factory = new SqlSessionFactoryBean();

        factory.setDataSource(storeDomainDataSource);
        factory.setVfs(SpringBootVFS.class);
        factory.setTypeAliasesPackage(
                "com.example.spring.backup.datasource.dto.**, com.example.spring.backup.datasource.entity.**");
        factory.setConfigLocation(applicationContext.getResource("classpath:store-mybatis-config.xml"));
        factory.setMapperLocations(
                applicationContext.getResources("classpath:store-mybatis-mapper/**/*.xml")
        );
        Objects.requireNonNull(factory.getObject())
                .getConfiguration()
                .setMapUnderscoreToCamelCase(true);

        return factory.getObject();
    }
}
