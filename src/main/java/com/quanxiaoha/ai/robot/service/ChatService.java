package com.quanxiaoha.ai.robot.service;


import com.quanxiaoha.ai.robot.model.vo.chat.NewChatReqVO;
import com.quanxiaoha.ai.robot.model.vo.chat.NewChatRspVO;
import com.quanxiaoha.ai.robot.utils.Response;

/**
 * @author: 犬小哈
 * @url: www.quanxiaoha.com
 * @date: 2023-09-15 14:03
 * @description: 对话
 **/
public interface ChatService {

    /**
     * 新建对话
     * @param newChatReqVO
     * @return
     */
    Response<NewChatRspVO> newChat(NewChatReqVO newChatReqVO);
}

