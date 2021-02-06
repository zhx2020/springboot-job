package com.zwl.job.dao;

import com.zwl.job.entity.Post;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface PostDao extends Mapper<Post> {

    List<Post> findAll();

    Post findById(String id);
}
