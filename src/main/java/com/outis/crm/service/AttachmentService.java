package com.outis.crm.service;

import com.outis.crm.service.dto.AttachmentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.outis.crm.domain.Attachment}.
 */
public interface AttachmentService {
    /**
     * Save a attachment.
     *
     * @param attachmentDTO the entity to save.
     * @return the persisted entity.
     */
    AttachmentDTO save(AttachmentDTO attachmentDTO);

    /**
     * Updates a attachment.
     *
     * @param attachmentDTO the entity to update.
     * @return the persisted entity.
     */
    AttachmentDTO update(AttachmentDTO attachmentDTO);

    /**
     * Partially updates a attachment.
     *
     * @param attachmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttachmentDTO> partialUpdate(AttachmentDTO attachmentDTO);

    /**
     * Get all the attachments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttachmentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" attachment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttachmentDTO> findOne(Long id);

    /**
     * Delete the "id" attachment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
