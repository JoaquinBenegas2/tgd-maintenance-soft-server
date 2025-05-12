package com.tgd.maintenance_soft_server.modules.ai_generation.clients.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgd.maintenance_soft_server.modules.ai_generation.clients.AiGenerationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiGenerationClientImpl implements AiGenerationClient {

    private final WebClient webClient;

    private static final String MODEL = "gpt-4o-mini";
    private static final String OPENAI_BASE_URL = "https://api.openai.com/v1/chat/completions";

    @Value("${openai.api-key}")
    private String openAiApiKey;

    @Override
    public Flux<String> streamChatCompletion(String userMessage) {
        WebClient client = webClient.mutate()
                .baseUrl(OPENAI_BASE_URL)
                .defaultHeader("Authorization", "Bearer " + openAiApiKey)
                .build();

        List<Map<String, String>> messages = List.of(
                Map.of("role", "system",
                        "content", "Eres un asistente experto en mantenimiento industrial. " +
                                   "Debes responder siempre en formato **Markdown válido**, con títulos, " +
                                   "listas y secciones claramente separadas. " +
                                   "Asegúrate de que los encabezados tengan saltos de línea y las listas estén bien formateadas."
                ),
                Map.of("role", "user", "content", userMessage)
        );

        Map<String, Object> body = Map.of(
                "model", MODEL,
                "stream", true,
                "messages", messages
        );

        return client.post()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .bodyValue(body)
                .retrieve()
                .bodyToFlux(String.class)
                .doOnSubscribe(sub -> System.out.println("Suscripto al stream de OpenAI"))
                .doOnError(error -> {
                    System.err.println("❌ Error en OpenAI: " + error.getMessage());
                    error.printStackTrace();
                })
                .doOnNext(line -> System.out.println("👉 LÍNEA RECIBIDA DE OPENAI: " + line))
                .map(this::extractContent);
    }

    private String extractContent(String jsonLine) {
        if (jsonLine.equals("[DONE]")) return "";

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonLine);
            return root.path("choices").get(0).path("delta").path("content").asText("");
        } catch (Exception e) {
            System.err.println("❌ Error parseando chunk: " + e.getMessage());
            return "";
        }
    }
}
