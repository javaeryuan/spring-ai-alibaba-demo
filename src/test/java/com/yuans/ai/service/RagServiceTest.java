package com.yuans.ai.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * RagService单元测试
 */
@ExtendWith(MockitoExtension.class)
public class RagServiceTest {
    @InjectMocks
    private RagService ragService;
    
    @Test
    void testGenerateWithRag() {
        // 执行测试
        String result = ragService.generateWithRag("test query");
        
        // 验证结果
        assertNotNull(result);
        assertEquals("这是一个模拟的RAG响应，查询内容：test query", result);
    }
    
    @Test
    void testRetrieveRelevantDocuments() {
        // 执行测试
        var result = ragService.retrieveRelevantDocuments("test query", 5);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("这是一个模拟的相关文档，查询内容：test query", result.get(0));
    }
}