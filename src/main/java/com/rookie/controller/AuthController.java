package com.rookie.controller;

import com.rookie.dto.AccessTokenDTO;
import com.rookie.model.GiteeUser;
import com.rookie.model.GithubUser;
import com.rookie.mapper.UserExtMapper;
import com.rookie.model.User;
import com.rookie.model.UserExt;
import com.rookie.provider.GiteeProvider;
import com.rookie.provider.GithubProvider;
import com.rookie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@Slf4j
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
    private UserExtMapper userExtMapper;

    @Resource
    private UserService userService;

    @GetMapping("/callback")
    public String callback(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "state") String state,
            HttpServletRequest request,
            HttpServletResponse response
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

            String token1 = UUID.randomUUID().toString();
            user.setToken(token1);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setUserSource("github");

            userExtMapper.insert(user);
            //request.getSession().setAttribute("user", user);
            Cookie cookie = new Cookie("token", token1);
            cookie.setDomain("localhost");
            response.addCookie(cookie);

            System.out.println(user);
            return "redirect:index";
        }else{
            //log.error("callback get github user, {}", githubUser);
            return "redirect:index";
        }
    }

    @GetMapping("/callback/gitee")
    public String callbackGitee(
            @RequestParam(name = "code") String code,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        String accessToken = giteeProvider.getAccessToken(accessTokenDTO);
        GiteeUser giteeUser = giteeProvider.getUser(accessToken);
        System.out.println(giteeUser);

        if(giteeUser!=null){
            // 登陆成功

            String accountId = String.valueOf(giteeUser.getId());

            User user = userService.createOrUpdate(accountId, giteeUser);

            //String token = UUID.randomUUID().toString();
            //user.setToken(token);
            //user.setName(giteeUser.getName());
            //user.setAccountId(String.valueOf(giteeUser.getId()));
            //user.setGmtCreat(System.currentTimeMillis());
            //user.setGmtModified(user.getGmtCreat());
            //user.setSource("gitee");
            //user.setAvatarUrl(giteeUser.getAvatarUrl());





            System.out.println(user);

            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        cookie.setMaxAge(0);
                        break;
                    }
                }
            }


            Cookie cookie = new Cookie("token", user.getToken());
            //cookie.setDomain("43.138.201.141");
            //cookie.setDomain("localhost");
            cookie.setPath("/");
            response.addCookie(cookie);

            System.out.println("SET TOKEN ------------------------------------------------------------");

            return "redirect:/";
        }else{
            //log.error("callback get github user, {}", giteeUser);

            return "redirect:/";
        }

    }

}
