package com.zwl.job.controller;

import com.zwl.job.entity.Post;
import com.zwl.job.entity.Result;
import com.zwl.job.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping("/findAll")
    public List<Post> findAll() {
        return postService.findAll();
    }

    @RequestMapping("/findById")
    public Post findById(String id) {
        return postService.findById(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Post post) {
        return postService.add(post);
    }

    @RequestMapping(value = "/del")
    public Result del(String id) {
        return postService.del(id);
    }
}
