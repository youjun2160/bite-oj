package com.bite.system.service.question;

import com.bite.common.core.domain.TableDataInfo;
import com.bite.system.domain.question.dto.QuestionQueryDTO;

public interface IQuestionService {
    TableDataInfo list(QuestionQueryDTO questionQueryDTO);
}
