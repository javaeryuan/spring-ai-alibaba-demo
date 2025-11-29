package com.yuans.ai.controller;

import com.yuans.ai.model.ToolCall;
import com.yuans.ai.model.ToolResult;
import com.yuans.ai.service.ToolCallingExecutor;
import com.yuans.ai.service.ToolRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * 工具调用示例Controller
 */
@RestController
@RequestMapping("/tool")
public class ToolCallingController {
    @Autowired
    private ToolCallingExecutor toolCallingExecutor;
    
    @Autowired
    private ToolRegistry toolRegistry;
    
    /**
     * 获取所有可用工具
     * @return 可用工具列表
     */
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> listTools() {
        return ResponseEntity.ok(Map.of(
            "tools", toolRegistry.getAllToolDefinitions(),
            "toolCount", toolRegistry.getAllToolNames().size()
        ));
    }
    
    /**
     * 执行工具调用
     * @param request 工具调用请求
     * @return 工具执行结果
     */
    @PostMapping("/call")
    public ResponseEntity<ToolResult> callTool(@RequestBody ToolCallRequest request) {
        // 创建工具调用请求
        ToolCall toolCall = ToolCall.builder()
                .callId(UUID.randomUUID().toString())
                .toolName(request.getToolName())
                .parameters(request.getParameters())
                .timestamp(System.currentTimeMillis())
                .build();
        
        // 执行工具调用
        ToolResult result = toolCallingExecutor.executeToolCall(toolCall);
        
        return ResponseEntity.ok(result);
    }
    
    /**
     * 工具调用请求类
     */
    public static class ToolCallRequest {
        private String toolName;
        private Map<String, Object> parameters;
        
        public String getToolName() {
            return toolName;
        }
        
        public void setToolName(String toolName) {
            this.toolName = toolName;
        }
        
        public Map<String, Object> getParameters() {
            return parameters;
        }
        
        public void setParameters(Map<String, Object> parameters) {
            this.parameters = parameters;
        }
    }
}