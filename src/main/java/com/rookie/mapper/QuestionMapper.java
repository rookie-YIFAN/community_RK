package com.rookie.mapper;

import com.rookie.model.Question;
import com.rookie.model.QuestionExample;
import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface QuestionMapper {
    long countByExample(QuestionExample example);

    int deleteByExample(QuestionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Question record);

    int insertSelective(Question record);

    List<Question> selectByExampleWithBLOBs(QuestionExample example);

    List<Question> selectByExample(QuestionExample example);

    Question selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Question record, @Param("example") QuestionExample example);

    int updateByExampleWithBLOBs(@Param("record") Question record, @Param("example") QuestionExample example);

    int updateByExample(@Param("record") Question record, @Param("example") QuestionExample example);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKeyWithBLOBs(Question record);

    int updateByPrimaryKey(Question record);

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

    @Select("select * from question where id = #{id}")
    Question getById(Integer id);

    @Update("update question set title = #{title}, description = #{description}, gmt_modified = #{gmtModified}, tag = #{tag}")
    int update(Question question);
}
