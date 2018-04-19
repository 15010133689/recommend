package com.recommend.svcdisplay.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "reco_user")
@DynamicInsert
@DynamicUpdate
@Cacheable
@Getter
@Setter
public class UserEntity {
    @Id
    @Column(name = "userid")
    private String userId;
    @Column(name = "username")
    private  String userName;
    @Column(name = "age")
    private int age;
}
