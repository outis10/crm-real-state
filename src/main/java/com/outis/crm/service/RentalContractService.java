package com.outis.crm.service;

import com.outis.crm.service.dto.RentalContractDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.outis.crm.domain.RentalContract}.
 */
public interface RentalContractService {
    /**
     * Save a rentalContract.
     *
     * @param rentalContractDTO the entity to save.
     * @return the persisted entity.
     */
    RentalContractDTO save(RentalContractDTO rentalContractDTO);

    /**
     * Updates a rentalContract.
     *
     * @param rentalContractDTO the entity to update.
     * @return the persisted entity.
     */
    RentalContractDTO update(RentalContractDTO rentalContractDTO);

    /**
     * Partially updates a rentalContract.
     *
     * @param rentalContractDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RentalContractDTO> partialUpdate(RentalContractDTO rentalContractDTO);

    /**
     * Get all the rentalContracts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RentalContractDTO> findAll(Pageable pageable);

    /**
     * Get the "id" rentalContract.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RentalContractDTO> findOne(Long id);

    /**
     * Delete the "id" rentalContract.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
