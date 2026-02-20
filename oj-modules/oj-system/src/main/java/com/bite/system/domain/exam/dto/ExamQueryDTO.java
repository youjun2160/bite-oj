package com.bite.system.domain.exam.dto;

import com.bite.common.core.domain.PageQueryDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExamQueryDTO extends PageQueryDTO {

    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
