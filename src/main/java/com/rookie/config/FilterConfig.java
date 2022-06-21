package com.rookie.config;

import com.rookie.utils.EncodingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;

@Configuration
public class FilterConfig{
    //@Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new EncodingFilter());
        bean.addUrlPatterns("/*");
        System.out.println("filter 注册");
        return bean;

    }
}
