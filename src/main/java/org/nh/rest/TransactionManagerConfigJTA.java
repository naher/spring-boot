package org.nh.rest;

import java.io.File;
import java.util.Properties;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import com.atomikos.icatch.config.UserTransactionService;
import com.atomikos.icatch.config.UserTransactionServiceImp;
import com.atomikos.icatch.jta.UserTransactionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationHome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.transaction.jta.JtaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.boot.jta.atomikos.AtomikosProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.util.StringUtils;

@Configuration
@Profile("jta")
@EnableJpaRepositories(basePackages = {"org.nh.rest.persistence.relational.ds01",
        "org.nh.rest.persistence.relational.ds02"})
public class TransactionManagerConfigJTA {

    @Bean
    @ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.ds01")
    public DataSource ds01() {
        return new AtomikosDataSourceBean();
    }

    @Bean
    PlatformTransactionManager ds01TransactionManager(JtaTransactionManager tm) {
        return tm;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean ds01EntityManagerFactory() {

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setJtaDataSource(ds01());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setPackagesToScan("org.nh.rest.model.ds01");

        return factoryBean;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.ds02")
    public DataSource ds02() {
        return new AtomikosDataSourceBean();
    }

    @Bean
    PlatformTransactionManager ds02TransactionManager(JtaTransactionManager tm) {
        return tm;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean ds02EntityManagerFactory() {

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setJtaDataSource(ds02());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setPackagesToScan("org.nh.rest.model.ds02");

        return factoryBean;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    @ConditionalOnMissingBean
    public UserTransactionManager atomikosTransactionManager(UserTransactionService userTransactionService)
            throws Exception {
        UserTransactionManager manager = new UserTransactionManager();
        manager.setStartupTransactionService(false);
        manager.setForceShutdown(true);
        return manager;
    }

    @Bean
    PlatformTransactionManager distributedTransactionManager(JtaTransactionManager tm) {
        return tm;
    }

    @Bean
    public JtaTransactionManager transactionManager(UserTransaction userTransaction,
            TransactionManager transactionManager) {
        return new JtaTransactionManager(userTransaction, transactionManager);
    }

    @Autowired
    private JtaProperties jtaProperties;

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties(prefix = JtaProperties.PREFIX)
    public AtomikosProperties atomikosProperties() {
        return new AtomikosProperties();
    }

    @Bean(initMethod = "init", destroyMethod = "shutdownForce")
    @ConditionalOnMissingBean(UserTransactionService.class)
    public UserTransactionServiceImp userTransactionService(AtomikosProperties atomikosProperties) {
        Properties properties = new Properties();
        if (StringUtils.hasText(this.jtaProperties.getTransactionManagerId())) {
            properties.setProperty("com.atomikos.icatch.tm_unique_name", this.jtaProperties.getTransactionManagerId());
        }
        properties.setProperty("com.atomikos.icatch.log_base_dir", getLogBaseDir());
        properties.putAll(atomikosProperties.asProperties());
        return new UserTransactionServiceImp(properties);
    }

    private String getLogBaseDir() {
        if (StringUtils.hasLength(this.jtaProperties.getLogDir())) {
            return this.jtaProperties.getLogDir();
        }
        File home = new ApplicationHome().getDir();
        return new File(home, "transaction-logs").getAbsolutePath();
    }

}
