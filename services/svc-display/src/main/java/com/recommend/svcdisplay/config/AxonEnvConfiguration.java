package com.recommend.svcdisplay.config;

import com.mongodb.MongoClient;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.mongo.DefaultMongoTemplate;
import org.axonframework.mongo.MongoTemplate;
import org.axonframework.mongo.eventhandling.saga.repository.MongoSagaStore;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.documentperevent.DocumentPerEventStorageStrategy;
import org.axonframework.serialization.Serializer;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zqw
 */
@Configuration
public class AxonEnvConfiguration {
    @Bean
    public EventStorageEngine eventStorageEngine(MongoClient mongoClient, Serializer serializer) {
        DefaultMongoTemplate mongoTemplate = new DefaultMongoTemplate(mongoClient,"axon").withDomainEventsCollection( "events").withSnapshotCollection("snapshot");
        return new MongoEventStorageEngine(
                serializer, null, mongoTemplate, new DocumentPerEventStorageStrategy());
    }

    @Bean
    public SagaStore sagaStore(MongoClient mongoClient, Serializer serializer) {
        DefaultMongoTemplate mongoTemplate = new DefaultMongoTemplate(mongoClient,"axon").withSagasCollection("saga");

        return new MongoSagaStore(mongoTemplate, serializer);
    }
    @Bean
    public SpringAggregateSnapshotterFactoryBean springAggregateSnapshotterFactoryBean() {
        return new SpringAggregateSnapshotterFactoryBean();
    }

    @Bean
    public SnapshotTriggerDefinition snapshotTriggerDefinition(Snapshotter snapshotter) {
        return new EventCountSnapshotTriggerDefinition(
                snapshotter,
                10
        );
    }
}

