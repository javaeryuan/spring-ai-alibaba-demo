package com.yuans.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * MCP上下文模型，用于表示完整的上下文信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class McpContext {
    /**
     * 会话ID
     */
    private String sessionId;
    
    /**
     * 消息列表
     */
    @Builder.Default
    private List<McpMessage> messages = new ArrayList<>();
    
    /**
     * 上下文元数据
     */
    @Builder.Default
    private Map<String, Object> metadata = Map.of();
    
    /**
     * 上下文创建时间
     */
    private long createdAt;
    
    /**
     * 上下文更新时间
     */
    private long updatedAt;
    
    /**
     * 消息数量
     */
    private int messageCount;
    
    /**
     * 添加消息到上下文
     * @param message 要添加的消息
     */
    public void addMessage(McpMessage message) {
        this.messages.add(message);
        this.messageCount = this.messages.size();
        this.updatedAt = System.currentTimeMillis();
    }
    
    /**
     * 修剪上下文，保持指定数量的最新消息
     * @param maxMessages 最大消息数量
     */
    public void prune(int maxMessages) {
        if (this.messages.size() > maxMessages) {
            this.messages = this.messages.subList(this.messages.size() - maxMessages, this.messages.size());
            this.messageCount = this.messages.size();
            this.updatedAt = System.currentTimeMillis();
        }
    }
    
    /**
     * 清空上下文消息
     */
    public void clearMessages() {
        this.messages.clear();
        this.messageCount = 0;
        this.updatedAt = System.currentTimeMillis();
    }
}