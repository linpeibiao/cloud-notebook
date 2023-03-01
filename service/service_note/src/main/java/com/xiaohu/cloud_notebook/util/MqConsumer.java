package com.xiaohu.cloud_notebook.util;

import com.xiaohu.cloud_notebook.model.domain.NoteContentHistory;
import com.xiaohu.cloud_notebook.service.NoteContentHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaohu
 * @date 2023/02/27/ 22:06
 * @description 消息消费者
 */
@Component
@Slf4j
public class MqConsumer {

    @Autowired
    private NoteContentHistoryService noteContentHistoryService;
    /**
     * 注入 接收消息
     */
    public static final String NOTE_QUEUE_NAME = "note.queue";
    @RabbitListener(queues = NOTE_QUEUE_NAME)
    public void receive(NoteContentHistory contentHistory) {
        // TODO
        noteContentHistoryService.save(contentHistory);
        log.info("receive message: " + "保存笔记正文到笔记历史");
    }
}
