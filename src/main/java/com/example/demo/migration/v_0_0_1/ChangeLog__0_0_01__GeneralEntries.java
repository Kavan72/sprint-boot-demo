package com.example.demo.migration.v_0_0_1;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import com.mongodb.reactivestreams.client.ClientSession;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@ChangeUnit(id = "client-updater", order = "2", author = "mongock")
public class ChangeLog__0_0_01__GeneralEntries {

    @Execution
    public void execution(ClientSession clientSession, MongoDatabase mongoDatabase) {
        System.out.println("dwadawdawddawda");
    }

    @RollbackExecution
    public void rollbackExecution(ClientSession clientSession, MongoDatabase mongoDatabase) {
    }
}
