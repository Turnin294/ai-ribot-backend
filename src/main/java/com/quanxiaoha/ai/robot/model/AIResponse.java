package com.quanxiaoha.ai.robot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: AI 对话响应类
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AIResponse {
    // 流式响应内容
    private String v;
}
