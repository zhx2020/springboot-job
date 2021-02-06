package com.zwl.job.dao;

import com.zwl.job.entity.Year;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface YearDao extends Mapper<Year> {
}
