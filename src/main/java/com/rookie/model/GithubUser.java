package com.rookie.model;

import lombok.Data;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
