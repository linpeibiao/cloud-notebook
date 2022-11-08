package com.xiaohu.cloud_notebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author xiaohu
 * @date 2022/11/09/ 0:11
 * @description
 */
@SpringBootApplication
@EnableWebMvc
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
