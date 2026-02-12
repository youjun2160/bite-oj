package com.bite.system.domain.question.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionQueryDTO {

    private Integer difficulty;

    private String title;

    private Integer pageSize = 10;

    private Integer pageNum = 1;
}
