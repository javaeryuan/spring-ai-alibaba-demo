package com.yuans.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 图节点模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphNode {
    /**
     * 节点ID
     */
    private String id;
    
    /**
     * 节点名称
     */
    private String name;
    
    /**
     * 节点类型：input, process, decision, output
     */
    private String type;
    
    /**
     * 节点描述
     */
    private String description;
    
    /**
     * 节点执行逻辑的实现类
     */
    private Class<?> implementationClass;
    
    /**
     * 节点配置参数
     */
    private Map<String, Object> config;
    
    /**
     * 节点状态：idle, running, completed, failed
     */
    private String status;
    
    /**
     * 节点执行结果
     */
    private Object result;
    
    /**
     * 节点执行错误信息
     */
    private String errorMessage;
}