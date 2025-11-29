package com.yuans.ai.service;

import com.yuans.ai.model.ToolCall;
import com.yuans.ai.model.ToolResult;

/**
 * 工具接口，所有工具必须实现此接口
 */
public interface Tool {
    /**
     * 获取工具名称
     * @return 工具名称
     */
    String getName();
    
    /**
     * 获取工具描述
     * @return 工具描述
     */
    String getDescription();
    
    /**
     * 执行工具调用
     * @param toolCall 工具调用请求
     * @return 工具执行结果
     */
    ToolResult execute(ToolCall toolCall);
}