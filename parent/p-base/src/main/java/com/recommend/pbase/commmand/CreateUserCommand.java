package com.recommend.pbase.commmand;


import lombok.Getter;
import lombok.Setter;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Getter
@Setter
public class CreateUserCommand {
    @TargetAggregateIdentifier
    private String userId;
    private String userName;
    private int age;
    public  CreateUserCommand(String userId,String userName,int age){
        this.userId=userId;
        this.userName=userName;
        this.age=age;
    }

}
