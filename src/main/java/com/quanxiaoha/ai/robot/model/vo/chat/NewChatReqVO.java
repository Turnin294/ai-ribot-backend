package com.quanxiaoha.ai.robot.model.vo.chat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;


/**
 * @description: 新建对话
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewChatReqVO {

    @NotBlank(message = "用户消息不能为空")
    private String message;

}

