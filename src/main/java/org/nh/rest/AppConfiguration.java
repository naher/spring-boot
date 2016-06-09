package org.nh.rest;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AppConfiguration {

    private Logger log = Logger.getLogger(AppConfiguration.class);

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        javax.servlet.Filter securityFilter = new javax.servlet.Filter() {

            @Override
            public void destroy() {
                log.info("destroy");
            }

            @Override
            public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)
                    throws IOException, ServletException {
                log.info(">> -- Filtering " + ((HttpServletRequest) req).getRequestURI());
                fc.doFilter(req, res);
                log.info("<< -- Filtering " + ((HttpServletRequest) req).getRequestURI());
            }

            @Override
            public void init(FilterConfig arg0) throws ServletException {
                log.info("init");
            }
        };
        registrationBean.setFilter(securityFilter);
        registrationBean.setOrder(2);
        return registrationBean;
    }

    @Bean
    @Primary
    // @ConfigurationProperties()
    public DataSource primaryDataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.driverClassName(driverClassName);
        builder.url(url);
        return builder.build();
    }

    @Bean
    PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }

    @Value("${spring.datasource.hibernate.dialect}")
    private String dialect;

    @Value("${spring.datasource.jpa.hibernate.ddl-auto}")
    private String ddl_auto;

    @Bean
    @Primary
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.hbm2ddl.auto", ddl_auto);

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setDataSource(primaryDataSource());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setJpaProperties(properties);
        factoryBean.setPackagesToScan("org.nh.rest.model");

        return factoryBean;
    }

}
