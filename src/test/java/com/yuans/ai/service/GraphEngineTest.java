package com.yuans.ai.service;

import com.yuans.ai.model.GraphDefinition;
import com.yuans.ai.model.GraphEdge;
import com.yuans.ai.model.GraphExecutionContext;
import com.yuans.ai.model.GraphNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * GraphEngine单元测试
 */
@ExtendWith(MockitoExtension.class)
public class GraphEngineTest {
    @Mock
    private NodeExecutor mockNodeExecutor;
    
    @InjectMocks
    private GraphEngine graphEngine;
    
    @Test
    void testExecuteGraph() {
        // 创建测试用的图定义
        GraphNode startNode = GraphNode.builder()
                .id("node1")
                .name("Start Node")
                .type("process")
                .description("Start node")
                .status("idle")
                .build();
        
        GraphNode endNode = GraphNode.builder()
                .id("node2")
                .name("End Node")
                .type("output")
                .description("End node")
                .status("idle")
                .build();
        
        GraphEdge edge = GraphEdge.builder()
                .id("edge1")
                .sourceNodeId("node1")
                .targetNodeId("node2")
                .name("Edge 1")
                .description("Edge from node1 to node2")
                .build();
        
        GraphDefinition graphDefinition = GraphDefinition.builder()
                .id("test_graph")
                .name("Test Graph")
                .description("Test graph")
                .nodes(List.of(startNode, endNode))
                .edges(List.of(edge))
                .startNodeId("node1")
                .endNodeIds(List.of("node2"))
                .build();
        
        // 模拟节点执行器支持该节点
        when(mockNodeExecutor.supports(any(GraphNode.class))).thenReturn(true);
        
        // 模拟节点执行
        when(mockNodeExecutor.execute(any(GraphNode.class), any(GraphExecutionContext.class)))
                .thenAnswer(invocation -> {
                    GraphExecutionContext context = invocation.getArgument(1);
                    GraphNode node = invocation.getArgument(0);
                    node.setStatus("completed");
                    node.setResult("Node executed: " + node.getName());
                    return context;
                });
        
        // 执行测试
        GraphExecutionContext context = graphEngine.executeGraph(graphDefinition, Map.of("input", "test data"));
        
        // 验证结果
        assertNotNull(context);
        assertEquals("completed", context.getStatus());
        assertNotNull(context.getExecutionId());
        assertNotNull(context.getExecutionTime());
    }
}