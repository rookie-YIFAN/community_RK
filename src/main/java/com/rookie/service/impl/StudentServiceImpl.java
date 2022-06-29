package com.rookie.service.impl;

import com.rookie.service.StudentService;
import com.rookie.mapper.StudentMapper;
import com.rookie.model.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentMapper studentMapper;

    /**
     * @Transactional: 表示方法的有事务支持
     *       默认：使用库的隔离级别， REQUIRED 传播行为； 超时时间  -1
     *       抛出运行时异常，回滚事务
     */
    @Transactional
    @Override
    public Student select(Integer id) {
        return studentMapper.select(id);
    }
}
