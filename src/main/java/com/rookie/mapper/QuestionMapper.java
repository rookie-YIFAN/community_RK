package com.rookie.mapper;

import com.rookie.model.Question;
import com.rookie.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title, description, gmt_create, gmt_modified, creator, tag) " +
            "values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    void create(Question question);

    @Select("select * from question limit #{page}, #{size}")
    List<Question> get(Integer page, Integer size);

    @Select("select * from question")
    List<Question> getAll();

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{id} limit #{page}, #{size}")
    List<Question> getByContidition(Integer id, Integer page, Integer size);
}
