package com.yuans.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 嵌入模型，用于表示文档或查询的向量嵌入
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Embedding {
    /**
     * 嵌入ID
     */
    private String id;
    
    /**
     * 嵌入向量
     */
    private float[] vector;
    
    /**
     * 嵌入对应的文本
     */
    private String text;
    
    /**
     * 嵌入模型名称
     */
    private String modelName;
    
    /**
     * 嵌入创建时间
     */
    private long createdAt;
}