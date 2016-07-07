package org.nh.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"org.nh.rest.controllers", "org.nh.rest.aspects", "org.nh.rest.service",
        "org.nh.rest.security"})
@EnableJpaRepositories({"org.nh.rest.persistence.relational.ds"})
@EnableElasticsearchRepositories({"org.nh.rest.persistence.elasticsearch"})
@EnableGemfireRepositories({"org.nh.rest.persistence.gemfire"})
@Import({AppConfiguration.class, DS01Config.class, DS02Config.class, TransactionManagerConfigChained.class,
        TransactionManagerConfigJTA.class, GemfireConfig.class})
public class Application {

    public static final String X_AUTH_EMAIL = "X-Auth-Email";
    public static final String X_AUTH_PASS = "X-Auth-Password";
    public static final String X_AUTH_SLNTKN = "X-Auth-SlnTkn";
    public static final String X_AUTH_SID = "X-Auth-Sid";

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

}
