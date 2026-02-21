package com.bite.system.service.exam;

import com.bite.common.core.domain.R;
import com.bite.system.domain.exam.dto.ExamAddDTO;
import com.bite.system.domain.exam.dto.ExamQueryDTO;
import com.bite.system.domain.exam.dto.ExamQuestAddDTO;
import com.bite.system.domain.exam.vo.ExamVO;

import java.util.List;

public interface IExamService {

    List<ExamVO> list(ExamQueryDTO examQueryDTO);

    int add(ExamAddDTO examAddDTO);

    boolean questionAdd(ExamQuestAddDTO examQuestAddDTO);
}
