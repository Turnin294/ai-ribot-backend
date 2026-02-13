package com.quanxiaoha.ai.robot.model.vo.customerService;

import com.quanxiaoha.ai.robot.model.common.BasePageQuery;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @description: 查询 Markdown 问答文件列表
 **/
@Data
@AllArgsConstructor
@Builder
public class FindMarkdownFilePageListReqVO extends BasePageQuery {

}

