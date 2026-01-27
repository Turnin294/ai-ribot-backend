package com.quanxiaoha.ai.robot.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 犬小哈
 * @Date: 2025/11/2 22:31
 * @Version: v1.0.0
 * @Description: AI 客服 Markdown 问答文件上传事件
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AiCustomerServiceMdUploadedEvent {

    /**
     * 存储路径
     */
    private String filePath;
}

