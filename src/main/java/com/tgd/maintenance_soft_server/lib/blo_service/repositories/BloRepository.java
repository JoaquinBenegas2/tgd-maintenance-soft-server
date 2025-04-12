package com.tgd.maintenance_soft_server.lib.blo_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BloRepository<E, ID, U> extends JpaRepository<E, ID> {
    List<E> findAllByUser(U user);
    Optional<E> findByIdAndUser(ID id, U user);
}
