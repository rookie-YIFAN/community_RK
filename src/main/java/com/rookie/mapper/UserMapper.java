package com.rookie.mapper;


import com.rookie.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    //
    //private Integer id;
    //private String name;
    //private String accountId;
    //private String token;
    //private Long gmtCreat;
    //private Long gmtModified;
    @Insert("insert into user (name, account_id, token, gmt_create, gmt_modified, user_source, avatar_url) " +
            "values (#{name}, #{accountId}, #{token}, #{gmtCreat}, #{gmtModified}, #{source}, #{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User getByToken(String token);


    @Select("select * from user where id = #{id}")
    User getById(Integer id);
}
