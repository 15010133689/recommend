package com.recommend.pbase.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreatedEvent {
    private  String userId;
    private  String userName;
    private int age;
    public UserCreatedEvent() {
    }
    public UserCreatedEvent(String userId,String userName,int age){
        this.userId=userId;
        this.userName=userName;
        this.age=age;
    }
}
