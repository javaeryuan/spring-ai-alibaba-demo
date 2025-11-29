package com.yuans.ai.service;

import com.yuans.ai.model.GraphDefinition;
import com.yuans.ai.model.GraphEdge;
import com.yuans.ai.model.GraphExecutionContext;
import com.yuans.ai.model.GraphNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * 图执行引擎，用于执行整个图的流程
 */
@Service
public class GraphEngine {
    @Autowired
    private ApplicationContext applicationContext;
    
    @Autowired
    private List<NodeExecutor> nodeExecutors;
    
    @Value("${ai.graph.execution-timeout:60000}")
    private long executionTimeout;
    
    // 线程池，用于异步执行图
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    
    /**
     * 执行图
     * @param graphDefinition 图定义
     * @param inputData 输入数据
     * @return 执行上下文
     */
    public GraphExecutionContext executeGraph(GraphDefinition graphDefinition, Map<String, Object> inputData) {
        // 1. 创建执行上下文
        GraphExecutionContext context = GraphExecutionContext.builder()
                .executionId(UUID.randomUUID().toString())
                .graphDefinition(graphDefinition)
                .currentNodeId(graphDefinition.getStartNodeId())
                .status("running")
                .startTime(System.currentTimeMillis())
                .executionData(inputData)
                .build();
        
        try {
            // 2. 执行图
            executeGraphInternal(context);
            
            // 3. 设置执行结果
            context.setStatus("completed");
            context.setEndTime(System.currentTimeMillis());
            context.setExecutionTime(context.getEndTime() - context.getStartTime());
        } catch (Exception e) {
            // 4. 处理执行错误
            context.setStatus("failed");
            context.setEndTime(System.currentTimeMillis());
            context.setExecutionTime(context.getEndTime() - context.getStartTime());
            context.setErrorMessage(e.getMessage());
        }
        
        return context;
    }
    
    /**
     * 异步执行图
     * @param graphDefinition 图定义
     * @param inputData 输入数据
     * @return 执行结果的Future
     */
    public Future<GraphExecutionContext> executeGraphAsync(GraphDefinition graphDefinition, Map<String, Object> inputData) {
        return executorService.submit(() -> executeGraph(graphDefinition, inputData));
    }
    
    /**
     * 内部执行图的方法
     * @param context 执行上下文
     */
    private void executeGraphInternal(GraphExecutionContext context) {
        GraphDefinition graph = context.getGraphDefinition();
        
        // 构建节点和边的映射，方便快速查找
        Map<String, GraphNode> nodeMap = graph.getNodes().stream()
                .collect(Collectors.toMap(GraphNode::getId, node -> node));
        
        Map<String, List<GraphEdge>> outgoingEdgesMap = graph.getEdges().stream()
                .collect(Collectors.groupingBy(GraphEdge::getSourceNodeId));
        
        // 从起始节点开始执行
        String currentNodeId = context.getCurrentNodeId();
        
        // 执行循环，直到到达结束节点或执行失败
        while (currentNodeId != null && !context.getStatus().equals("failed")) {
            // 检查是否到达结束节点
            if (graph.getEndNodeIds().contains(currentNodeId)) {
                break;
            }
            
            // 1. 获取当前节点
            GraphNode currentNode = nodeMap.get(currentNodeId);
            if (currentNode == null) {
                throw new RuntimeException("Node not found: " + currentNodeId);
            }
            
            // 2. 执行当前节点
            executeNode(currentNode, context);
            
            // 3. 查找下一个要执行的节点
            List<GraphEdge> outgoingEdges = outgoingEdgesMap.getOrDefault(currentNodeId, List.of());
            if (outgoingEdges.isEmpty()) {
                // 没有出边，结束执行
                break;
            }
            
            // 4. 根据条件选择下一个节点
            String nextNodeId = selectNextNode(outgoingEdges, context);
            if (nextNodeId == null) {
                // 没有符合条件的边，结束执行
                break;
            }
            
            // 5. 更新当前节点ID
            currentNodeId = nextNodeId;
            context.setCurrentNodeId(currentNodeId);
        }
    }
    
    /**
     * 执行单个节点
     * @param node 要执行的节点
     * @param context 执行上下文
     */
    private void executeNode(GraphNode node, GraphExecutionContext context) {
        // 查找支持该节点的执行器
        NodeExecutor executor = nodeExecutors.stream()
                .filter(e -> e.supports(node))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No executor found for node type: " + node.getType()));
        
        // 执行节点
        executor.execute(node, context);
    }
    
    /**
     * 根据条件选择下一个节点
     * @param outgoingEdges 出边列表
     * @param context 执行上下文
     * @return 下一个节点ID
     */
    private String selectNextNode(List<GraphEdge> outgoingEdges, GraphExecutionContext context) {
        // 简化实现，选择第一条边
        // 实际实现中应该根据条件表达式判断
        if (!outgoingEdges.isEmpty()) {
            return outgoingEdges.get(0).getTargetNodeId();
        }
        return null;
    }
}