package com.zwl.job.controller;

import com.zwl.job.ws.MyWebSocket;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/hh")
    public int[] test() {
        System.out.println();
        return new int[]{1, 2, 3};
    }

    @RequestMapping("/broadcast")
    public void broadcast(String str){
        MyWebSocket.broadcast(str);
    }
}
