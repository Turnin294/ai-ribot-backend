package com.quanxiaoha.ai.robot.controller;

import com.quanxiaoha.ai.robot.aspect.ApiOperationLog;
import com.quanxiaoha.ai.robot.utils.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author: 犬小哈
 * @Date: 2025/5/22 12:25
 * @Version: v1.0.0
 * @Description: 对话
 **/
@RestController
@RequestMapping("/chat")
public class ChatController {

    @PostMapping("/new")
    @ApiOperationLog(description = "新建对话")
    public Response<?> newChat() {
        return Response.success();
    }

}
