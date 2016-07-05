package org.nh.rest;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "org.nh.rest.persistence.relational.ds01", /*
                                                                                  * entityManagerFactoryRef
                                                                                  * =
                                                                                  * "ds01EntityManager",
                                                                                  */transactionManagerRef = "ds01TransactionManager")
@Profile("nonjta")
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

    @Bean
    public ChainedTransactionManager chainedTransactionManager(PlatformTransactionManager ds01TransactionManager,
            PlatformTransactionManager ds02TransactionManager) {
        ChainedTransactionManager t = new ChainedTransactionManager(ds01TransactionManager, ds02TransactionManager);
        return t;
    }

}
