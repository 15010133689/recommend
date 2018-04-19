package com.recommend.svcdisplay.aggregate;


import com.recommend.pbase.commmand.CreateUserCommand;
import com.recommend.pbase.event.UserCreatedEvent;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@Getter
@Setter
public class UserAggregate{
    @AggregateIdentifier
    private String userId;
    private  String userName;
    private int age;

    public UserAggregate(){}

    public UserAggregate(CreateUserCommand command){
        apply(new UserCreatedEvent(command.getUserId(),command.getUserName(),command.getAge()));
    }
    @EventHandler
    public void on(UserCreatedEvent event){
        this.userId=event.getUserId();
        this.userName=event.getUserName();
        this.age=event.getAge();
    }

}
