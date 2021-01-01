package com.zwl.job.controller;

import com.zwl.job.entity.Result;
import com.zwl.job.service.ConfigService;
import com.zwl.job.ws.MyWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @RequestMapping("/start")
    public Result start() {
        return configService.start();
    }

    @RequestMapping("/stop")
    public Result stop() {
        return configService.stop();
    }

    @RequestMapping("/status")
    public Result status() {
        return configService.status();
    }

    @RequestMapping("/sync")
    public Result sync() {
        return configService.sync();
    }
}
