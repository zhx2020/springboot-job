package com.zwl.job.controller;

import com.zwl.job.entity.Result;
import com.zwl.job.entity.Store;
import com.zwl.job.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/store")
@RestController
public class StoreController {

    @Autowired
    private StoreService storeService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Store store) {
        return storeService.insert(store);
    }

    @RequestMapping("/findAll")
    public Result findAll() {
        return storeService.findAll();
    }

    @RequestMapping("/findById")
    public Result findById(String id) {
        return storeService.findById(id);
    }

    @RequestMapping("/findByIdWithPage")
    public Result findByIdWithPage(String id, Integer page, Integer size) {
        return storeService.findByIdWithPage(id, page, size);
    }

    @RequestMapping("/del")
    public Result del(String userId, String jobId) {
        return storeService.del(userId, jobId);
    }
}
