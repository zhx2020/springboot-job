package com.zwl.job.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/search")
    public String search() {
        return "search";
    }

    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable String id) {
        return "detail";
    }

    @RequestMapping("/store")
    public String store() {
        return "store";
    }

    @RequestMapping("/discuss")
    public String discuss() {
        return "discuss";
    }

    @RequestMapping("/issue")
    public String issue() {
        return "issue";
    }

    @RequestMapping("/post/{id}")
    public String post(@PathVariable String id) {
        return "post";
    }

    @RequestMapping("/chat")
    public String chat() {
        return "chat";
    }

    @RequestMapping("/center")
    public String center() {
        return "center";
    }

    @RequestMapping("/admin/home")
    public String adminHome() {
        return "admin-home";
    }

    @RequestMapping("/admin/user")
    public String adminUser() {
        return "admin-user";
    }

    @RequestMapping("/admin/post")
    public String adminPost() {
        return "admin-post";
    }

    @RequestMapping("/admin/config")
    public String adminConfig() {
        return "admin-config";
    }
}
