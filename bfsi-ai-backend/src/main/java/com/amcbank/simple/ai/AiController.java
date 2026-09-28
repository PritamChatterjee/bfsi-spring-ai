package com.amcbank.simple.ai;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/status")
    public Map<String, Object> status() {
        return Map.of(
                "module", "AMC Bank Spring AI Assistant",
                "secured", true,
                "purpose", "Educational banking Q&A. Live banking data remains in secured BankService APIs.");
    }

    @PostMapping("/ask")
    public ResponseEntity<AiAnswerResponse> ask(@RequestBody AiQuestionRequest request) {
        return ResponseEntity.ok(aiService.ask(request.getQuestion()));
    }
}
