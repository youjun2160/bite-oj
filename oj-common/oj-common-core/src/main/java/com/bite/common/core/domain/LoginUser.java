package com.bite.common.core.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUser {

    private String nickName; //用户昵称

    private Integer identity;  //1表示普通用户   2表示管理员用户

}
