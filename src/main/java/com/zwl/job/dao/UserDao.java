package com.zwl.job.dao;

import com.zwl.job.entity.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserDao extends Mapper<User> {

    List<User> getAllUsers();

    List<User> findAll();

    User findById(String id);

    User findFriendById(String id);
}
