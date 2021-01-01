package com.zwl.job.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/store")
    public String store() {
        return "store";
    }

    @RequestMapping("/chart")
    public String chart() {
        return "chart";
    }

    @RequestMapping("/config")
    public String config() {
        return "config";
    }

    @RequestMapping("/search")
    public String search() {
        return "search";
    }

    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable String id) {
        return "detail";
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }
}
