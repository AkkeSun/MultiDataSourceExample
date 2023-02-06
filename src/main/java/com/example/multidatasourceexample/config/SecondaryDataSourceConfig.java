package com.example.multidatasourceexample.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@EnableJpaRepositories(
    entityManagerFactoryRef = "secondaryEntityManagerFactory",
    transactionManagerRef = "secondaryTransactionManager",
    basePackages = { "com.example.multidatasourceexample.repository.secondary" } // Repository Directory
)
public class SecondaryDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.secondary")
    public DataSourceProperties secondaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource secondaryDataSource(
        @Qualifier("secondaryDataSourceProperties") DataSourceProperties dataSourceProperties
    ) {
        return dataSourceProperties
            .initializeDataSourceBuilder()
            .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
        EntityManagerFactoryBuilder builder,
        @Qualifier("secondaryDataSource") DataSource dataSource
    ) {
        return builder
            .dataSource(dataSource)
            .packages("com.example.multidatasourceexample.domain.secondary") // entity Directory
            .persistenceUnit("secondaryEntityManager")
            .build();
    }

    @Bean
    public PlatformTransactionManager secondaryTransactionManager(
        @Qualifier("secondaryEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
