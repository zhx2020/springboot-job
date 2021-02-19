package com.zwl.job.controller;

import com.zwl.job.entity.Friend;
import com.zwl.job.entity.Result;
import com.zwl.job.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @RequestMapping("/findAll")
    public List<Friend> findAll() {
        return friendService.findAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Friend friend) {
        return friendService.add(friend);
    }

    @RequestMapping("/del")
    public Result del(String userId, String friendId) {
        return friendService.del(userId, friendId);
    }
}
