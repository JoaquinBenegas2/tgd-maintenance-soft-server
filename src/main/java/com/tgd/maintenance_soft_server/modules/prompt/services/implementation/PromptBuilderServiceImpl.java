package com.tgd.maintenance_soft_server.modules.prompt.services.implementation;

import com.tgd.maintenance_soft_server.modules.maintenance.entities.MaintenanceAnswerEntity;
import com.tgd.maintenance_soft_server.modules.maintenance.entities.MaintenanceEntity;
import com.tgd.maintenance_soft_server.modules.maintenance.repositories.MaintenanceRepository;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.prompt.services.PromptBuilderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromptBuilderServiceImpl implements PromptBuilderService {

    private final MaintenanceRepository maintenanceRepository;

    @Override
    public String buildDailySummary(PlantEntity plantEntity) {
        LocalDate today = LocalDate.now();
        List<MaintenanceEntity> todayMaintenances =
                maintenanceRepository.findByMaintenanceDateAndIdentifyingEntity(today, plantEntity);

        if (todayMaintenances.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("A continuación recibirás los datos de todos los mantenimientos realizados hoy. ");
        sb.append("Genera un resumen técnico conciso (que no sean muchos renglones) a partir de la información que te envío. Que incluya:\n");
        sb.append("1. Hallazgos clave.\n");
        sb.append("2. Anomalías detectadas.\n");
        sb.append("3. Recomendaciones prioritarias.\n\n");

        sb.append("# Resumen de mantenimientos del día ")
                .append(today)
                .append("\n\n");

        // 1) Agrupar todas las respuestas de hoy por nombre de formulario
        Map<String, List<MaintenanceAnswerEntity>> answersByForm =
                todayMaintenances.stream()
                        .flatMap(m -> m.getAnswers().stream())
                        .collect(Collectors.groupingBy(ans -> ans.getForm().getName()));

        // 2) Para cada formulario, agrupamos por mantenimiento y volcamos al StringBuilder
        for (Map.Entry<String, List<MaintenanceAnswerEntity>> entry : answersByForm.entrySet()) {
            String formName = entry.getKey();
            List<MaintenanceAnswerEntity> answers = entry.getValue();

            sb.append("----\n\n");
            sb.append("## Formulario: ").append(formName).append("\n");

            // Agrupar por ID de mantenimiento
            Map<Long, List<MaintenanceAnswerEntity>> answersByMaint =
                    answers.stream()
                            .collect(Collectors.groupingBy(ans -> ans.getMaintenance().getId()));

            for (Map.Entry<Long, List<MaintenanceAnswerEntity>> maintEntry : answersByMaint.entrySet()) {
                Long maintId = maintEntry.getKey();
                MaintenanceEntity maint = maintEntry.getValue().get(0).getMaintenance();

                sb.append("- **Mantenimiento ID:** ").append(maintId)
                        .append(" | Ruta: ").append(maint.getRoute().getName())
                        .append(" | Elemento: ").append(maint.getElement().getName())
                        .append("\n");

                // Listado de respuestas
                for (MaintenanceAnswerEntity ans : maintEntry.getValue()) {
                    sb.append("    - ").append(ans.getFormField().getName())
                            .append(": ").append(ans.getValue()).append("\n");
                }
            }
            sb.append("\n");
        }

        System.out.println(sb);

        return sb.toString();
    }

    @Override
    public String buildImprovementSuggestions(String dailySummary) {
        return new StringBuilder()
                .append("A partir del siguiente resumen técnico de mantenimientos del día, ")
                .append("generá 3 a 5 recomendaciones prácticas para mejorar el estado operativo de la planta. ")
                .append("Las recomendaciones deben ser concretas, breves y orientadas a la acción. ")
                .append("Utilizá viñetas para presentarlas, y tené en cuenta hallazgos, anomalías ")
                .append("y oportunidades de mejora mencionadas en el resumen.\n\n")
                .append("Resumen técnico:\n\"\"\"\n")
                .append(dailySummary)
                .append("\n\"\"\"")
                .toString();
    }
}
