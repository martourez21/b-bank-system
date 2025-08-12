package com.bbanksystems.loan_system.config;


import com.drools.accountmanagement.loan.entity.LoanApplication;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class KieClientConfig {

    private static final Logger log = LoggerFactory.getLogger(KieClientConfig.class);

    @Value("${kie.server.url}")
    private String kieServerUrl;

    @Value("${kie.server.username}")
    private String user;

    @Value("${kie.server.password}")
    private String password;

    @Bean
    public KieServicesConfiguration kieServicesConfiguration() {
        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(kieServerUrl, user, password);

        Set<Class<?>> extraClassList = new HashSet<>();
        extraClassList.add(LoanApplication.class);
        config.addExtraClasses(extraClassList);

        config.setMarshallingFormat(MarshallingFormat.JSON);
        return config;
    }

    @Bean
    public KieServicesClient kieServicesClient(KieServicesConfiguration config) {
        return KieServicesFactory.newKieServicesClient(config);
    }
}