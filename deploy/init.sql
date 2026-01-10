# 账号   密码

#表名小写   多个单词下划线隔开   全部以tb开头

create table tb_sys_user (

    user_id      bigint unsigned not null comment '用户id（主键）',
    user_account varchar(20) NOT NULL comment '账号',
    password     varchar(20) NOT NULL comment '密码',
    create_by    bigint unsigned not null comment '创建人',
    create_time  datetime not null comment '创建时间',
    update_by    bigint unsigned comment '更新人',
    update_time  datetime comment '更新时间',





    primary key (`user_id`),
    unique key `idx_user_account` (`user_account`)
)