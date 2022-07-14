package com.rookie.controller;

import com.rookie.cache.TagCache;
import com.rookie.model.Question;
import com.rookie.mapper.QuestionMapper;
import com.rookie.mapper.UserExtMapper;
import com.rookie.model.User;
import com.rookie.model.UserExt;
import com.rookie.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private QuestionService questionService;

    @Resource
    private UserExtMapper userExtMapper;

    @GetMapping("/publish/{id}")
    public String question(@PathVariable(name = "id") String id, Model model) {
        //Long questionId = null;
        //try {
        //    questionId = Long.parseLong(id);
        //} catch (NumberFormatException e) {
        //    throw new CustomizeException(CustomizeErrorCode.INVALID_INPUT);
        //}
        //QuestionDTO questionDTO = questionService.getById(questionId);
        //List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        //List<CommentDTO> comments = commentService.listByTargetId(questionId, CommentTypeEnum.QUESTION);
        //累加阅读数
        //questionService.incView(questionId);
        Question question = questionMapper.selectByPrimaryKey(Integer.parseInt(id));
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", Integer.parseInt(id));
        model.addAttribute("tags", TagCache.get());
        System.out.println(TagCache.get());
        return "publish";
    }


    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());

        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description" , required = false) String description,
            @RequestParam(value = "tag" , required = false) String tag,
            @RequestParam(value = "id" , required = false) Integer id,
            HttpServletRequest request,
            Model model
    ){
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());


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
        if (StringUtils.isBlank(title)) {
            model.addAttribute("msg", "标题不能为空");
            return "publish";
        }

        if (StringUtils.length(title) > 50) {
            model.addAttribute("msg", "标题最多 50 个字符");
            return "publish";
        }
        if (StringUtils.isBlank(description)) {
            model.addAttribute("msg", "问题补充不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(tag)) {
            model.addAttribute("msg", "标签不能为空");
            return "publish";
        }

        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)) {
            model.addAttribute("msg", "输入非法标签:" + invalid);
            return "publish";
        }

        // 若已登录
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        //questionMapper.create(question);
        return "redirect:/";
    }
}
