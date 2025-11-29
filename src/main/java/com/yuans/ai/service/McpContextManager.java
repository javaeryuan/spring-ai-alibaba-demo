package com.yuans.ai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuans.ai.model.McpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

/**
 * MCP上下文管理器，用于处理上下文的存储、检索、更新和清理
 */
@Service
public class McpContextManager {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Value("${ai.mcp.context-ttl:86400}")
    private long contextTtl;
    
    @Value("${ai.mcp.max-messages:50}")
    private int maxMessages;
    
    private static final String CONTEXT_KEY_PREFIX = "mcp:context:";
    
    /**
     * 创建新的上下文
     * @return 新创建的上下文
     */
    public McpContext createContext() {
        String sessionId = UUID.randomUUID().toString();
        long now = System.currentTimeMillis();
        
        McpContext context = McpContext.builder()
                .sessionId(sessionId)
                .createdAt(now)
                .updatedAt(now)
                .messageCount(0)
                .build();
        
        // 存储到Redis
        saveContext(context);
        
        return context;
    }
    
    /**
     * 根据会话ID检索上下文
     * @param sessionId 会话ID
     * @return 上下文对象，如果不存在则返回null
     */
    public McpContext retrieveContext(String sessionId) {
        String key = CONTEXT_KEY_PREFIX + sessionId;
        String contextJson = redisTemplate.opsForValue().get(key);
        
        if (contextJson == null) {
            return null;
        }
        
        try {
            return objectMapper.readValue(contextJson, McpContext.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize context", e);
        }
    }
    
    /**
     * 保存上下文到Redis
     * @param context 上下文对象
     */
    public void saveContext(McpContext context) {
        // 自动修剪上下文，保持合理大小
        context.prune(maxMessages);
        
        String key = CONTEXT_KEY_PREFIX + context.getSessionId();
        try {
            String contextJson = objectMapper.writeValueAsString(context);
            redisTemplate.opsForValue().set(key, contextJson, Duration.ofSeconds(contextTtl));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize context", e);
        }
    }
    
    /**
     * 更新上下文
     * @param context 上下文对象
     */
    public void updateContext(McpContext context) {
        saveContext(context);
    }
    
    /**
     * 删除上下文
     * @param sessionId 会话ID
     */
    public void deleteContext(String sessionId) {
        String key = CONTEXT_KEY_PREFIX + sessionId;
        redisTemplate.delete(key);
    }
    
    /**
     * 清理过期上下文（可选，Redis会自动过期）
     */
    public void cleanupExpiredContexts() {
        // Redis会自动根据TTL删除过期键，这里可以添加额外的清理逻辑
    }
}