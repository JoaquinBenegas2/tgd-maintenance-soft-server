package com.tgd.maintenance_soft_server.lib.blo_service.interfaces;

import com.tgd.maintenance_soft_server.lib.blo_service.repositories.BloRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Base Logic Service Interface

@Service
public interface BloServiceInterface<REQ, RES, E extends UserIdentifiable<U>, ID, U> {
    List<RES> getAll(U user);

    RES getById(ID id, U user);

    RES create(U user, REQ request);

    RES update(ID id, U user, REQ request);

    void deleteById(ID id, U user);

    BloRepository<E, ID, U> getRepository();
}
