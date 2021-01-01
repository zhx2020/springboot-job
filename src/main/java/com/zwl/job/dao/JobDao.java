package com.zwl.job.dao;

import com.zwl.job.entity.Job;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface JobDao extends Mapper<Job> {
}
