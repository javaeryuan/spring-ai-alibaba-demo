package com.yuans.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 图边模型，用于表示节点之间的连接关系
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphEdge {
    /**
     * 边ID
     */
    private String id;
    
    /**
     * 源节点ID
     */
    private String sourceNodeId;
    
    /**
     * 目标节点ID
     */
    private String targetNodeId;
    
    /**
     * 边名称
     */
    private String name;
    
    /**
     * 边描述
     */
    private String description;
    
    /**
     * 边条件表达式（用于条件分支）
     */
    private String condition;
    
    /**
     * 边权重（用于权重图）
     */
    private Double weight;
    
    /**
     * 边配置参数
     */
    private Map<String, Object> config;
}