package com.recommend.webpages.controller;

import com.recommend.pbase.commmand.CreateUserCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    CommandGateway commandGateway;
    @RequestMapping(value = "/createUser",method = RequestMethod.GET)
    public String createUser(@RequestParam("userName")String userName,@RequestParam("age")int age){
        String userId=UUID.randomUUID().toString();
        commandGateway.sendAndWait(new CreateUserCommand(userId,userName,age));
        return "success";
    }
    @Value("${spring.rabbitmq.host}")
    String foo;
    @Value("${zidingyi}")
    String zidingyi;
    @RequestMapping(value = "/hi")
    public String hi(){
        return foo+"----"+zidingyi;
    }
}
