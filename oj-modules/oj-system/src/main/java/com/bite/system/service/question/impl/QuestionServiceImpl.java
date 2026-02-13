package com.bite.system.service.question.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.bite.common.core.domain.TableDataInfo;
import com.bite.system.domain.question.dto.QuestionQueryDTO;
import com.bite.system.domain.question.vo.QuestionVO;
import com.bite.system.mapper.question.QuestionMapper;
import com.bite.system.service.question.IQuestionService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<QuestionVO> list(QuestionQueryDTO questionQueryDTO) {
        PageHelper.startPage(questionQueryDTO.getPageNum(), questionQueryDTO.getPageSize());
        return questionMapper.selectQuestionList(questionQueryDTO);


        //questionVOList == null || questionVOList.isEmpty()
//        if(CollectionUtil.isEmpty(questionVOList)){
//            return TableDataInfo.empty();
//        }
//        new PageInfo<>(questionVOList).getTotal();
//        return TableDataInfo.success(questionVOList, questionVOList.size());
    }
}
