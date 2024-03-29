package com.xiaohu.cloud_notebook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xiaohu
 * @date 2022/11/13/ 17:42
 * @description
 */

@SpringBootApplication(scanBasePackages = {"com.xiaohu"})
@MapperScan("com.xiaohu.cloud_notebook.mapper")
@EnableFeignClients("com.xiaohu.cloud_notebook.service")
public class NoteApplication {
    public static void main(String[] args) {
        SpringApplication.run(NoteApplication.class, args);
    }
}
