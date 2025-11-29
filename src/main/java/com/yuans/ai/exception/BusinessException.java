package com.yuans.ai.exception;

/**
 * 业务异常类
 */
public class BusinessException extends RuntimeException {
    /**
     * 构造方法
     * @param message 异常信息
     */
    public BusinessException(String message) {
        super(message);
    }
    
    /**
     * 构造方法
     * @param message 异常信息
     * @param cause 异常原因
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}