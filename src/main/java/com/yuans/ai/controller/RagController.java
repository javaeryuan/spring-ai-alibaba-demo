package com.yuans.ai.controller;

import com.yuans.ai.service.RagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RAG示例Controller
 */
@RestController
@RequestMapping("/rag")
public class RagController {
    @Autowired
    private RagService ragService;
    
    /**
     * 使用RAG生成响应
     * @param request 请求体，包含用户查询
     * @return 响应实体
     */
    @PostMapping("/generate")
    public ResponseEntity<String> generateWithRag(@RequestBody RagRequest request) {
        String response = ragService.generateWithRag(request.getQuery());
        return ResponseEntity.ok(response);
    }
    
    /**
     * RAG请求类
     */
    public static class RagRequest {
        private String query;
        
        public String getQuery() {
            return query;
        }
        
        public void setQuery(String query) {
            this.query = query;
        }
    }
}