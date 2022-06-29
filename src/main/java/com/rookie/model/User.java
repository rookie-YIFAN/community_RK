package com.rookie.model;


import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreat;
    private Long gmtModified;
    private String source;
    private String avatarUrl;

}
