package org.nh.rest;

import java.util.Properties;

import org.nh.rest.model.ds01.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.GemfireTransactionManager;
import org.springframework.data.gemfire.LocalRegionFactoryBean;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.GemFireCache;

@Configuration
public class GemfireConfig {

    @Bean
    Properties gemfireProperties() {
        Properties gemfireProperties = new Properties();
        gemfireProperties.setProperty("name", "DataGemFireApplication");
        gemfireProperties.setProperty("mcast-port", "0");
        gemfireProperties.setProperty("log-level", "config");
        return gemfireProperties;
    }

    @Bean
    CacheFactoryBean gemfireCache() {
        CacheFactoryBean gemfireCache = new CacheFactoryBean();
        gemfireCache.setClose(true);
        gemfireCache.setProperties(gemfireProperties());
        String gemfireCacheName = "gemfireCache" + System.currentTimeMillis();
        gemfireCache.setBeanName(gemfireCacheName);
        gemfireCache.setUseBeanFactoryLocator(false);
        return gemfireCache;
    }

    @Bean
    LocalRegionFactoryBean<Long, User> usersRegion(final GemFireCache cache) {
        LocalRegionFactoryBean<Long, User> userRegion = new LocalRegionFactoryBean<>();
        userRegion.setCache(cache);
        userRegion.setClose(false);
        userRegion.setName("Users");
        userRegion.setPersistent(false);
        return userRegion;
    }

    @Bean
    GemfireTransactionManager gemfireTransactionManager(final Cache cache) {
        return new GemfireTransactionManager(cache);
    }

}
