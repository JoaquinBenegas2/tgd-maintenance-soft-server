package com.tgd.maintenance_soft_server.modules.ai_generation.services.implementation;

import com.tgd.maintenance_soft_server.modules.ai_generation.clients.AiGenerationClient;
import com.tgd.maintenance_soft_server.modules.ai_generation.services.AiGenerationService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.prompt.services.PromptBuilderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AiGenerationServiceImpl implements AiGenerationService {

    private final PromptBuilderService promptBuilderService;
    private final AiGenerationClient aiGenerationClient;

    @Override
    public Flux<String> getDailySummary(PlantEntity plantEntity) {
        String prompt = promptBuilderService.buildDailySummary(plantEntity);
        LocalDate today = LocalDate.now();
        if (prompt == null) return Flux.just("No ", "se ", "registraron ", "mantenimientos ", "para ", "la ", "fecha ", today + ".");

        return aiGenerationClient.streamChatCompletion(prompt);
    }

    @Override
    public Flux<String> getImprovementSuggestions(String dailySummary) {
        String prompt = promptBuilderService.buildImprovementSuggestions(dailySummary);
        if (dailySummary == null || dailySummary.isEmpty()) return Flux.just("No ", "se ", "encontraron ", "sugerencias ", "de ", "mejora.");

        return aiGenerationClient.streamChatCompletion(prompt);
        // return Flux.just("Esta " + "funcionalidad " + "está " + "en " + "desarrollo.");
    }
}
