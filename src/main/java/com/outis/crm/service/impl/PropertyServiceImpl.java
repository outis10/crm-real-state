package com.outis.crm.service.impl;

import com.outis.crm.domain.Property;
import com.outis.crm.repository.PropertyRepository;
import com.outis.crm.service.PropertyService;
import com.outis.crm.service.dto.PropertyDTO;
import com.outis.crm.service.mapper.PropertyMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.outis.crm.domain.Property}.
 */
@Service
@Transactional
public class PropertyServiceImpl implements PropertyService {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyServiceImpl.class);

    private final PropertyRepository propertyRepository;

    private final PropertyMapper propertyMapper;

    public PropertyServiceImpl(PropertyRepository propertyRepository, PropertyMapper propertyMapper) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper = propertyMapper;
    }

    @Override
    public PropertyDTO save(PropertyDTO propertyDTO) {
        LOG.debug("Request to save Property : {}", propertyDTO);
        Property property = propertyMapper.toEntity(propertyDTO);
        property = propertyRepository.save(property);
        return propertyMapper.toDto(property);
    }

    @Override
    public PropertyDTO update(PropertyDTO propertyDTO) {
        LOG.debug("Request to update Property : {}", propertyDTO);
        Property property = propertyMapper.toEntity(propertyDTO);
        property = propertyRepository.save(property);
        return propertyMapper.toDto(property);
    }

    @Override
    public Optional<PropertyDTO> partialUpdate(PropertyDTO propertyDTO) {
        LOG.debug("Request to partially update Property : {}", propertyDTO);

        return propertyRepository
            .findById(propertyDTO.getId())
            .map(existingProperty -> {
                propertyMapper.partialUpdate(existingProperty, propertyDTO);

                return existingProperty;
            })
            .map(propertyRepository::save)
            .map(propertyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PropertyDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Properties");
        return propertyRepository.findAll(pageable).map(propertyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PropertyDTO> findOne(Long id) {
        LOG.debug("Request to get Property : {}", id);
        return propertyRepository.findById(id).map(propertyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Property : {}", id);
        propertyRepository.deleteById(id);
    }
}
