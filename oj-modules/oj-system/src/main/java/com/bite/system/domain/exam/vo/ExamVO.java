package com.bite.system.domain.exam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExamVO {
    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer status;

    private String createName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
