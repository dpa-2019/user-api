package com.dpa.poc.config.profile;

import com.dpa.poc.data.MongoUserDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevProfile {

    @Bean
    public MongoUserDAO getMongoUserDAO() {
        return new MongoUserDAO("mongodb+srv://dpa_poc:dpa!2019@cluster0-brbjx.mongodb.net/test?retryWrites=true");
    }


}
