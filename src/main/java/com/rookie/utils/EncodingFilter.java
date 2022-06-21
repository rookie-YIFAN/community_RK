package com.rookie.utils;

import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("----------------------------------执行了 EncodingFilter----------------------------------");

        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("utf-8");
        filter.setForceEncoding(true);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
