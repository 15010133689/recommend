package com.recommend.svcdisplay.handler;


import com.recommend.pbase.commmand.CreateUserCommand;
import com.recommend.pbase.event.UserCreatedEvent;
import com.recommend.svcdisplay.aggregate.UserAggregate;
import com.recommend.svcdisplay.dao.UserDao;
import com.recommend.svcdisplay.entity.UserEntity;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class UserEventHandler {
    private static final Logger LOGGER = getLogger(UserEventHandler.class);
    @Autowired
    private Repository<UserAggregate> repository;
    @Autowired
    private EventBus eventBus;
    @Autowired
    private UserDao userDao;

    @CommandHandler
    public void handle(CreateUserCommand command) throws Exception {
        repository.newInstance(() -> new UserAggregate(command));
    }
    @EventHandler
    public void on(UserCreatedEvent event){
        UserEntity user=new UserEntity();
        user.setUserId(event.getUserId());
        user.setUserName(event.getUserName());
        user.setAge(event.getAge());
        userDao.save(user);
    }
}
