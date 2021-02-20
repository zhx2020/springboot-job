package com.zwl.job.dao;

import com.zwl.job.entity.Store;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface StoreDao extends Mapper<Store> {

    @Select("select * from tab_store where user_id = #{id} order by create_time desc")
    List<Store> findAllByUserId(String id);

    @Select("select * from tab_store where user_id = #{id} order by create_time desc limit #{m},#{n}")
    List<Store> findAllByUserIdWithPage(String id, Integer m, Integer n);

}