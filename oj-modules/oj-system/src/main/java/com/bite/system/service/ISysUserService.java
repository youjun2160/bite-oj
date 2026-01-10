package com.bite.system.service;

import com.bite.system.controller.LoginResult;

public interface ISysUserService {
    LoginResult login(String userAccount, String password);
}
