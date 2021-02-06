package com.zwl.job.service;

import com.github.wujun234.uid.impl.CachedUidGenerator;
import com.zwl.job.dao.PostDao;
import com.zwl.job.dao.UserDao;
import com.zwl.job.entity.Post;
import com.zwl.job.entity.Result;
import com.zwl.job.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CachedUidGenerator cachedUidGenerator;

    public List<Post> findAll() {
        return postDao.findAll();
    }

    public Post findById(String id) {
        return postDao.findById(id);
    }

    public Result add(Post post) {
        String postId = String.valueOf(cachedUidGenerator.getUID());
        post.setPostId(postId);
        postDao.insertSelective(post);
        return new Result(true, "发布成功", postId);
    }

    public Result del(String id) {
        postDao.deleteByPrimaryKey(id);
        return new Result(true, "删除成功");
    }
}
