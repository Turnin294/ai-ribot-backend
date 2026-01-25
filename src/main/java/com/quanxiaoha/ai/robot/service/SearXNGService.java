package com.quanxiaoha.ai.robot.service;

import com.quanxiaoha.ai.robot.model.dto.SearchResultDTO;

import java.util.List;

public interface SearXNGService {
    List<SearchResultDTO> search(String query);
}
