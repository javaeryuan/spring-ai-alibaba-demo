package com.yuans.ai.service;

import com.yuans.ai.model.ToolDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 工具注册中心，用于管理所有可用工具
 */
@Component
public class ToolRegistry {
    @Autowired
    private ApplicationContext applicationContext;
    
    private final Map<String, Tool> toolMap = new HashMap<>();
    private final Map<String, ToolDefinition> toolDefinitionMap = new HashMap<>();
    
    /**
     * 初始化时自动注册所有实现了Tool接口的Bean
     */
    @PostConstruct
    public void init() {
        // 获取所有实现了Tool接口的Bean
        Map<String, Tool> tools = applicationContext.getBeansOfType(Tool.class);
        
        // 注册所有工具
        for (Tool tool : tools.values()) {
            registerTool(tool);
        }
    }
    
    /**
     * 注册工具
     * @param tool 工具实例
     */
    public void registerTool(Tool tool) {
        toolMap.put(tool.getName(), tool);
        
        // 这里可以根据工具类的注解或反射来构建ToolDefinition
        // 简化实现，直接创建基本的ToolDefinition
        ToolDefinition definition = ToolDefinition.builder()
                .name(tool.getName())
                .description(tool.getDescription())
                .implementationClass(tool.getClass())
                .build();
        
        toolDefinitionMap.put(tool.getName(), definition);
    }
    
    /**
     * 根据名称获取工具
     * @param toolName 工具名称
     * @return 工具实例
     */
    public Tool getTool(String toolName) {
        return toolMap.get(toolName);
    }
    
    /**
     * 根据名称获取工具定义
     * @param toolName 工具名称
     * @return 工具定义
     */
    public ToolDefinition getToolDefinition(String toolName) {
        return toolDefinitionMap.get(toolName);
    }
    
    /**
     * 获取所有工具名称
     * @return 工具名称集合
     */
    public Set<String> getAllToolNames() {
        return toolMap.keySet();
    }
    
    /**
     * 获取所有工具定义
     * @return 工具定义映射
     */
    public Map<String, ToolDefinition> getAllToolDefinitions() {
        return new HashMap<>(toolDefinitionMap);
    }
    
    /**
     * 移除工具
     * @param toolName 工具名称
     */
    public void removeTool(String toolName) {
        toolMap.remove(toolName);
        toolDefinitionMap.remove(toolName);
    }
}