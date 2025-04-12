package com.tgd.maintenance_soft_server.lib.blo_service.services;

import com.tgd.maintenance_soft_server.lib.blo_service.interfaces.BloServiceInterface;
import com.tgd.maintenance_soft_server.lib.blo_service.interfaces.UserIdentifiable;
import com.tgd.maintenance_soft_server.lib.blo_service.repositories.BloRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

@Service
public abstract class BloService<REQ, RES, E extends UserIdentifiable<U>, ID, U> implements BloServiceInterface<REQ, RES, E, ID, U> {

    private final Class<RES> responseClass;
    private final Class<E> entityClass;

    @Autowired
    private ModelMapper modelMapper;

    @SuppressWarnings("unchecked")
    public BloService() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.responseClass = (Class<RES>) genericSuperclass.getActualTypeArguments()[1];
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[2];
    }

    @Override
    public List<RES> getAll(U user) {
        return getRepository().findAllByUser(user).stream()
                .map(entity -> modelMapper.map(entity, responseClass))
                .collect(Collectors.toList());
    }

    @Override
    public RES getById(ID id, U user) {
        return getRepository().findByIdAndUser(id, user)
                .map(entity -> modelMapper.map(entity, responseClass))
                .orElseThrow(() -> new EntityNotFoundException("Entity not found or not associated with user"));
    }

    @Override
    public RES create(U user, REQ request) {
        E entity = modelMapper.map(request, entityClass);
        entity.setUser(user);
        return modelMapper.map(getRepository().save(entity), responseClass);
    }

    @Override
    public RES update(ID id, U user, REQ request) {
        E existingEntity = getRepository().findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found or not associated with user"));

        modelMapper.map(request, existingEntity);
        return modelMapper.map(getRepository().save(existingEntity), responseClass);
    }

    @Override
    public void deleteById(ID id, U user) {
        E entity = getRepository().findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found or not associated with user"));

        getRepository().delete(entity);
    }

    @Override
    public abstract BloRepository<E, ID, U> getRepository();
}
