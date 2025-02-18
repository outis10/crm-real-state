package com.outis.crm.service;

import com.outis.crm.service.dto.ChatInteractionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.outis.crm.domain.ChatInteraction}.
 */
public interface ChatInteractionService {
    /**
     * Save a chatInteraction.
     *
     * @param chatInteractionDTO the entity to save.
     * @return the persisted entity.
     */
    ChatInteractionDTO save(ChatInteractionDTO chatInteractionDTO);

    /**
     * Updates a chatInteraction.
     *
     * @param chatInteractionDTO the entity to update.
     * @return the persisted entity.
     */
    ChatInteractionDTO update(ChatInteractionDTO chatInteractionDTO);

    /**
     * Partially updates a chatInteraction.
     *
     * @param chatInteractionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ChatInteractionDTO> partialUpdate(ChatInteractionDTO chatInteractionDTO);

    /**
     * Get all the chatInteractions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChatInteractionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" chatInteraction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChatInteractionDTO> findOne(Long id);

    /**
     * Delete the "id" chatInteraction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
