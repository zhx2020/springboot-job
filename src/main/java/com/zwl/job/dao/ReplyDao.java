package com.zwl.job.dao;

import com.zwl.job.entity.Reply;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ReplyDao extends Mapper<Reply> {
}
