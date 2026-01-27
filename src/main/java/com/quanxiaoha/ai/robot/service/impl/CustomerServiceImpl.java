package com.quanxiaoha.ai.robot.service.impl;

import com.quanxiaoha.ai.robot.domain.dos.AiCustomerServiceMdStorageDO;
import com.quanxiaoha.ai.robot.domain.mapper.AiCustomerServiceMdStorageMapper;
import com.quanxiaoha.ai.robot.enums.AiCustomerServiceMdStatusEnum;
import com.quanxiaoha.ai.robot.enums.ResponseCodeEnum;
import com.quanxiaoha.ai.robot.event.AiCustomerServiceMdUploadedEvent;
import com.quanxiaoha.ai.robot.exception.BizException;
import com.quanxiaoha.ai.robot.service.CustomerService;
import com.quanxiaoha.ai.robot.utils.Response;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @Author: 犬小哈
 * @Date: 2025/8/11 15:48
 * @Version: v1.0.0
 * @Description: AI 客服
 **/
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Value("${customer-service.md-storage-path}")
    private String mdStoragePath;

    @Resource
    private AiCustomerServiceMdStorageMapper aiCustomerServiceMdStorageMapper;
    @Resource
    private ApplicationEventPublisher eventPublisher; // 注入事件发布器

    /**
     * 上传 Markdown 问答文件
     *
     * @param file
     * @return
     */
    @Override
    public Response<?> uploadMarkdownFile(MultipartFile file) {
        // 校验文件不能为空
        if (file == null || file.isEmpty()) {
            throw new BizException(ResponseCodeEnum.UPLOAD_FILE_CANT_EMPTY);
        }

        // 获取原始文件名（去除空格）
        String originalFilename = StringUtils.trimToEmpty(file.getOriginalFilename());

        // 验证文件类型，仅支持 Markdown
        if (StringUtils.isBlank(originalFilename) || !isMarkdownFile(originalFilename)) {
            throw new BizException(ResponseCodeEnum.ONLY_SUPPORT_MARKDOWN);
        }

        try {
            // 重新生成文件名 (防止文件名冲突导致覆盖)
            String newFilename = UUID.randomUUID().toString() + "-" + originalFilename;

            // 构建存储路径
            Path storageDirectory = Paths.get(mdStoragePath);
            Path targetPath = storageDirectory.resolve(newFilename);

            // 确保目录存在
            Files.createDirectories(storageDirectory);

            // 保存文件
            file.transferTo(targetPath.toFile());

            // 记录操作日志
            log.info("## Markdown 问答文件存储成功, 文件名：{} -> 存储路径：{}", originalFilename, targetPath);

            // 存储入库
            aiCustomerServiceMdStorageMapper.insert(AiCustomerServiceMdStorageDO.builder()
                            .originalFileName(originalFilename)
                            .newFileName(newFilename)
                            .filePath(targetPath.toString())
                            .fileSize(file.getSize())
                            .status(AiCustomerServiceMdStatusEnum.PENDING.getCode())
                            .createTime(LocalDateTime.now())
                            .updateTime(LocalDateTime.now())
                            .build());

            // 发布事件
            eventPublisher.publishEvent(AiCustomerServiceMdUploadedEvent.builder()
                            .filePath(targetPath.toString())
                            .build());

            return Response.success();

        } catch (IOException e) {
            log.error("## Markdown 问答文件上传失败：{}", originalFilename, e);
            throw new BizException(ResponseCodeEnum.UPLOAD_FILE_FAILED);
        }
    }

    /**
     * 验证文件是否为 Markdown 格式
     */
    private boolean isMarkdownFile(String filename) {
        if (StringUtils.isBlank(filename)) {
            return false;
        }

        // 获取文件扩展名
        String extension = FilenameUtils.getExtension(filename);
        return StringUtils.equalsIgnoreCase(extension, "md");
    }
}
