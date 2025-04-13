package com.tgd.maintenance_soft_server.lib.blo_service.services;

import com.tgd.maintenance_soft_server.lib.blo_service.interfaces.BloServiceInterface;
import com.tgd.maintenance_soft_server.lib.blo_service.interfaces.IdentifyingEntity;
import com.tgd.maintenance_soft_server.lib.blo_service.repositories.BloRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

@Service
public abstract class BloService<REQ, RES, E extends IdentifyingEntity<IE>, ID, IE> implements BloServiceInterface<REQ, RES, E, ID, IE> {

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
    public List<RES> getAll(IE identifyingEntity) {
        return getRepository().findAllByIdentifyingEntity(identifyingEntity).stream()
                .map(entity -> modelMapper.map(entity, responseClass))
                .collect(Collectors.toList());
    }

    @Override
    public RES getById(ID id, IE identifyingEntity) {
        return getRepository().findByIdAndIdentifyingEntity(id, identifyingEntity)
                .map(entity -> modelMapper.map(entity, responseClass))
                .orElseThrow(() -> new EntityNotFoundException("Entity not found or not associated with the identifying entity"));
    }

    @Override
    public RES create(IE identifyingEntity, REQ request) {
        E entity = modelMapper.map(request, entityClass);
        entity.setIdentifyingEntity(identifyingEntity);
        return modelMapper.map(getRepository().save(entity), responseClass);
    }

    @Override
    public RES update(ID id, IE identifyingEntity, REQ request) {
        E existingEntity = getRepository().findByIdAndIdentifyingEntity(id, identifyingEntity)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found or not associated with the identifying entity"));

        modelMapper.map(request, existingEntity);
        return modelMapper.map(getRepository().save(existingEntity), responseClass);
    }

    @Override
    public void deleteById(ID id, IE identifyingEntity) {
        E entity = getRepository().findByIdAndIdentifyingEntity(id, identifyingEntity)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found or not associated with the identifying entity"));

        getRepository().delete(entity);
    }

    @Override
    public abstract BloRepository<E, ID, IE> getRepository();
}
