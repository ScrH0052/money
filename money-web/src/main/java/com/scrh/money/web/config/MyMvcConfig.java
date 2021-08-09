package com.scrh.money.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ScrH0052
 * @date 2021/8/4
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    /**
     * 视图跳转控制器
     * @param registry 视图控制注册器
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //跳转登录页面
        registry.addViewController("/loan/page/login").setViewName("login");
        //跳转注册页面
        registry.addViewController("/loan/page/register").setViewName("register");
        //跳转实名认证页面
        registry.addViewController("/loan/page/realName").setViewName("realName");
        //跳转注册成功页面
        registry.addViewController("/loan/page/complete").setViewName("registerComplete");
    }
}
