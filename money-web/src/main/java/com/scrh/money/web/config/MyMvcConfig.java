package com.scrh.money.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * mvc配置类，此次练习仅做视图控制使用，为节约时间不做登录拦截器之类的其他功能了
 *
 * @author ScrH0052
 * @date 2021/8/4
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    /**
     * 视图跳转控制器
     *
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
        //充值页面
        registry.addViewController("/loan/page/toRecharge").setViewName("toRecharge");
//        registry.addViewController("/loan/page/myRecharge").setViewName("myRecharge");
//        registry.addViewController("/loan/page/recharge/payBack").setViewName("payBack");


    }
}
