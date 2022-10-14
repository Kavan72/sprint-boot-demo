package com.example.demo.config;

import com.mongodb.reactivestreams.client.MongoClient;
import io.mongock.driver.mongodb.reactive.driver.MongoReactiveDriver;
import io.mongock.runner.springboot.MongockSpringboot;
import io.mongock.runner.springboot.base.MongockInitializingBeanRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

@Configuration
public class Beans {

    @Bean
    public Supplier<String> idGenerator() {
        return () -> UUID.randomUUID().toString();
    }

    @Bean
    public Supplier<Instant> timestampGenerator() {
        return Instant::now;
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoMappingContext mongoMappingContext) {
        MappingMongoConverter converter = new MappingMongoConverter(NoOpDbRefResolver.INSTANCE, mongoMappingContext);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }

    @Bean
    public MongockInitializingBeanRunner getBuilder(MongoClient reactiveMongoClient,
                                                    ApplicationContext context) {
        return MongockSpringboot.builder()
                .setDriver(MongoReactiveDriver.withDefaultLock(reactiveMongoClient, "denios-backend"))
                .addMigrationScanPackage("com.example.demo.migration")
                .setSpringContext(context)
                .setTransactionEnabled(true)
                .buildInitializingBeanRunner();
    }
}
