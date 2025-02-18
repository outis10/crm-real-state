package com.outis.crm.service.impl;

import com.outis.crm.domain.Charge;
import com.outis.crm.repository.ChargeRepository;
import com.outis.crm.service.ChargeService;
import com.outis.crm.service.dto.ChargeDTO;
import com.outis.crm.service.mapper.ChargeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.outis.crm.domain.Charge}.
 */
@Service
@Transactional
public class ChargeServiceImpl implements ChargeService {

    private static final Logger LOG = LoggerFactory.getLogger(ChargeServiceImpl.class);

    private final ChargeRepository chargeRepository;

    private final ChargeMapper chargeMapper;

    public ChargeServiceImpl(ChargeRepository chargeRepository, ChargeMapper chargeMapper) {
        this.chargeRepository = chargeRepository;
        this.chargeMapper = chargeMapper;
    }

    @Override
    public ChargeDTO save(ChargeDTO chargeDTO) {
        LOG.debug("Request to save Charge : {}", chargeDTO);
        Charge charge = chargeMapper.toEntity(chargeDTO);
        charge = chargeRepository.save(charge);
        return chargeMapper.toDto(charge);
    }

    @Override
    public ChargeDTO update(ChargeDTO chargeDTO) {
        LOG.debug("Request to update Charge : {}", chargeDTO);
        Charge charge = chargeMapper.toEntity(chargeDTO);
        charge = chargeRepository.save(charge);
        return chargeMapper.toDto(charge);
    }

    @Override
    public Optional<ChargeDTO> partialUpdate(ChargeDTO chargeDTO) {
        LOG.debug("Request to partially update Charge : {}", chargeDTO);

        return chargeRepository
            .findById(chargeDTO.getId())
            .map(existingCharge -> {
                chargeMapper.partialUpdate(existingCharge, chargeDTO);

                return existingCharge;
            })
            .map(chargeRepository::save)
            .map(chargeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChargeDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Charges");
        return chargeRepository.findAll(pageable).map(chargeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ChargeDTO> findOne(Long id) {
        LOG.debug("Request to get Charge : {}", id);
        return chargeRepository.findById(id).map(chargeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Charge : {}", id);
        chargeRepository.deleteById(id);
    }
}
