package com.example.demo.config;

import com.example.demo.models.CompanyDTO;
import com.example.demo.models.PricesDTO;
import com.mongodb.reactivestreams.client.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.index.IndexResolver;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.index.ReactiveIndexOperations;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Slf4j
@Configuration
public class MongoConfig {

    private final ReactiveMongoTemplate mongoTemplate;
    private final MongoClient reactiveMongoClient;

    @Autowired
    public MongoConfig(MongoClient reactiveMongoClient, ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.reactiveMongoClient = reactiveMongoClient;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initIndicesAfterStartup() {
        try {
            final MongoMappingContext mappingContext = (MongoMappingContext) this.mongoTemplate.getConverter().getMappingContext();
            IndexResolver resolver = new MongoPersistentEntityIndexResolver(mappingContext);

            ensureIndices(resolver, CompanyDTO.class);
            ensureIndices(resolver, PricesDTO.class);
        } catch (Exception ex) {
            log.error("Failed to init indexes after start: ", ex);
        }

        log.info("Index check process completed");
    }

    private <T> void ensureIndices(IndexResolver resolver, Class<T> tClass) {
        log.info("Check `{}` indices", tClass.getCanonicalName());
        ReactiveIndexOperations indexOperationsAssetDTO = mongoTemplate.indexOps(tClass);
        resolver.resolveIndexFor(tClass).forEach(indexOperationsAssetDTO::ensureIndex);
    }
}
