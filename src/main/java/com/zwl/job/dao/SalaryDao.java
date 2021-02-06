package com.zwl.job.dao;

import com.zwl.job.entity.Salary;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface SalaryDao extends Mapper<Salary> {
}
