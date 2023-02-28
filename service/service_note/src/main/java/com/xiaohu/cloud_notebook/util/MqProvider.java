package com.xiaohu.cloud_notebook.util;

import com.xiaohu.cloud_notebook.model.domain.NoteContentHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.xiaohu.cloud_notebook.config.RabbitmqConfig.NOTE_EXCHANGE_NAME;

/**
 * @author xiaohu
 * @date 2023/02/28/ 21:26
 * @description 消息生产者
 */
@Component
@Slf4j
public class MqProvider {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @PostConstruct
    public void init(){}

    public void sendMessage(NoteContentHistory noteContentHistory){
        log.info("send message: " + noteContentHistory);
        // 消息ID, 用于确认消息
        CorrelationData correlationData = new CorrelationData("1");
        // 生产个消息
        rabbitTemplate.convertAndSend(NOTE_EXCHANGE_NAME, "note",
                noteContentHistory,
                correlationData);
    }
}
