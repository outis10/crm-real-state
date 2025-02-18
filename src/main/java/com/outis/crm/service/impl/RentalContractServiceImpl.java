package com.outis.crm.service.impl;

import com.outis.crm.domain.RentalContract;
import com.outis.crm.repository.RentalContractRepository;
import com.outis.crm.service.RentalContractService;
import com.outis.crm.service.dto.RentalContractDTO;
import com.outis.crm.service.mapper.RentalContractMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.outis.crm.domain.RentalContract}.
 */
@Service
@Transactional
public class RentalContractServiceImpl implements RentalContractService {

    private static final Logger LOG = LoggerFactory.getLogger(RentalContractServiceImpl.class);

    private final RentalContractRepository rentalContractRepository;

    private final RentalContractMapper rentalContractMapper;

    public RentalContractServiceImpl(RentalContractRepository rentalContractRepository, RentalContractMapper rentalContractMapper) {
        this.rentalContractRepository = rentalContractRepository;
        this.rentalContractMapper = rentalContractMapper;
    }

    @Override
    public RentalContractDTO save(RentalContractDTO rentalContractDTO) {
        LOG.debug("Request to save RentalContract : {}", rentalContractDTO);
        RentalContract rentalContract = rentalContractMapper.toEntity(rentalContractDTO);
        rentalContract = rentalContractRepository.save(rentalContract);
        return rentalContractMapper.toDto(rentalContract);
    }

    @Override
    public RentalContractDTO update(RentalContractDTO rentalContractDTO) {
        LOG.debug("Request to update RentalContract : {}", rentalContractDTO);
        RentalContract rentalContract = rentalContractMapper.toEntity(rentalContractDTO);
        rentalContract = rentalContractRepository.save(rentalContract);
        return rentalContractMapper.toDto(rentalContract);
    }

    @Override
    public Optional<RentalContractDTO> partialUpdate(RentalContractDTO rentalContractDTO) {
        LOG.debug("Request to partially update RentalContract : {}", rentalContractDTO);

        return rentalContractRepository
            .findById(rentalContractDTO.getId())
            .map(existingRentalContract -> {
                rentalContractMapper.partialUpdate(existingRentalContract, rentalContractDTO);

                return existingRentalContract;
            })
            .map(rentalContractRepository::save)
            .map(rentalContractMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RentalContractDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all RentalContracts");
        return rentalContractRepository.findAll(pageable).map(rentalContractMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RentalContractDTO> findOne(Long id) {
        LOG.debug("Request to get RentalContract : {}", id);
        return rentalContractRepository.findById(id).map(rentalContractMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete RentalContract : {}", id);
        rentalContractRepository.deleteById(id);
    }
}
