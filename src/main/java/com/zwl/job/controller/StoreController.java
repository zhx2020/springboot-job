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

    @RequestMapping("/del")
    public Result del(String id) {
        return storeService.del(id);
    }
}
