package org.nh.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Profile("nonjta")
public class TransactionManagerConfigChained {

    @Bean
    public ChainedTransactionManager distributedTransactionManager(PlatformTransactionManager ds01TransactionManager,
            PlatformTransactionManager ds02TransactionManager) {
        ChainedTransactionManager t = new ChainedTransactionManager(ds01TransactionManager, ds02TransactionManager);
        return t;
    }

}
