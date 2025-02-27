package com.outis.realstate.ai.service;

import com.outis.realstate.ai.service.dto.RAGContextDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.outis.realstate.ai.domain.RAGContext}.
 */
public interface RAGContextService {
    /**
     * Save a rAGContext.
     *
     * @param rAGContextDTO the entity to save.
     * @return the persisted entity.
     */
    RAGContextDTO save(RAGContextDTO rAGContextDTO);

    /**
     * Updates a rAGContext.
     *
     * @param rAGContextDTO the entity to update.
     * @return the persisted entity.
     */
    RAGContextDTO update(RAGContextDTO rAGContextDTO);

    /**
     * Partially updates a rAGContext.
     *
     * @param rAGContextDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RAGContextDTO> partialUpdate(RAGContextDTO rAGContextDTO);

    /**
     * Get all the rAGContexts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RAGContextDTO> findAll(Pageable pageable);

    /**
     * Get the "id" rAGContext.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RAGContextDTO> findOne(String id);

    /**
     * Delete the "id" rAGContext.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
