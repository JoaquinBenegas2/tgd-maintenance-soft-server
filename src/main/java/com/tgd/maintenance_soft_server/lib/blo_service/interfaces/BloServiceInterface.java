package com.tgd.maintenance_soft_server.lib.blo_service.interfaces;

import com.tgd.maintenance_soft_server.lib.blo_service.repositories.BloRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Base Logic Service Interface

@Service
public interface BloServiceInterface<REQ, RES, E extends IdentifyingEntity<IE>, ID, IE> {
    List<RES> getAll(IE identifyingEntity);

    RES getById(ID id, IE identifyingEntity);

    RES create(IE identifyingEntity, REQ request);

    RES update(ID id, IE identifyingEntity, REQ request);

    void deleteById(ID id, IE identifyingEntity);

    BloRepository<E, ID, IE> getRepository();
}
