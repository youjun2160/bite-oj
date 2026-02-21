package com.bite.system.service.exam.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bite.common.core.domain.R;
import com.bite.common.core.enums.ResultCode;
import com.bite.common.security.exception.ServiceException;
import com.bite.system.domain.exam.Exam;
import com.bite.system.domain.exam.ExamQuestion;
import com.bite.system.domain.exam.dto.ExamAddDTO;
import com.bite.system.domain.exam.dto.ExamQueryDTO;
import com.bite.system.domain.exam.dto.ExamQuestAddDTO;
import com.bite.system.domain.exam.vo.ExamVO;
import com.bite.system.domain.question.Question;
import com.bite.system.mapper.exam.ExamMapper;
import com.bite.system.mapper.exam.ExamQuestionMapper;
import com.bite.system.mapper.question.QuestionMapper;
import com.bite.system.service.exam.IExamService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ExamServiceImpl extends ServiceImpl<ExamQuestionMapper, ExamQuestion> implements IExamService {

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private ExamQuestionMapper examQuestionMapper;

    @Override
    public List<ExamVO> list(ExamQueryDTO examQueryDTO) {
        PageHelper.startPage(examQueryDTO.getPageNum(), examQueryDTO.getPageSize());
        return examMapper.selectExamList(examQueryDTO);
    }

    @Override
    public int add(ExamAddDTO examAddDTO) {
        List<Exam> examList = examMapper
                .selectList(new LambdaQueryWrapper<Exam>().eq(Exam::getTitle, examAddDTO.getTitle()));
        if (CollectionUtil.isNotEmpty(examList)) {
            throw new ServiceException(ResultCode.FAILED_ALREADY_EXISTS);
        }
        if (examAddDTO.getStartTime().isBefore(LocalDateTime.now())) {
            throw new ServiceException(ResultCode.EXAM_START_TIME_BEFORE_CURRENT_TIME);
        }
        if (examAddDTO.getStartTime().isAfter(examAddDTO.getEndTime())) {
            throw new ServiceException(ResultCode.EXAM_START_TIME_AFTER_END_TIME);
        }

        Exam exam = new Exam();
        BeanUtil.copyProperties(examAddDTO, exam);
        return examMapper.insert(exam);
    }

    @Override
    public boolean questionAdd(ExamQuestAddDTO examQuestAddDTO) {
        Exam exam = getExam(examQuestAddDTO);
        Set<Long> questionIdSet = examQuestAddDTO.getQuestionId();
        if (CollectionUtil.isEmpty(questionIdSet)) {
            return true;
        }
        List<Question> questionList = questionMapper.selectBatchIds(questionIdSet);
        if(CollectionUtil.isEmpty(questionList) || questionList.size() < questionIdSet.size()) {
            throw new ServiceException(ResultCode.EXAM_QUESTION_NOT_EXISTS);
        }
        return saveExamQuestion(examQuestAddDTO, questionIdSet);
    }

    private boolean saveExamQuestion(ExamQuestAddDTO examQuestAddDTO, Set<Long> questionIdSet) {
        int num = 1;
        List<ExamQuestion> examQuestionList = new ArrayList<>();
        for(Long questionId : questionIdSet){
            ExamQuestion examQuestion = new ExamQuestion();
            examQuestion.setExamId(examQuestAddDTO.getExamId());
            examQuestion.setQuestionId(questionId);
            examQuestion.setQuestionOrder(num++);
            examQuestionList.add(examQuestion);
//            examQuestionMapper.insert(examQuestion);
        }
        return saveBatch(examQuestionList);
    }

    private Exam getExam(ExamQuestAddDTO examQuestAddDTO) {
        Exam exam = examMapper.selectById(examQuestAddDTO.getExamId());
        if(exam == null){
            throw new ServiceException(ResultCode.EXAM_NOT_EXISTS);
        }
        return exam;
    }
}
