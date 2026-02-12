package com.bite.system.service.question.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.bite.common.core.domain.TableDataInfo;
import com.bite.system.domain.question.dto.QuestionQueryDTO;
import com.bite.system.domain.question.vo.QuestionVO;
import com.bite.system.mapper.question.QuestionMapper;
import com.bite.system.service.question.IQuestionService;

import java.util.List;

public class QuestionServiceImpl implements IQuestionService {

    private QuestionMapper questionMapper;

    @Override
    public TableDataInfo list(QuestionQueryDTO questionQueryDTO) {
        List<QuestionVO> questionVOList = questionMapper.selectQuestionList(questionQueryDTO);
        //questionVOList == null || questionVOList.isEmpty()
        if(CollectionUtil.isEmpty(questionVOList)){
            return TableDataInfo.empty();
        }
        return TableDataInfo.success(questionVOList, questionVOList.size());
    }
}
