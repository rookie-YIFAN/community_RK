package com.rookie.service;


import com.rookie.dto.PaginationDTO;
import com.rookie.dto.QuestionDTO;
import com.rookie.exception.CustomizeException;
import com.rookie.exception.EnumErrorCode;
import com.rookie.mapper.QuestionExtMapper;
import com.rookie.mapper.QuestionMapper;
import com.rookie.mapper.UserMapper;
import com.rookie.model.Question;
import com.rookie.model.QuestionExample;
import com.rookie.model.User;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private QuestionExtMapper questionExtMapper;

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            // 创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.create(question);
        } else {
            // 更新
            question.setGmtModified(System.currentTimeMillis());
            int res = questionMapper.update(question);
            if (res != 1){
                throw new CustomizeException(EnumErrorCode.QUESTION_NOT_FOUND);
            }
            //Question dbQuestion = questionMapper.getById(question.getId());
            //if (dbQuestion == null) {
            //    throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            //}
            //
            //if (dbQuestion.getCreator().longValue() != question.getCreator().longValue()) {
            //    throw new CustomizeException(CustomizeErrorCode.INVALID_OPERATION);
            //}

            //Question updateQuestion = new Question();
            //updateQuestion.setGmtModified(System.currentTimeMillis());
            //updateQuestion.setTitle(question.getTitle());
            //updateQuestion.setDescription(question.getDescription());
            //updateQuestion.setTag(question.getTag());
            //QuestionExample example = new QuestionExample();
            //example.createCriteria()
            //        .andIdEqualTo(question.getId());
            //int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            //if (updated != 1) {
            //    //throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            //}

        }
    }

    public PaginationDTO get(Integer page, Integer size) {
        List<Question> questionList = questionMapper.get((page-1)*size, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for(Question question : questionList){
            User user = userMapper.getById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        System.out.println(questionDTOS);

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setData(questionDTOS);

        Integer totalCount = questionMapper.count();

        Integer totalPage; // 总页数
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage, page);
        //Integer offset = page < 1 ? 0 : size * (page - 1);
        //questionQueryDTO.setSize(size);
        //questionQueryDTO.setPage(offset);


        return paginationDTO;
    }

    public List<QuestionDTO> getQuestionDTO() {
        List<Question> questionList = questionMapper.getAll();
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for(Question question : questionList){
            User user = userMapper.getById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        System.out.println(questionDTOS);


        //Integer offset = page < 1 ? 0 : size * (page - 1);
        //questionQueryDTO.setSize(size);
        //questionQueryDTO.setPage(offset);


        return questionDTOS;
    }

    public PaginationDTO getQuestionDTOByCondition(Integer id, Integer offset, Integer size) {
        List<Question> questionList = questionMapper.getByContidition(id, offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for(Question question : questionList){
            User user = userMapper.getById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        System.out.println(questionDTOS);

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setData(questionDTOS);
        //Integer offset = page < 1 ? 0 : size * (page - 1);
        //questionQueryDTO.setSize(size);
        //questionQueryDTO.setPage(offset);


        return paginationDTO;
    }

    public QuestionDTO getById(Integer id){
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null){
            throw new CustomizeException(EnumErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.getById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }


    public void increaseViewCount(Integer id) {
        //Question question = questionMapper.selectByPrimaryKey(id);
        //Question updateQuestion = new Question();
        //updateQuestion.setViewCount(question.getViewCount()+1);
        //QuestionExample example = new QuestionExample();
        //example.createCriteria().andIdEqualTo(id);
        Question updateQuestion = new Question();
        updateQuestion.setId(id);
        updateQuestion.setViewCount(1);
        questionExtMapper.incView(updateQuestion);
    }
}
