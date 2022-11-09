package com.xiaohu.cloud_notebook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xiaohu
 * @date 2022/11/09/ 0:11
 * @description 程序主启动类
 */
@SpringBootApplication(scanBasePackages = {"com.xiaohu"})
@MapperScan("com.xiaohu.cloud_notebook.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
