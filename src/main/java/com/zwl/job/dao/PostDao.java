package com.zwl.job.dao;

import com.zwl.job.entity.Post;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface PostDao extends Mapper<Post> {

    List<Post> findAll(@Param("m") Integer m, @Param("n") Integer n);

    List<Post> findAllByUserId(@Param("id") String id, @Param("m") Integer m, @Param("n") Integer n);

    Post findById(String id);

    Post findByIdWithPage(@Param("id") String id, @Param("m") Integer m, @Param("n") Integer n);
}
