package com.yuans.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工具参数模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolParameter {
    /**
     * 参数名称
     */
    private String name;
    
    /**
     * 参数描述
     */
    private String description;
    
    /**
     * 参数类型
     */
    private String type;
    
    /**
     * 是否必填
     */
    private boolean required;
    
    /**
     * 默认值
     */
    private Object defaultValue;
}