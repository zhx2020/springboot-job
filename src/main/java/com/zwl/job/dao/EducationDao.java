package com.zwl.job.dao;

import com.zwl.job.entity.Education;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface EducationDao extends Mapper<Education> {
}
