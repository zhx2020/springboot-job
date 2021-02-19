package com.zwl.job.service;

import com.github.wujun234.uid.impl.CachedUidGenerator;
import com.zwl.job.dao.PostDao;
import com.zwl.job.dao.ReplyDao;
import com.zwl.job.dao.UserDao;
import com.zwl.job.entity.Post;
import com.zwl.job.entity.Reply;
import com.zwl.job.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {

    @Autowired
    private ReplyDao replyDao;

    @Autowired
    private CachedUidGenerator cachedUidGenerator;

    public Result findById(String id) {
        return new Result(true, "查询成功", replyDao.selectByPrimaryKey(id));
    }

    public Result modify(Reply reply) {
        replyDao.updateByPrimaryKeySelective(reply);
        return new Result(true, "修改成功", reply.getReplyId());
    }

    public Result add(Reply reply) {
        String replyId = String.valueOf(cachedUidGenerator.getUID());
        reply.setReplyId(replyId);
        replyDao.insertSelective(reply);
        return new Result(true, "回复成功", replyId);
    }

    public Result del(String id) {
        replyDao.deleteByPrimaryKey(id);
        return new Result(true, "删除成功");
    }
}
