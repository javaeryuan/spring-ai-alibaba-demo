package com.yuans.ai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuans.ai.model.McpContext;
import com.yuans.ai.model.McpMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * McpContextManager单元测试
 */
@ExtendWith(MockitoExtension.class)
public class McpContextManagerTest {
    @Mock
    private RedisTemplate<String, String> redisTemplate;
    
    @Mock
    private ValueOperations<String, String> valueOperations;
    
    @Mock
    private ObjectMapper objectMapper;
    
    @InjectMocks
    private McpContextManager contextManager;
    
    @Test
    void testCreateContext() {
        // 模拟Redis操作
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        
        // 执行测试
        McpContext context = contextManager.createContext();
        
        // 验证结果
        assertNotNull(context);
        assertNotNull(context.getSessionId());
        assertEquals(0, context.getMessageCount());
        
        // 验证Redis存储操作
        verify(valueOperations, times(1)).set(anyString(), anyString());
    }
    
    @Test
    void testRetrieveContext() throws Exception {
        // 模拟Redis操作
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        
        // 模拟上下文JSON
        String sessionId = "test_session_id";
        McpContext expectedContext = McpContext.builder()
                .sessionId(sessionId)
                .createdAt(System.currentTimeMillis())
                .updatedAt(System.currentTimeMillis())
                .messageCount(0)
                .build();
        
        String contextJson = "{\"sessionId\":\"test_session_id\",\"messageCount\":0}";
        when(valueOperations.get("mcp:context:" + sessionId)).thenReturn(contextJson);
        when(objectMapper.readValue(contextJson, McpContext.class)).thenReturn(expectedContext);
        
        // 执行测试
        McpContext context = contextManager.retrieveContext(sessionId);
        
        // 验证结果
        assertNotNull(context);
        assertEquals(sessionId, context.getSessionId());
    }
    
    @Test
    void testRetrieveNonExistentContext() {
        // 模拟Redis操作
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("mcp:context:non_existent_id")).thenReturn(null);
        
        // 执行测试
        McpContext context = contextManager.retrieveContext("non_existent_id");
        
        // 验证结果
        assertNull(context);
    }
    
    @Test
    void testAddMessageToContext() throws Exception {
        // 模拟Redis操作
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        
        // 模拟上下文
        String sessionId = "test_session_id";
        McpContext context = McpContext.builder()
                .sessionId(sessionId)
                .createdAt(System.currentTimeMillis())
                .updatedAt(System.currentTimeMillis())
                .messageCount(0)
                .build();
        
        // 模拟Redis获取和存储
        String initialJson = "{\"sessionId\":\"test_session_id\",\"messageCount\":0}";
        when(valueOperations.get("mcp:context:" + sessionId)).thenReturn(initialJson);
        when(objectMapper.readValue(initialJson, McpContext.class)).thenReturn(context);
        when(objectMapper.writeValueAsString(context)).thenReturn(initialJson);
        
        // 创建测试消息
        McpMessage message = McpMessage.builder()
                .messageId("test_message_id")
                .role("user")
                .content("Test message")
                .type("text")
                .timestamp(System.currentTimeMillis())
                .build();
        
        // 添加消息到上下文
        context.addMessage(message);
        
        // 保存上下文
        contextManager.saveContext(context);
        
        // 验证结果
        assertEquals(1, context.getMessageCount());
        verify(valueOperations, times(1)).set(anyString(), anyString());
    }
}