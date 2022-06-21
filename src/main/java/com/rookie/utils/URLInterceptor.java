package com.rookie.utils;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthOptionPaneUI;

public class URLInterceptor implements HandlerInterceptor {
    /**
     *
     * @param request
     * @param response
     * @param handler  被拦截器的控制器对象
     * @return boolean
     *    true： 请求能被Controller处理
     *    false: 请求被截断
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("URI: "+ request.getRequestURI());
        System.out.println("URL: "+ request.getRequestURL());
        return true;
    }
}
