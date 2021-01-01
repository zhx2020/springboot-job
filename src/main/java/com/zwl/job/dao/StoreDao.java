package com.zwl.job.dao;

import com.zwl.job.entity.Store;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface StoreDao extends Mapper<Store> {
}
