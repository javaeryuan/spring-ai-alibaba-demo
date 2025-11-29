package com.yuans.ai.service.impl;

import com.yuans.ai.model.GraphExecutionContext;
import com.yuans.ai.model.GraphNode;
import com.yuans.ai.service.NodeExecutor;
import org.springframework.stereotype.Component;

/**
 * 处理节点执行器实现
 */
@Component
public class ProcessNodeExecutorImpl implements NodeExecutor {
    @Override
    public GraphExecutionContext execute(GraphNode node, GraphExecutionContext context) {
        // 模拟节点执行逻辑
        System.out.println("Executing node: " + node.getName());
        
        // 更新节点状态
        node.setStatus("completed");
        
        // 模拟执行结果
        node.setResult("Processed data: " + context.getExecutionData());
        
        // 更新执行上下文
        context.getExecutionData().put("processed", true);
        context.getExecutionData().put("nodeResult", node.getResult());
        
        return context;
    }
    
    @Override
    public boolean supports(GraphNode node) {
        // 支持处理类型的节点
        return "process".equals(node.getType());
    }
}