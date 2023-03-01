package com.xiaohu.cloud_notebook.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaohu
 * @date 2023/02/27/ 21:55
 * @description 队列、交换机配置
 */
@Configuration
public class RabbitmqConfig {
    public static final String NOTE_EXCHANGE_NAME = "note.exchange";
    public static final String NOTE_QUEUE_NAME = "note.queue";

    /**
     * 声明最普通的交换机和队列
     * @return
     */
    @Bean("noteExchange")
    public DirectExchange confirmExchange(){
        return new DirectExchange(NOTE_EXCHANGE_NAME);
    }

    @Bean("noteQueue")
    public Queue confirmQueue(){
        return QueueBuilder.durable(NOTE_QUEUE_NAME).build();
    }

    /**
     * 绑定
     * @param confirmQueue
     * @param confirmExchange
     * @return
     */
    @Bean
    public Binding queueBindExchange(@Qualifier("noteQueue") Queue confirmQueue,
                                     @Qualifier("noteExchange") DirectExchange confirmExchange){
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with("note");

    }
}
