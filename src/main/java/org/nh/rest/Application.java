package org.nh.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"org.nh.rest.controllers", "org.nh.rest.service"})
@EnableJpaRepositories({"org.nh.rest.persistence.relational"})
@EnableElasticsearchRepositories({"org.nh.rest.persistence.elasticsearch"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
