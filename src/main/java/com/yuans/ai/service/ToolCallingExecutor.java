package com.yuans.ai.service;

import com.yuans.ai.model.ToolCall;
import com.yuans.ai.model.ToolResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * 工具调用执行器，用于执行工具调用请求
 */
@Service
public class ToolCallingExecutor {
    @Autowired
    private ToolRegistry toolRegistry;
    
    @Value("${ai.tool.timeout:30000}")
    private long toolTimeout;
    
    // 线程池，用于异步执行工具调用
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    
    /**
     * 执行工具调用
     * @param toolCall 工具调用请求
     * @return 工具执行结果
     */
    public ToolResult executeToolCall(ToolCall toolCall) {
        long startTime = System.currentTimeMillis();
        
        try {
            // 1. 获取工具
            Tool tool = toolRegistry.getTool(toolCall.getToolName());
            if (tool == null) {
                return ToolResult.builder()
                        .callId(toolCall.getCallId())
                        .status(ToolResult.Status.FAILURE)
                        .errorMessage("Tool not found: " + toolCall.getToolName())
                        .timestamp(System.currentTimeMillis())
                        .executionTime(System.currentTimeMillis() - startTime)
                        .build();
            }
            
            // 2. 异步执行工具调用，设置超时
            Future<ToolResult> future = executorService.submit(() -> {
                try {
                    return tool.execute(toolCall);
                } catch (Exception e) {
                    return ToolResult.builder()
                            .callId(toolCall.getCallId())
                            .status(ToolResult.Status.FAILURE)
                            .errorMessage("Tool execution failed: " + e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .executionTime(System.currentTimeMillis() - startTime)
                            .build();
                }
            });
            
            // 3. 等待执行结果，设置超时
            return future.get(toolTimeout, TimeUnit.MILLISECONDS);
            
        } catch (TimeoutException e) {
            return ToolResult.builder()
                    .callId(toolCall.getCallId())
                    .status(ToolResult.Status.FAILURE)
                    .errorMessage("Tool execution timed out after " + toolTimeout + "ms")
                    .timestamp(System.currentTimeMillis())
                    .executionTime(System.currentTimeMillis() - startTime)
                    .build();
        } catch (Exception e) {
            return ToolResult.builder()
                    .callId(toolCall.getCallId())
                    .status(ToolResult.Status.FAILURE)
                    .errorMessage("Tool execution failed: " + e.getMessage())
                    .timestamp(System.currentTimeMillis())
                    .executionTime(System.currentTimeMillis() - startTime)
                    .build();
        }
    }
    
    /**
     * 批量执行工具调用
     * @param toolCalls 工具调用请求列表
     * @return 工具执行结果列表
     */
    public java.util.List<ToolResult> executeBatchToolCalls(java.util.List<ToolCall> toolCalls) {
        java.util.List<ToolResult> results = new java.util.ArrayList<>();
        
        for (ToolCall toolCall : toolCalls) {
            results.add(executeToolCall(toolCall));
        }
        
        return results;
    }
}