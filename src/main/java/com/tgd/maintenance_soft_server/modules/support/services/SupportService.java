package com.tgd.maintenance_soft_server.modules.support.services;

import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.support.dtos.ReportAProblemDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface SupportService {
    void reportAProblem(
            PlantEntity plantEntity,
            ReportAProblemDto reportAProblemDto,
            List<MultipartFile> images
    ) throws IOException;
}
