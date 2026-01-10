package com.bite.system.controller;

import com.bite.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysUserController {

    @Autowired
    private ISysUserService sysUserService;

    //登陆成功或失败       bool  true  false     or      int code 1  0
    //如果失败需要失败原因  String meg
    //请求方法 url
    public LoginResult login(String userAccount, String password) {
        return sysUserService.login(userAccount, password);
    }
}
