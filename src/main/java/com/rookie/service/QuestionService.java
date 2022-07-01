package com.rookie.service;


import com.rookie.dto.PaginationDTO;
import com.rookie.dto.QuestionDTO;
import com.rookie.mapper.QuestionMapper;
import com.rookie.mapper.UserMapper;
import com.rookie.model.Question;
import com.rookie.model.User;
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
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.getById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
}
