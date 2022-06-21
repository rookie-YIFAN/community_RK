package com.rookie.mapper;

import com.rookie.model.Student;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface StudentMapper {
    Student select(Integer id);
}
