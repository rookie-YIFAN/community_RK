package com.rookie.model;


import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String description;
    private String title;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private User user;
}
