package com.yuans.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 文档模型，用于表示RAG系统中的文档
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    /**
     * 文档ID
     */
    private String id;
    
    /**
     * 文档内容
     */
    private String content;
    
    /**
     * 文档元数据
     */
    private Map<String, Object> metadata;
    
    /**
     * 文档来源
     */
    private String source;
    
    /**
     * 文档创建时间
     */
    private long createdAt;
    
    /**
     * 文档更新时间
     */
    private long updatedAt;
}