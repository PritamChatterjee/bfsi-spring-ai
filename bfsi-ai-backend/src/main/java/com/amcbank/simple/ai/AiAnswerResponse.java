package com.amcbank.simple.ai;

public class AiAnswerResponse {
    private final String answer;
    private final boolean aiEnabled;

    public AiAnswerResponse(String answer, boolean aiEnabled) {
        this.answer = answer;
        this.aiEnabled = aiEnabled;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isAiEnabled() {
        return aiEnabled;
    }
}
