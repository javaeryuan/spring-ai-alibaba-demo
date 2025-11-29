package com.yuans.ai.exception;

/**
 * 资源未找到异常类
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * 构造方法
     * @param message 异常信息
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    /**
     * 构造方法
     * @param message 异常信息
     * @param cause 异常原因
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}