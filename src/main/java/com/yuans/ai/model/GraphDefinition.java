package com.yuans.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 图定义模型，用于表示完整的图结构
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphDefinition {
    /**
     * 图ID
     */
    private String id;
    
    /**
     * 图名称
     */
    private String name;
    
    /**
     * 图描述
     */
    private String description;
    
    /**
     * 图节点列表
     */
    private List<GraphNode> nodes;
    
    /**
     * 图边列表
     */
    private List<GraphEdge> edges;
    
    /**
     * 起始节点ID
     */
    private String startNodeId;
    
    /**
     * 结束节点ID列表
     */
    private List<String> endNodeIds;
    
    /**
     * 图配置参数
     */
    private Map<String, Object> config;
}