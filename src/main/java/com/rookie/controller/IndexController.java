package com.rookie.controller;

import com.rookie.dto.PaginationDTO;
import com.rookie.dto.QuestionDTO;
import com.rookie.mapper.QuestionMapper;
import com.rookie.mapper.UserExtMapper;
import com.rookie.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {
    @Resource
    private UserExtMapper userExtMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name="page", defaultValue = "1") Integer page,
                        @RequestParam(name="value", defaultValue = "5") Integer size){
        //Cookie[] cookies = request.getCookies();
        //boolean flag = true;
        //if (cookies == null) return "index";
        //for (Cookie cookie : cookies) {
        //    if (cookie.getName().equals("token")){
        //        System.out.println("cookie "+ cookie);
        //        String token = cookie.getValue();
        //        User user = userMapper.getByToken(token);
        //        if (user != null) request.getSession().setAttribute("user", user);
        //        System.out.println(user);
        //        flag = false;
        //        break;
        //    }
        //}
        //if (flag) request.getSession().removeAttribute("user");

        //User user = (User) request.getSession().getAttribute("user");
        //if (user == null) return "index";
        System.out.println("-----------------------------------------------------------------------------");
        PaginationDTO pagination = questionService.get(page, size);
        model.addAttribute("pagination", pagination);

        return "index";
    }

    @GetMapping("/index")
    public String indexPage(HttpServletRequest request,
                            Model model,
                            @RequestParam(name="page", defaultValue = "1") Integer page,
                            @RequestParam(name="value", defaultValue = "5") Integer size){
        //Cookie[] cookies = request.getCookies();
        //boolean flag = true;
        //if (cookies == null) return "index1";
        //for (Cookie cookie : cookies) {
        //    if (cookie.getName().equals("token")){
        //        System.out.println("cookie "+ cookie);
        //        String token = cookie.getValue();
        //        User user = userMapper.getByToken(token);
        //        if (user != null) request.getSession().setAttribute("user", user);
        //        flag = false;
        //        break;
        //    }
        //}
        //if (flag) request.getSession().removeAttribute("user");

        //User user = (User) request.getSession().getAttribute("user");
        //if (user == null) return "index";

        List<QuestionDTO> QuestionDTO = questionService.getQuestionDTO();
        System.out.println(QuestionDTO);
        model.addAttribute("QuestionDTO", QuestionDTO);
        return "index1";

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         HttpSession session,
                         SessionStatus sessionStatus,
                         Model model){
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return "redirect:/";
        //for (Cookie cookie : cookies) {
        //    cookie.setMaxAge(0);
        //}

        Cookie token = new Cookie("token", "");
        Cookie JSESSIONID = new Cookie("JSESSIONID", "");

        token.setMaxAge(0);
        JSESSIONID.setMaxAge(0);

        response.addCookie(token);
        response.addCookie(JSESSIONID);



        // PaginationDTO pagination = questionService.get(page, size);
        // model.addAttribute("pagination", pagination);

        return "redirect:/";
    }
}
