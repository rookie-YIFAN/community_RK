package com.rookie.controller;

import com.rookie.model.Question;
import com.rookie.mapper.QuestionMapper;
import com.rookie.mapper.UserMapper;
import com.rookie.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model
    ){
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);


        // 查看登录状态
        //User user = null;
        //Cookie[] cookies = request.getCookies();
        //if (cookies == null) return "publish";
        //for (Cookie cookie : cookies) {
        //    if (cookie.getName().equals("token")){
        //        user =  userMapper.getByToken(cookie.getValue());
        //        request.getSession().setAttribute("user",user);
        //        break;
        //    }
        //}
        User user = (User) request.getSession().getAttribute("user");
        //if (user == null) return "index";
        if (user == null) {
            System.out.println("user == null !");
            model.addAttribute("msg", "error: 用户未登录");
            return "publish";
        }

        // 若已登录
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());

        questionMapper.create(question);
        return "redirect:/publish";
    }
}
