package com.yuans.ai.service;

import com.yuans.ai.model.GraphExecutionContext;
import com.yuans.ai.model.GraphNode;

/**
 * 节点执行器接口，用于执行单个节点的逻辑
 */
public interface NodeExecutor {
    /**
     * 执行节点逻辑
     * @param node 要执行的节点
     * @param context 执行上下文
     * @return 执行后的上下文
     */
    GraphExecutionContext execute(GraphNode node, GraphExecutionContext context);
    
    /**
     * 检查是否支持执行指定节点
     * @param node 要检查的节点
     * @return 是否支持执行
     */
    boolean supports(GraphNode node);
}