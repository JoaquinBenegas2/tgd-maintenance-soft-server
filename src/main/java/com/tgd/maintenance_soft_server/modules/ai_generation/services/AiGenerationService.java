package com.tgd.maintenance_soft_server.modules.ai_generation.services;

import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public interface AiGenerationService {

    Flux<String> getDailySummary(PlantEntity plantEntity);

    Flux<String> getImprovementSuggestions(String dailySummary);
}
