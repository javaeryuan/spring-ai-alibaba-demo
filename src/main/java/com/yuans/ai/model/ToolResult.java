package com.yuans.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工具执行结果模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolResult {
    /**
     * 调用ID，与ToolCall的callId对应
     */
    private String callId;
    
    /**
     * 执行状态：SUCCESS, FAILURE
     */
    private Status status;
    
    /**
     * 执行结果内容
     */
    private Object content;
    
    /**
     * 错误信息（如果执行失败）
     */
    private String errorMessage;
    
    /**
     * 执行时间戳
     */
    private long timestamp;
    
    /**
     * 执行耗时（毫秒）
     */
    private long executionTime;
    
    /**
     * 执行状态枚举
     */
    public enum Status {
        SUCCESS,
        FAILURE
    }
}