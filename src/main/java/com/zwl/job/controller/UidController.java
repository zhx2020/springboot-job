package com.zwl.job.controller;

import com.zwl.job.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UidController {

    @Autowired
    private IdGenerator idGenerator;

    @GetMapping("/testGet")
    public Long testGet(){
        return idGenerator.nextId();
    }

    @GetMapping("/testParse/{uid}")
    public String testParse(@PathVariable("uid") Long uid){
        return idGenerator.parse(uid);
    }
}
