package com.tgd.maintenance_soft_server.modules.ai_generation.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgd.maintenance_soft_server.modules.ai_generation.services.AiGenerationService;
import com.tgd.maintenance_soft_server.modules.auth.services.AuthService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
@RequestMapping("/api/ai-generation")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AiGenerationController {

    private final AiGenerationService aiGenerationService;
    private final AuthService authService;

    @PostMapping("/daily-summary")
    public Flux<ServerSentEvent<String>> getDailySummary(@RequestHeader("x-plant-id") Long plantId) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return aiGenerationService.getDailySummary(plantEntity).map(chunk -> {
            Map<String, String> json = Map.of(
                    "role", "assistant",
                    "content", chunk
            );
            try {
                String payload = new ObjectMapper().writeValueAsString(json);
                return ServerSentEvent.<String>builder().data(payload).build();
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error serializando SSE", e);
            }
        });
    }

    @PostMapping("/improvement-suggestions")
    public Flux<ServerSentEvent<String>> improvementSuggestions(
            @RequestHeader("x-plant-id") Long plantId,
            @RequestBody String prompt
    ) {
        authService.getSelectedPlant(plantId);

        return aiGenerationService.getImprovementSuggestions(prompt).map(chunk -> {
            Map<String, String> json = Map.of(
                    "role", "assistant",
                    "content", chunk
            );
            try {
                String payload = new ObjectMapper().writeValueAsString(json);
                return ServerSentEvent.<String>builder().data(payload).build();
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error serializando SSE", e);
            }
        });
    }
}
