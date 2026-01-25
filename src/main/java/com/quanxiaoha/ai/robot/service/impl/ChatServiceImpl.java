package com.quanxiaoha.ai.robot.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quanxiaoha.ai.robot.domain.dos.ChatDO;
import com.quanxiaoha.ai.robot.domain.dos.ChatMessageDO;
import com.quanxiaoha.ai.robot.domain.mapper.ChatMapper;
import com.quanxiaoha.ai.robot.domain.mapper.ChatMessageMapper;
import com.quanxiaoha.ai.robot.model.vo.chat.*;
import com.quanxiaoha.ai.robot.service.ChatService;
import com.quanxiaoha.ai.robot.utils.PageResponse;
import com.quanxiaoha.ai.robot.utils.Response;
import com.quanxiaoha.ai.robot.utils.StringUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Author: 犬小哈
 * @Date: 2025/8/11 15:48
 * @Version: v1.0.0
 * @Description: 对话
 **/
@Service
@Slf4j
public class ChatServiceImpl implements ChatService {

    @Resource
    private ChatMapper chatMapper;
    @Autowired
    private ChatMessageMapper chatMessageMapper;

    /**
     * 新建对话
     *
     * @param newChatReqVO
     * @return
     */
    @Override
    public Response<NewChatRspVO> newChat(NewChatReqVO newChatReqVO) {
        // 用户发送的消息
        String message = newChatReqVO.getMessage();

        // 生成对话 UUID
        String uuid = UUID.randomUUID().toString();
        // 截取用户发送的消息，作为对话摘要
        String summary = StringUtil.truncate(message, 20);

        // 存储对话记录到数据库中
        chatMapper.insert(ChatDO.builder()
                .summary(summary)
                .uuid(uuid)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build());

        // 将摘要、UUID 返回给前端
        return Response.success(NewChatRspVO.builder()
                .uuid(uuid)
                .summary(summary)
                .build());
    }

     /**
     * 查询历史消息
     *
     * @param findChatHistoryMessagePageListReqVO
     * @return
     */
    @Override
    public PageResponse<FindChatHistoryMessagePageListRspVO> findChatHistoryMessagePageList(FindChatHistoryMessagePageListReqVO findChatHistoryMessagePageListReqVO) {
        // 获取当前页、以及每页需要展示的数据数量
        Long current = findChatHistoryMessagePageListReqVO.getCurrent();
        Long size = findChatHistoryMessagePageListReqVO.getSize();
        String chatId = findChatHistoryMessagePageListReqVO.getChatId();

        // 执行分页查询
        Page<ChatMessageDO> chatMessageDOPage = chatMessageMapper.selectPageList(current, size, chatId);
        List<ChatMessageDO> chatMessageDOS = chatMessageDOPage.getRecords();
        // DO 转 VO
        List<FindChatHistoryMessagePageListRspVO> vos = null;
        if (CollUtil.isNotEmpty(chatMessageDOS)) {
            vos = chatMessageDOS.stream()
                    .map(chatMessageDO -> FindChatHistoryMessagePageListRspVO.builder() // 构建返参 VO 实体类
                            .id(chatMessageDO.getId())
                            .chatId(chatMessageDO.getChatUuid())
                            .content(chatMessageDO.getContent())
                            .role(chatMessageDO.getRole())
                            .createTime(chatMessageDO.getCreateTime())
                            .build())
                    // 升序排序
                    .sorted(Comparator.comparing(FindChatHistoryMessagePageListRspVO::getCreateTime))
                    .collect(Collectors.toList());
        }

        return PageResponse.success(chatMessageDOPage, vos);
    }
    /**
     * 查询历史对话
     *
     * @param findChatHistoryPageListReqVO
     * @return
     */
    @Override
    public PageResponse<FindChatHistoryPageListRspVO> findChatHistoryPageList(FindChatHistoryPageListReqVO findChatHistoryPageListReqVO) {
        // 获取当前页、以及每页需要展示的数据数量
        Long current = findChatHistoryPageListReqVO.getCurrent();
        Long size = findChatHistoryPageListReqVO.getSize();

        // 执行分页查询
        Page<ChatDO> chatDOPage = chatMapper.selectPageList(current, size);

	    // 获取查询结果
        List<ChatDO> chatDOS = chatDOPage.getRecords();

        // DO 转 VO
        List<FindChatHistoryPageListRspVO> vos = null;
        if (CollUtil.isNotEmpty(chatDOS)) {
            vos = chatDOS.stream()
                    .map(chatDO -> FindChatHistoryPageListRspVO.builder() // 构建返参 VO
                            .id(chatDO.getId())
                            .uuid(chatDO.getUuid())
                            .summary(chatDO.getSummary())
                            .updateTime(chatDO.getUpdateTime())
                            .build())
                    .collect(Collectors.toList());
        }

        return PageResponse.success(chatDOPage, vos);
    }
}

