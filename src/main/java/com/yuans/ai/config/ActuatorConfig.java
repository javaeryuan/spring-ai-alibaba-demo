package com.yuans.ai.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Actuator配置类
 */
@Configuration
public class ActuatorConfig {
    @Value("${spring.application.name}")
    private String applicationName;
    
    /**
     * 自定义MeterRegistry，添加应用名称标签
     * @return MeterRegistryCustomizer
     */
    @Bean
    public MeterRegistryCustomizer<MeterRegistry> meterRegistryCustomizer() {
        return registry -> registry.config()
                .commonTags("application", applicationName);
    }
}