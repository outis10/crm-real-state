package com.outis.realstate.properties.service.impl;

import com.outis.realstate.properties.domain.Sale;
import com.outis.realstate.properties.repository.SaleRepository;
import com.outis.realstate.properties.service.SaleService;
import com.outis.realstate.properties.service.dto.SaleDTO;
import com.outis.realstate.properties.service.mapper.SaleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.outis.realstate.properties.domain.Sale}.
 */
@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    private static final Logger LOG = LoggerFactory.getLogger(SaleServiceImpl.class);

    private final SaleRepository saleRepository;

    private final SaleMapper saleMapper;

    public SaleServiceImpl(SaleRepository saleRepository, SaleMapper saleMapper) {
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
    }

    @Override
    public SaleDTO save(SaleDTO saleDTO) {
        LOG.debug("Request to save Sale : {}", saleDTO);
        Sale sale = saleMapper.toEntity(saleDTO);
        sale = saleRepository.save(sale);
        return saleMapper.toDto(sale);
    }

    @Override
    public SaleDTO update(SaleDTO saleDTO) {
        LOG.debug("Request to update Sale : {}", saleDTO);
        Sale sale = saleMapper.toEntity(saleDTO);
        sale = saleRepository.save(sale);
        return saleMapper.toDto(sale);
    }

    @Override
    public Optional<SaleDTO> partialUpdate(SaleDTO saleDTO) {
        LOG.debug("Request to partially update Sale : {}", saleDTO);

        return saleRepository
            .findById(saleDTO.getId())
            .map(existingSale -> {
                saleMapper.partialUpdate(existingSale, saleDTO);

                return existingSale;
            })
            .map(saleRepository::save)
            .map(saleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SaleDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Sales");
        return saleRepository.findAll(pageable).map(saleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SaleDTO> findOne(Long id) {
        LOG.debug("Request to get Sale : {}", id);
        return saleRepository.findById(id).map(saleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Sale : {}", id);
        saleRepository.deleteById(id);
    }
}
