package com.rookie.controller;

import com.rookie.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class HelloWord{

    @Resource
    private StudentService studentService;



    @GetMapping("/hello")
    //@ResponseBody
    public String hello(
            @RequestParam(name = "name") String name, Model model
    ){
        model.addAttribute("name", name);
        return "hello";
    }


    @RequestMapping("/select")
    @ResponseBody
    public Object select(Integer id){
        return studentService.select(id);
    }

    @GetMapping("/dubbo")
    @ResponseBody
    public String dubbo(){
        return "dubbo";
    }
}
