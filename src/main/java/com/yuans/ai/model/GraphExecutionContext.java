package com.yuans.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 图执行上下文模型，用于表示图执行过程中的状态和数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphExecutionContext {
    /**
     * 执行ID
     */
    private String executionId;
    
    /**
     * 图定义
     */
    private GraphDefinition graphDefinition;
    
    /**
     * 当前执行的节点ID
     */
    private String currentNodeId;
    
    /**
     * 执行状态：running, completed, failed, cancelled
     */
    private String status;
    
    /**
     * 执行开始时间
     */
    private long startTime;
    
    /**
     * 执行结束时间
     */
    private long endTime;
    
    /**
     * 执行耗时（毫秒）
     */
    private long executionTime;
    
    /**
     * 执行数据，用于在节点之间传递数据
     */
    private Map<String, Object> executionData;
    
    /**
     * 执行结果
     */
    private Object result;
    
    /**
     * 执行错误信息
     */
    private String errorMessage;
}