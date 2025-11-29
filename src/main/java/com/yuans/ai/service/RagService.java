package com.yuans.ai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * RAG服务，用于实现检索增强生成功能
 */
@Service
public class RagService {
    
    @Value("${ai.rag.retrieval-top-k:5}")
    private int retrievalTopK;
    
    /**
     * 使用RAG生成响应
     * @param query 用户查询
     * @return 生成的响应
     */
    public String generateWithRag(String query) {
        // 简化实现，直接返回模拟响应
        return "这是一个模拟的RAG响应，查询内容：" + query;
    }
    
    /**
     * 生成查询向量并检索相关文档
     * @param query 用户查询
     * @param topK 检索数量
     * @return 相关文档列表
     */
    public List<String> retrieveRelevantDocuments(String query, int topK) {
        // 简化实现，返回模拟文档
        return Collections.singletonList("这是一个模拟的相关文档，查询内容：" + query);
    }
}