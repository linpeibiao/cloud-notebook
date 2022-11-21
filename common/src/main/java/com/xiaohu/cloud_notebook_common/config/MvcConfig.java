package com.xiaohu.cloud_notebook_common.config;

import com.xiaohu.cloud_notebook_common.intercepter.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xiaohu
 * @date 2022/11/19/ 16:59
 * @description
 */
@Configuration
@Slf4j
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录拦截器
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        "/note/**",
                        "/base/**",
                        "/**/**"
                ).order(1);
    }
}
