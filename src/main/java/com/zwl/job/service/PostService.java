package com.zwl.job.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.wujun234.uid.impl.CachedUidGenerator;
import com.zwl.job.dao.PostDao;
import com.zwl.job.dao.UserDao;
import com.zwl.job.entity.Job;
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

    public Result count() {
        int amount = postDao.selectAll().size();
        System.out.println(amount);
        return new Result(true, "查询成功", amount);
    }

    public Result countById(String id) {
        Post post = new Post();
        post.setUserId(id);
        int amount = postDao.selectCount(post);
        System.out.println(amount);
        return new Result(true, "查询成功", amount);
    }

    public Result findAll(Integer page, Integer size) {
        List<Post> list = postDao.findAll((page - 1) * size, size);
        return new Result(true, "查询成功", list);
    }

    public Result findAllByUserId(String id, Integer page, Integer size) {
        List<Post> list = postDao.findAllByUserId(id, (page - 1) * size, size);
        return new Result(true, "查询成功", list);
    }

    public Post findById(String id) {
        return postDao.findById(id);
    }

    public Post findByIdWithPage(String id, Integer page, Integer size) {
        return postDao.findByIdWithPage(id, (page - 1) * size, size);
    }

    public Result add(Post post) {
        String postId = String.valueOf(cachedUidGenerator.getUID());
        post.setPostId(postId);
        postDao.insertSelective(post);
        return new Result(true, "发布成功", postId);
    }

    public Result modify(Post post) {
        postDao.updateByPrimaryKeySelective(post);
        return new Result(true, "修改成功", post.getPostId());
    }

    public Result del(String id) {
        postDao.deleteByPrimaryKey(id);
        return new Result(true, "删除成功");
    }
}
