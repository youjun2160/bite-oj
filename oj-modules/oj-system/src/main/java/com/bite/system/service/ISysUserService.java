package com.bite.system.service;

import com.bite.common.core.domain.R;
import com.bite.common.core.service.BaseService;
import com.bite.system.controller.LoginResult;
import com.bite.system.domain.SysUser;
import com.bite.system.domain.SysUserSaveDTO;

public interface ISysUserService {
    R<String> login(String userAccount, String password);

    R<Void> add(SysUserSaveDTO sysUserSaveDTO);
}
