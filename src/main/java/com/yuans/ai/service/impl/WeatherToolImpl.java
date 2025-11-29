package com.yuans.ai.service.impl;

import com.yuans.ai.model.ToolCall;
import com.yuans.ai.model.ToolResult;
import com.yuans.ai.service.Tool;
import org.springframework.stereotype.Component;

/**
 * 天气工具实现，用于获取指定城市的天气信息
 */
@Component
public class WeatherToolImpl implements Tool {
    @Override
    public String getName() {
        return "weather_tool";
    }
    
    @Override
    public String getDescription() {
        return "获取指定城市的天气信息";
    }
    
    @Override
    public ToolResult execute(ToolCall toolCall) {
        try {
            // 模拟获取天气信息
            String city = (String) toolCall.getParameters().get("city");
            if (city == null || city.isEmpty()) {
                return ToolResult.builder()
                        .callId(toolCall.getCallId())
                        .status(ToolResult.Status.FAILURE)
                        .errorMessage("City parameter is required")
                        .timestamp(System.currentTimeMillis())
                        .executionTime(System.currentTimeMillis() - toolCall.getTimestamp())
                        .build();
            }
            
            // 模拟天气数据
            String weatherInfo = String.format("城市: %s, 天气: 晴, 温度: 25°C, 湿度: 45%%, 风力: 微风", city);
            
            return ToolResult.builder()
                    .callId(toolCall.getCallId())
                    .status(ToolResult.Status.SUCCESS)
                    .content(weatherInfo)
                    .timestamp(System.currentTimeMillis())
                    .executionTime(System.currentTimeMillis() - toolCall.getTimestamp())
                    .build();
        } catch (Exception e) {
            return ToolResult.builder()
                    .callId(toolCall.getCallId())
                    .status(ToolResult.Status.FAILURE)
                    .errorMessage("Failed to get weather info: " + e.getMessage())
                    .timestamp(System.currentTimeMillis())
                    .executionTime(System.currentTimeMillis() - toolCall.getTimestamp())
                    .build();
        }
    }
}