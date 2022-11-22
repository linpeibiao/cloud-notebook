package com.xiaohu.cloud_notebook.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaohu
 * @date 2022/11/21/ 20:46
 * @description
 */
@Configuration
public class RedissonConfig {
    @Bean
    public RedissonClient redissonClient(){
        // 配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://119.91.234.58:6379").setPassword("linpeibiaoxiaohu");
        // 创建RedissonClient对象
        return Redisson.create(config);
    }
}
