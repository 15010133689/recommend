package com.recommend.svcdisplay.config;

import com.recommend.svcdisplay.aggregate.UserAggregate;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfiguration {
    @Bean
    public Repository<UserAggregate> repository(EventStore eventStore){
        return new EventSourcingRepository<UserAggregate>(UserAggregate.class, eventStore);
    }
}
