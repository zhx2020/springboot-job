package com.zwl.job.controller;

import com.zwl.job.entity.Result;
import com.zwl.job.entity.User;
import com.zwl.job.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findAll")
    public List<User> findAll() {
        return userService.findAll();
    }

    @RequestMapping("/findById")
    public User findAll(String id) {
        return userService.findById(id);
    }

    @RequestMapping("/findFriendById")
    public User findFriendAll(String id) {
        return userService.findFriendById(id);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(String userId, String userPass) {
        return userService.login(userId, userPass);
    }

    @RequestMapping("/check")
    public Result check(HttpServletRequest request) {
        String token = request.getHeader("token");
        return userService.check(token);
    }

    @RequestMapping("/send")
    public Result send(String phone) throws Exception {
        return userService.send(phone);
    }

    @RequestMapping("/verify")
    public Result verify(String phone, String code) {
        return userService.verify(phone, code);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register(@RequestBody User user) {
        return userService.register(user);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public Result modify(@RequestBody User user) {
        return userService.modify(user);
    }

    @RequestMapping(value = "/del")
    public Result del(String id) {
        return userService.del(id);
    }
}
