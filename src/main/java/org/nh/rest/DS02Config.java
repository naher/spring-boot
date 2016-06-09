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
@EnableJpaRepositories(basePackages = "org.nh.rest.model.ds02", entityManagerFactoryRef = "ds02EntityManager", transactionManagerRef = "ds02TransactionManager")
public class DS02Config {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource02")
    public DataSource ds02() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    PlatformTransactionManager ds02TransactionManager() {
        return new JpaTransactionManager(ds02EntityManagerFactory().getObject());
    }

    @Bean
    LocalContainerEntityManagerFactoryBean ds02EntityManagerFactory() {

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setDataSource(ds02());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setPackagesToScan("org.nh.rest.model.ds02");

        return factoryBean;
    }
}
