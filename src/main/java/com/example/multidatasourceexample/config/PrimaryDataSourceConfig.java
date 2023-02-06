package com.example.multidatasourceexample.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
    entityManagerFactoryRef = "primaryEntityManagerFactory",
    transactionManagerRef = "primaryTransactionManager",
    basePackages = { "com.example.multidatasourceexample.repository.primary" } // Repository Directory
)
public class PrimaryDataSourceConfig {

    @Bean
    @Primary // 한쪽에만 붙여줍니다
    @ConfigurationProperties("spring.datasource.primary")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource primaryDataSource(
        @Qualifier("primaryDataSourceProperties") DataSourceProperties dataSourceProperties
    ) {
        return dataSourceProperties
            .initializeDataSourceBuilder()
            .build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
        EntityManagerFactoryBuilder builder,
        @Qualifier("primaryDataSource") DataSource dataSource
    ) {
        return builder
            .dataSource(dataSource)
            .packages("com.example.multidatasourceexample.domain.primary") // entity Directory
            .persistenceUnit("primaryEntityManager")
            .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager primaryTransactionManager(
        @Qualifier("primaryEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
