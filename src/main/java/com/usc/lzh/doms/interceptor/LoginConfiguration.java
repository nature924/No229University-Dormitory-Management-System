package com.usc.lzh.doms.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 * 配置拦截的路径
 */
@Configuration
public class LoginConfiguration implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截一切路径
        // 除了index、login和静态资源文件
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/login.*").excludePathPatterns("/", "/index").excludePathPatterns("/layui/**", "/css/**", "/images/**", "/lib/**");
    }
}
