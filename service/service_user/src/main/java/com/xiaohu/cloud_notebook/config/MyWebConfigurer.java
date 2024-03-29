package com.xiaohu.cloud_notebook.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xiaohu
 * @date 2022/11/09/ 0:39
 * @description
 */
@SpringBootConfiguration
public class MyWebConfigurer implements WebMvcConfigurer {
    /**
     * 所有请求都允许跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                //允许跨域的 cookie
                .allowCredentials(true)
                // 前端更改端口时，相应的后端需要允许新更改的端口访问，否则会出现跨域问题
                .allowedOrigins("http://119.91.234.58:8000", "http://localhost:8088", "http://localhost:8080")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedHeaders("*");
    }
}
