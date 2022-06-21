package com.rookie.config;

import com.rookie.utils.URLInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    // 添加拦截器对象 注入容器
    public void addInterceptors(InterceptorRegistry registry){
        // 创建拦截器对象
        HandlerInterceptor interceptor = new URLInterceptor();

        // 指定拦截路径
        String[] path = {"/**"};
        // 指定不拦截的地址
        //String excludePath;

        registry.addInterceptor(interceptor).addPathPatterns(path);
        // .excludePathPatterns(excludePath)
        System.out.println("拦截器被创建");
    }
}
