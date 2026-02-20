package com.bite.system.service.exam;

import com.bite.system.domain.exam.dto.ExamQueryDTO;
import com.bite.system.domain.exam.vo.ExamVO;

import java.util.List;

public interface IExamService {

    List<ExamVO> list(ExamQueryDTO examQueryDTO);
}
