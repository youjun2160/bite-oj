package com.bite.system.domain.exam.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class ExamQuestAddDTO {

    private Long examId;

    private LinkedHashSet<Long> questionId;
}
