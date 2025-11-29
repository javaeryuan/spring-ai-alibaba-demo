package com.yuans.ai.controller;

import com.yuans.ai.model.GraphDefinition;
import com.yuans.ai.model.GraphExecutionContext;
import com.yuans.ai.service.GraphEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Graph引擎示例Controller
 */
@RestController
@RequestMapping("/graph")
public class GraphController {
    @Autowired
    private GraphEngine graphEngine;
    
    /**
     * 执行图
     * @param request 图执行请求
     * @return 执行上下文
     */
    @PostMapping("/execute")
    public ResponseEntity<GraphExecutionContext> executeGraph(@RequestBody GraphExecutionRequest request) {
        GraphExecutionContext context = graphEngine.executeGraph(request.getGraphDefinition(), request.getInputData());
        return ResponseEntity.ok(context);
    }
    
    /**
     * 图执行请求类
     */
    public static class GraphExecutionRequest {
        private GraphDefinition graphDefinition;
        private Map<String, Object> inputData;
        
        public GraphDefinition getGraphDefinition() {
            return graphDefinition;
        }
        
        public void setGraphDefinition(GraphDefinition graphDefinition) {
            this.graphDefinition = graphDefinition;
        }
        
        public Map<String, Object> getInputData() {
            return inputData;
        }
        
        public void setInputData(Map<String, Object> inputData) {
            this.inputData = inputData;
        }
    }
}