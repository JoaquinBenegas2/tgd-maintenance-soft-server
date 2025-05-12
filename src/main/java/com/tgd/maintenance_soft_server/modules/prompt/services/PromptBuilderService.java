package com.tgd.maintenance_soft_server.modules.prompt.services;

import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import org.springframework.stereotype.Service;

@Service
public interface PromptBuilderService {

    String buildDailySummary(PlantEntity plantEntity);

    String buildImprovementSuggestions(String dailySummary);
}
