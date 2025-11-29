package com.yuans.ai.controller;

import com.yuans.ai.model.McpContext;
import com.yuans.ai.model.McpMessage;
import com.yuans.ai.service.McpContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * MCP上下文管理示例Controller
 */
@RestController
@RequestMapping("/mcp/context")
public class McpContextController {
    @Autowired
    private McpContextManager contextManager;
    
    /**
     * 创建新的上下文
     * @return 新创建的上下文
     */
    @PostMapping("/create")
    public ResponseEntity<McpContext> createContext() {
        McpContext context = contextManager.createContext();
        return ResponseEntity.ok(context);
    }
    
    /**
     * 获取上下文
     * @param sessionId 会话ID
     * @return 上下文对象
     */
    @GetMapping("/{sessionId}")
    public ResponseEntity<McpContext> getContext(@PathVariable String sessionId) {
        McpContext context = contextManager.retrieveContext(sessionId);
        if (context == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(context);
    }
    
    /**
     * 添加消息到上下文
     * @param sessionId 会话ID
     * @param messageRequest 消息请求
     * @return 更新后的上下文
     */
    @PostMapping("/{sessionId}/messages")
    public ResponseEntity<McpContext> addMessage(@PathVariable String sessionId, @RequestBody MessageRequest messageRequest) {
        McpContext context = contextManager.retrieveContext(sessionId);
        if (context == null) {
            return ResponseEntity.notFound().build();
        }
        
        // 创建消息
        McpMessage message = McpMessage.builder()
                .messageId(UUID.randomUUID().toString())
                .role(messageRequest.getRole())
                .content(messageRequest.getContent())
                .type(messageRequest.getType())
                .toolCallId(messageRequest.getToolCallId())
                .timestamp(System.currentTimeMillis())
                .build();
        
        // 添加消息到上下文
        context.addMessage(message);
        
        // 保存上下文
        contextManager.saveContext(context);
        
        return ResponseEntity.ok(context);
    }
    
    /**
     * 删除上下文
     * @param sessionId 会话ID
     * @return 响应实体
     */
    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> deleteContext(@PathVariable String sessionId) {
        contextManager.deleteContext(sessionId);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * 消息请求类
     */
    public static class MessageRequest {
        private String role;
        private String content;
        private String type;
        private String toolCallId;
        
        public String getRole() {
            return role;
        }
        
        public void setRole(String role) {
            this.role = role;
        }
        
        public String getContent() {
            return content;
        }
        
        public void setContent(String content) {
            this.content = content;
        }
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
        
        public String getToolCallId() {
            return toolCallId;
        }
        
        public void setToolCallId(String toolCallId) {
            this.toolCallId = toolCallId;
        }
    }
}