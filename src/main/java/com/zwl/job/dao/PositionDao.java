package com.zwl.job.dao;

import com.zwl.job.entity.Position;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface PositionDao extends Mapper<Position> {
}
