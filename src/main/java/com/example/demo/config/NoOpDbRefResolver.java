package com.example.demo.config;

import com.mongodb.DBRef;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.lang.Nullable;

public enum NoOpDbRefResolver implements DbRefResolver {
    INSTANCE;

    private NoOpDbRefResolver() {
    }

    public Object resolveDbRef(MongoPersistentProperty property, @Nullable DBRef dbref, DbRefResolverCallback callback, DbRefProxyHandler handler) {
        return callback.resolve(property);
    }

    public Document fetch(DBRef dbRef) {
        return new Document("_id", dbRef.getId());
    }

    public List<Document> bulkFetch(List<DBRef> dbRefs) {
        if (dbRefs.isEmpty()) {
            return Collections.emptyList();
        } else {
            return dbRefs.stream().map(dbRef -> new Document("_id", dbRef.getId())).collect(Collectors.toList());
        }
    }

    @Nullable
    public Object resolveReference(MongoPersistentProperty property, Object source, ReferenceLookupDelegate referenceLookupDelegate, ReferenceResolver.MongoEntityReader entityReader) {
        return null;
    }
}