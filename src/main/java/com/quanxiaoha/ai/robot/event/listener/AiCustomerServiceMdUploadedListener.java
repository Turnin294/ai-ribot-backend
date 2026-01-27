package com.quanxiaoha.ai.robot.event.listener;

import com.quanxiaoha.ai.robot.event.AiCustomerServiceMdUploadedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author: 犬小哈
 * @Date: 2025/11/2 22:36
 * @Version: v1.0.0
 * @Description: TODO
 **/
@Component
@Slf4j
public class AiCustomerServiceMdUploadedListener {

    /**
     * Markdown 文件向量化
     * @param event
     */
    @EventListener
    public void vectorizing(AiCustomerServiceMdUploadedEvent event) {
        log.info("## AiCustomerServiceMdUploadedEvent: {}", event);

        // TODO Markdown 文件向量化
    }
}

