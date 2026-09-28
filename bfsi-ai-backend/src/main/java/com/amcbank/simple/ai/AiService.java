package com.amcbank.simple.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private static final String SYSTEM_PROMPT = """
            You are AMC Bank's educational banking assistant.
            Explain banking and AMC Bank application concepts clearly and concisely.
            Never invent customer balances, transactions, account numbers, passwords,
            loan approvals, or other live customer data.
            Do not claim that you executed a banking transaction.
            For live customer data, tell the user to use the secured AMC Bank screens.
            Keep answers suitable for a Java Full Stack training project.
            """;

    private final ObjectProvider<ChatModel> chatModelProvider;

    public AiService(ObjectProvider<ChatModel> chatModelProvider) {
        this.chatModelProvider = chatModelProvider;
    }

    public AiAnswerResponse ask(String question) {
        if (question == null || question.isBlank()) {
            throw new IllegalArgumentException("Question is required");
        }
        if (question.length() > 2000) {
            throw new IllegalArgumentException("Question must be 2000 characters or less");
        }

        ChatModel chatModel = chatModelProvider.getIfAvailable();
        if (chatModel == null) {
            return new AiAnswerResponse(
                    "Spring AI is installed but the chat model is disabled. " +
                    "Set SPRING_AI_MODEL_CHAT=openai and OPENAI_API_KEY, then restart the backend.",
                    false);
        }

        String answer = ChatClient.builder(chatModel)
                .build()
                .prompt()
                .system(SYSTEM_PROMPT)
                .user(question.trim())
                .call()
                .content();

        return new AiAnswerResponse(answer, true);
    }
}
