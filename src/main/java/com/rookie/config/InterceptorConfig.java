package com.rookie.config;

import com.rookie.utils.AuthInterceptor;
import com.rookie.utils.URLInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    private HandlerInterceptor urlInterceptor = new URLInterceptor();

    @Resource
    private HandlerInterceptor Authinterceptor = new AuthInterceptor();

    // 添加拦截器对象 注入容器
    public void addInterceptors(InterceptorRegistry registry){
        //// 创建拦截器对象
        //HandlerInterceptor interceptor1 = new URLInterceptor();

        // 指定拦截路径
        String[] path1 = {"/**"};
        // 指定不拦截的地址
        //String excludePath;

        registry.addInterceptor(urlInterceptor).addPathPatterns(path1);
        // .excludePathPatterns(excludePath)
        System.out.println("URLInterceptor 拦截器被创建");


        // 创建拦截器对象
        //HandlerInterceptor interceptor2 = new AuthInterceptor();

        // 指定拦截路径
        String[] path2 = {"/**"};
        // 指定不拦截的地址
        //String excludePath;

        registry.addInterceptor(Authinterceptor).addPathPatterns(path2);
        // .excludePathPatterns(excludePath)
        System.out.println("AuthInterceptor 拦截器被创建");
    }
}
