package com.quanxiaoha.ai.robot.controller;

import com.google.common.collect.Lists;
import com.quanxiaoha.ai.robot.advisor.CustomChatMemoryAdvisor;
import com.quanxiaoha.ai.robot.advisor.CustomStreamLoggerAndMessage2DBAdvisor;
import com.quanxiaoha.ai.robot.advisor.NetworkSearchAdvisor;
import com.quanxiaoha.ai.robot.aspect.ApiOperationLog;
import com.quanxiaoha.ai.robot.domain.mapper.ChatMessageMapper;
import com.quanxiaoha.ai.robot.model.vo.chat.*;
import com.quanxiaoha.ai.robot.model.vo.customerService.DeleteMarkdownFileReqVO;
import com.quanxiaoha.ai.robot.model.vo.customerService.FindMarkdownFilePageListReqVO;
import com.quanxiaoha.ai.robot.model.vo.customerService.FindMarkdownFilePageListRspVO;
import com.quanxiaoha.ai.robot.service.ChatService;
import com.quanxiaoha.ai.robot.service.CustomerService;
import com.quanxiaoha.ai.robot.service.SearXNGService;
import com.quanxiaoha.ai.robot.service.SearchResultContentFetcherService;
import com.quanxiaoha.ai.robot.utils.PageResponse;
import com.quanxiaoha.ai.robot.utils.Response;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.List;


/**
 * @Author: 犬小哈
 * @Date: 2025/5/22 12:25
 * @Version: v1.0.0
 * @Description: AI 客服
 **/
@RestController
@RequestMapping("/customer-service")
@Slf4j
public class AiCustomerServiceController {

    @Resource
    private CustomerService customerService;

    /**
     * 问答 MD 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/md/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<?> uploadMarkdownFile(@RequestPart(value = "file", required = false) MultipartFile file) {
        return customerService.uploadMarkdownFile(file);
    }

    @PostMapping("/md/delete")
    @ApiOperationLog(description = "删除 Markdown 问答文件")
    public Response<?> deleteMarkdownFile(@RequestBody @Validated DeleteMarkdownFileReqVO deleteMarkdownFileReqVO) {
        return customerService.deleteMarkdownFile(deleteMarkdownFileReqVO);
    }
    @PostMapping("/md/list")
    @ApiOperationLog(description = "Markdown 问答文件分页查询")
    public PageResponse<FindMarkdownFilePageListRspVO> findMarkdownFilePageList(@RequestBody @Validated FindMarkdownFilePageListReqVO findMarkdownFilePageListReqVO) {
        return customerService.findMarkdownFilePageList(findMarkdownFilePageListReqVO);
    }

}