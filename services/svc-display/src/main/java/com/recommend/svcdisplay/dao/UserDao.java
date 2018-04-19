package com.recommend.svcdisplay.dao;

import com.recommend.svcdisplay.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<UserEntity,String> {
    @Query(value = "select * from reco_user us where us.userid= :userId", nativeQuery = true)
    UserEntity findUserById(@Param(value = "userId") String userId);

}
