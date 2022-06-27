package com.rookie.controller;

import com.google.common.base.Ascii;
import com.rookie.dto.AccessTokenDTO;
import com.rookie.dto.GiteeUser;
import com.rookie.dto.GithubUser;
import com.rookie.mapper.UserMapper;
import com.rookie.model.User;
import com.rookie.provider.GiteeProvider;
import com.rookie.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;
import sun.awt.image.GifImageDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

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

    @Resource
    private UserMapper userMapper;


    @GetMapping("/callback")
    public String callback(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "state") String state,
            HttpServletRequest request
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

        if(githubUser!=null){
            // 登陆成功
            User user = new User();

            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreat(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreat());
            user.setSource("github");

            userMapper.insert(user);
            System.out.println(user);
            request.getSession().setAttribute("user", githubUser);
            return "redirect:index";
        }else{
            return "redirect:index";
        }
    }

    @GetMapping("/callback/gitee")
    public String callbackGitee(
            @RequestParam(name = "code") String code,
            HttpServletRequest request
    ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        String accessToken = giteeProvider.getAccessToken(accessTokenDTO);
        GiteeUser giteeUser = giteeProvider.getUser(accessToken);
        System.out.println(giteeUser);

        if(giteeUser!=null){
            // 登陆成功
            User user = new User();

            user.setToken(UUID.randomUUID().toString());
            user.setName(giteeUser.getName());
            user.setAccountId(String.valueOf(giteeUser.getId()));
            user.setGmtCreat(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreat());
            user.setSource("gitee");

            userMapper.insert(user);
            System.out.println(user);
            request.getSession().setAttribute("user", giteeUser);


            return "redirect:/index";
        }else{
            return "redirect:/index";
        }

    }

}
