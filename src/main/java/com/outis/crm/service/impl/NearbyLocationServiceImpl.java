package com.outis.crm.service.impl;

import com.outis.crm.domain.NearbyLocation;
import com.outis.crm.repository.NearbyLocationRepository;
import com.outis.crm.service.NearbyLocationService;
import com.outis.crm.service.dto.NearbyLocationDTO;
import com.outis.crm.service.mapper.NearbyLocationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.outis.crm.domain.NearbyLocation}.
 */
@Service
@Transactional
public class NearbyLocationServiceImpl implements NearbyLocationService {

    private static final Logger LOG = LoggerFactory.getLogger(NearbyLocationServiceImpl.class);

    private final NearbyLocationRepository nearbyLocationRepository;

    private final NearbyLocationMapper nearbyLocationMapper;

    public NearbyLocationServiceImpl(NearbyLocationRepository nearbyLocationRepository, NearbyLocationMapper nearbyLocationMapper) {
        this.nearbyLocationRepository = nearbyLocationRepository;
        this.nearbyLocationMapper = nearbyLocationMapper;
    }

    @Override
    public NearbyLocationDTO save(NearbyLocationDTO nearbyLocationDTO) {
        LOG.debug("Request to save NearbyLocation : {}", nearbyLocationDTO);
        NearbyLocation nearbyLocation = nearbyLocationMapper.toEntity(nearbyLocationDTO);
        nearbyLocation = nearbyLocationRepository.save(nearbyLocation);
        return nearbyLocationMapper.toDto(nearbyLocation);
    }

    @Override
    public NearbyLocationDTO update(NearbyLocationDTO nearbyLocationDTO) {
        LOG.debug("Request to update NearbyLocation : {}", nearbyLocationDTO);
        NearbyLocation nearbyLocation = nearbyLocationMapper.toEntity(nearbyLocationDTO);
        nearbyLocation = nearbyLocationRepository.save(nearbyLocation);
        return nearbyLocationMapper.toDto(nearbyLocation);
    }

    @Override
    public Optional<NearbyLocationDTO> partialUpdate(NearbyLocationDTO nearbyLocationDTO) {
        LOG.debug("Request to partially update NearbyLocation : {}", nearbyLocationDTO);

        return nearbyLocationRepository
            .findById(nearbyLocationDTO.getId())
            .map(existingNearbyLocation -> {
                nearbyLocationMapper.partialUpdate(existingNearbyLocation, nearbyLocationDTO);

                return existingNearbyLocation;
            })
            .map(nearbyLocationRepository::save)
            .map(nearbyLocationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NearbyLocationDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all NearbyLocations");
        return nearbyLocationRepository.findAll(pageable).map(nearbyLocationMapper::toDto);
    }

    public Page<NearbyLocationDTO> findAllWithEagerRelationships(Pageable pageable) {
        return nearbyLocationRepository.findAllWithEagerRelationships(pageable).map(nearbyLocationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NearbyLocationDTO> findOne(Long id) {
        LOG.debug("Request to get NearbyLocation : {}", id);
        return nearbyLocationRepository.findOneWithEagerRelationships(id).map(nearbyLocationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete NearbyLocation : {}", id);
        nearbyLocationRepository.deleteById(id);
    }
}
