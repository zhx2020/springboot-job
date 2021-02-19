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

    @RequestMapping("/count")
    public Result count() {
        return postService.count();
    }

    @RequestMapping("/countById")
    public Result countById(String id) {
        return postService.countById(id);
    }

    @RequestMapping("/findAll")
    public Result findAll(Integer page, Integer size) {
        return postService.findAll(page, size);
    }

    @RequestMapping("/findAllByUserId")
    public Result findAllByUserId(String id, Integer page, Integer size) {
        return postService.findAllByUserId(id, page, size);
    }

    @RequestMapping("/findById")
    public Post findById(String id) {
        return postService.findById(id);
    }

    @RequestMapping("/findByIdWithPage")
    public Post findByIdWithPage(String id, Integer page, Integer size) {
        return postService.findByIdWithPage(id, page, size);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Post post) {
        return postService.add(post);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public Result modify(@RequestBody Post post) {
        return postService.modify(post);
    }

    @RequestMapping(value = "/del")
    public Result del(String id) {
        return postService.del(id);
    }
}
