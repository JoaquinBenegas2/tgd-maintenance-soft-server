package com.tgd.maintenance_soft_server.lib.blo_service.services;

import com.tgd.maintenance_soft_server.lib.blo_service.interfaces.BloServiceInterface;
import com.tgd.maintenance_soft_server.lib.blo_service.interfaces.IdentifyingEntity;
import com.tgd.maintenance_soft_server.lib.blo_service.repositories.BloRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public abstract class BloService<REQ, RES, E extends IdentifyingEntity<IE>, ID, IE> implements BloServiceInterface<REQ, RES, E, ID, IE> {

    private final Class<RES> responseClass;
    private final Class<E> entityClass;

    @Autowired
    protected ModelMapper modelMapper;

    @SuppressWarnings("unchecked")
    public BloService() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.responseClass = (Class<RES>) genericSuperclass.getActualTypeArguments()[1];
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[2];
        configureMapper(modelMapper);
    }

    /**
     * Hook for subclasses to customize ModelMapper configuration.
     */
    protected void configureMapper(ModelMapper mapper) {
        // default: no custom configuration
    }

    /**
     * Hook to map entity to DTO; subclasses can override to add extra fields.
     */
    protected RES toDto(E entity) {
        return modelMapper.map(entity, responseClass);
    }

    /**
     * Hook to map request to entity; subclasses can override for custom logic.
     */
    protected E toEntity(REQ request) {
        return modelMapper.map(request, entityClass);
    }

    @Override
    public List<RES> getAll(IE identifyingEntity) {
        List<RES> result = getRepository().findAllByIdentifyingEntity(identifyingEntity).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        
        Collections.reverse(result);

        return result;
    }

    @Override
    public RES getById(ID id, IE identifyingEntity) {
        var entity = getRepository()
                .findByIdAndIdentifyingEntity(id, identifyingEntity)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found or not associated with the identifying entity"));
        return toDto(entity);
    }

    @Override
    @Transactional
    public RES create(IE identifyingEntity, REQ request) {
        E entity = toEntity(request);
        entity.setIdentifyingEntity(identifyingEntity);
        E saved = getRepository().save(entity);
        return toDto(saved);
    }

    @Override
    @Transactional
    public RES update(ID id, IE identifyingEntity, REQ request) {
        E existing = getRepository()
                .findByIdAndIdentifyingEntity(id, identifyingEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Entity not found or not associated with the identifying entity"));
        modelMapper.map(request, existing);
        getRepository().save(existing);
        return toDto(existing);
    }

    @Override
    @Transactional
    public void deleteById(ID id, IE identifyingEntity) {
        E entity = getRepository()
                .findByIdAndIdentifyingEntity(id, identifyingEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Entity not found or not associated with the identifying entity"));
        getRepository().delete(entity);
    }

    @Override
    public abstract BloRepository<E, ID, IE> getRepository();
}
