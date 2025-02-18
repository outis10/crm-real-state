package com.outis.crm.service;

import com.outis.crm.service.dto.NearbyLocationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.outis.crm.domain.NearbyLocation}.
 */
public interface NearbyLocationService {
    /**
     * Save a nearbyLocation.
     *
     * @param nearbyLocationDTO the entity to save.
     * @return the persisted entity.
     */
    NearbyLocationDTO save(NearbyLocationDTO nearbyLocationDTO);

    /**
     * Updates a nearbyLocation.
     *
     * @param nearbyLocationDTO the entity to update.
     * @return the persisted entity.
     */
    NearbyLocationDTO update(NearbyLocationDTO nearbyLocationDTO);

    /**
     * Partially updates a nearbyLocation.
     *
     * @param nearbyLocationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NearbyLocationDTO> partialUpdate(NearbyLocationDTO nearbyLocationDTO);

    /**
     * Get all the nearbyLocations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NearbyLocationDTO> findAll(Pageable pageable);

    /**
     * Get all the nearbyLocations with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NearbyLocationDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" nearbyLocation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NearbyLocationDTO> findOne(Long id);

    /**
     * Delete the "id" nearbyLocation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
