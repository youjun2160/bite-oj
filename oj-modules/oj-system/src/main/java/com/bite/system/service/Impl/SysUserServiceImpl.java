package com.bite.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bite.system.controller.LoginResult;
import com.bite.system.domain.SysUser;
import com.bite.system.mapper.SysUserMapper;
import com.bite.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public LoginResult login(String userAccount, String password) {
        //通过账号去数据库中查询
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper
                .select(SysUser::getPassword).eq(SysUser::getUserAccount, userAccount));
        LoginResult loginResult = new LoginResult();
        if (sysUser == null) {
            loginResult.setCode(0);
            loginResult.setMsg("当前用户不存在");
            return loginResult;
        }
        if (sysUser.getPassword().equals(password)) {
            loginResult.setCode(1);
            return loginResult;
        }
        loginResult.setCode(0);
        loginResult.setMsg("账号或密码错误");
        return loginResult;
    }
}
