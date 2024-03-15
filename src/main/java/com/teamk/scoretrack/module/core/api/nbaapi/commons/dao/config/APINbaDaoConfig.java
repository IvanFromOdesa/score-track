package com.teamk.scoretrack.module.core.api.nbaapi.commons.dao.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collections;

@Configuration
@EnableMongoRepositories(basePackages = "com.teamk.scoretrack.module.core.api.nbaapi")
@EnableConfigurationProperties
public class APINbaDaoConfig {
    private static final String NAME = "nbaapi";
    private static final String PROPS = NAME + "Properties";
    private static final String CLIENT = NAME + "MongoClient";

    @Bean(name = PROPS)
    @ConfigurationProperties(prefix = NAME + ".mongodb")
    @Primary
    public MongoProperties primaryProperties() {
        return new MongoProperties();
    }

    @Bean(name = CLIENT)
    public MongoClient mongoClient(@Qualifier(PROPS) MongoProperties mongoProperties) {
        MongoCredential credential = MongoCredential.createCredential(mongoProperties.getUsername(), mongoProperties.getAuthenticationDatabase(), mongoProperties.getPassword());
        return MongoClients.create(MongoClientSettings.builder().applyToClusterSettings(builder -> builder.hosts(Collections.singletonList(new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort())))).credential(credential).uuidRepresentation(UuidRepresentation.STANDARD).build());
    }

    @Primary
    @Bean(name = NAME + "MongoDBFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(@Qualifier(CLIENT) MongoClient mongoClient, @Qualifier(PROPS) MongoProperties mongoProperties) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }
}
