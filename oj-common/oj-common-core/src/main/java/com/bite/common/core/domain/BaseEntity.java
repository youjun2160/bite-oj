package com.bite.common.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BaseEntity {

    private long createBy;

    private LocalDateTime createTime;

    private long updateBy;

    private LocalDateTime updateTime;
}
