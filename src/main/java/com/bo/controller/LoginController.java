package com.bo.controller;

import com.bo.domain.ResponseResult;
import com.bo.domain.User;
import com.bo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping ("/user/login")
    public ResponseResult login(@RequestBody User user){
        //登录
        return loginService.login(user);
    }

    @RequestMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
}
