package com.quanxiaoha.ai.robot.enums;

import com.quanxiaoha.ai.robot.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: 犬小哈
 * @url: www.quanxiaoha.com
 * @date: 2023-08-15 10:33
 * @description: AI 客服 Markdown 文件状态
 **/
@Getter
@AllArgsConstructor
public enum AiCustomerServiceMdStatusEnum {

    PENDING(0, "待处理"),
    VECTORIZING(1, "向量化中"),
    COMPLETED(2, "已完成"),
    FAILED(3, "失败");

    private Integer code;
    private String description;

}

