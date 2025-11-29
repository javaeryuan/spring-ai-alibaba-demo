package com.yuans.ai.service;

import com.yuans.ai.model.ToolCall;
import com.yuans.ai.model.ToolResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * ToolCallingExecutor单元测试
 */
@ExtendWith(MockitoExtension.class)
public class ToolCallingExecutorTest {
    @Mock
    private ToolRegistry toolRegistry;
    
    @Mock
    private Tool mockTool;
    
    @InjectMocks
    private ToolCallingExecutor toolCallingExecutor;
    
    @Test
    void testExecuteToolCall() {
        // 模拟工具注册
        when(toolRegistry.getTool("test_tool")).thenReturn(mockTool);
        
        // 模拟工具执行结果
        ToolResult expectedResult = ToolResult.builder()
                .callId("test_call_id")
                .status(ToolResult.Status.SUCCESS)
                .content("Test result")
                .timestamp(System.currentTimeMillis())
                .executionTime(100)
                .build();
        
        // 模拟工具执行
        when(mockTool.execute(anyToolCall())).thenReturn(expectedResult);
        
        // 创建测试用的工具调用请求
        ToolCall toolCall = ToolCall.builder()
                .callId("test_call_id")
                .toolName("test_tool")
                .parameters(Map.of("param1", "value1"))
                .timestamp(System.currentTimeMillis())
                .build();
        
        // 执行测试
        ToolResult result = toolCallingExecutor.executeToolCall(toolCall);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(ToolResult.Status.SUCCESS, result.getStatus());
        assertEquals("Test result", result.getContent());
    }
    
    private ToolCall anyToolCall() {
        return org.mockito.Mockito.any(ToolCall.class);
    }
}