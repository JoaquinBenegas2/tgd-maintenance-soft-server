package com.tgd.maintenance_soft_server.modules.ai_generation.clients;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public interface AiGenerationClient {

    Flux<String> streamChatCompletion(String userMessage);
}
