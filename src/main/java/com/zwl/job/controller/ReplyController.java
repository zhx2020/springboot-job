package com.zwl.job.controller;

import com.zwl.job.entity.Post;
import com.zwl.job.entity.Reply;
import com.zwl.job.entity.Result;
import com.zwl.job.service.PostService;
import com.zwl.job.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @RequestMapping("/findById")
    public Result findById(String id) {
        return replyService.findById(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Reply reply) {
        System.out.println(reply);
        return replyService.add(reply);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public Result modify(@RequestBody Reply reply) {
        System.out.println(reply);
        return replyService.modify(reply);
    }

    @RequestMapping("/del")
    public Result del(String id) {
        return replyService.del(id);
    }
}
