package com.rookie.controller;

import com.google.common.base.Ascii;
import com.rookie.dto.AccessTokenDTO;
import com.rookie.dto.GiteeUser;
import com.rookie.dto.GithubUser;
import com.rookie.provider.GiteeProvider;
import com.rookie.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.awt.image.GifImageDecoder;

import javax.annotation.Resource;

@Controller
public class AuthController {
    @Resource
    private GithubProvider githubProvider;

    @Resource
    private GiteeProvider giteeProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;


    @GetMapping("/callback")
    public String callback(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "state") String state
    ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        System.out.println(accessTokenDTO.toString());
        String token = githubProvider.getAccessToken(accessTokenDTO);
        System.out.println("token:" + token);
        GithubUser githubUser = githubProvider.getUser(token);
        System.out.println(githubUser);
        return "index";
    }

    @GetMapping("/callback/gitee")
    public String callbackGitee(
            @RequestParam(name = "code") String code
    ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        String accessToken = giteeProvider.getAccessToken(accessTokenDTO);
        GiteeUser giteeUser = giteeProvider.getUser(accessToken);
        System.out.println(giteeUser);
        return "index";
    }

}
