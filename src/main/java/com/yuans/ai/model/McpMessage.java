package com.yuans.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MCP消息模型，用于表示上下文中的消息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class McpMessage {
    /**
     * 消息ID
     */
    private String messageId;
    
    /**
     * 消息角色：user, assistant, system, tool
     */
    private String role;
    
    /**
     * 消息内容
     */
    private String content;
    
    /**
     * 消息类型：text, image, audio, tool_call, tool_result
     */
    private String type;
    
    /**
     * 工具调用ID（如果是工具调用或工具结果消息）
     */
    private String toolCallId;
    
    /**
     * 消息时间戳
     */
    private long timestamp;
}