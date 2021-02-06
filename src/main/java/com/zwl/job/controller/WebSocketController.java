package com.zwl.job.controller;

import com.alibaba.fastjson.JSON;
import com.zwl.job.entity.Data;
import com.zwl.job.ws.WebSocketServer;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/my")
public class WebSocketController {

    // 页面请求
    @GetMapping("/count")
    public String count() {
        return "当前在线人数：" + WebSocketServer.webSocketSet.size();
    }

    // 推送数据接口
    @RequestMapping(value = "/send/{sid}", method = RequestMethod.POST)
    public String pushToWeb(@PathVariable String sid, @RequestBody Data data) {
        try {
            WebSocketServer.sendInfo(sid, JSON.toJSONString(data));
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "向id=" + sid + "的用户发送:" + JSON.toJSONString(data);
    }
}