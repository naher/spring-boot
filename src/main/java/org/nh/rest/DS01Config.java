package org.nh.rest;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "org.nh.rest.model.ds01", entityManagerFactoryRef = "ds01EntityManager", transactionManagerRef = "ds01TransactionManager")
public class DS01Config {

    @Bean
    // @Primary
    @ConfigurationProperties(prefix = "spring.datasource01")
    public DataSource ds01() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    PlatformTransactionManager ds01TransactionManager() {
        return new JpaTransactionManager(ds01EntityManagerFactory().getObject());
    }

    @Bean
    LocalContainerEntityManagerFactoryBean ds01EntityManagerFactory() {

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setDataSource(ds01());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setPackagesToScan("org.nh.rest.model.ds01");

        return factoryBean;
    }

}
