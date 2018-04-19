package com.recommend.svcdisplay.saga;

import com.recommend.pbase.event.UserCreatedEvent;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

@Saga
public class UserSaga {
    private static final Logger LOGGER = getLogger(UserSaga.class);
    @TargetAggregateIdentifier
    private String userId;

    @StartSaga
    @SagaEventHandler(associationProperty = "userId")
    public void handle(UserCreatedEvent event){
        this.userId = event.getUserId();
//        UserEntity user=new UserEntity();
//        user.setUserId(event.getUserId());
//        user.setUserName(event.getUserName());
//        user.setAge(event.getAge());
//        uerDao.save(user);
    }
}
