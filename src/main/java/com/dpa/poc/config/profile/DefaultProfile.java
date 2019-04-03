package com.dpa.poc.config.profile;

import com.dpa.poc.data.MongoUserDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("default")
public class DefaultProfile {

    @Bean
    public MongoUserDAO getMongoUserDAO() {
        return new MongoUserDAO("mongodb://localhost:27017");
    }


}
