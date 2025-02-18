package com.outis.crm.service.impl;

import com.outis.crm.domain.Quotation;
import com.outis.crm.repository.QuotationRepository;
import com.outis.crm.service.QuotationService;
import com.outis.crm.service.dto.QuotationDTO;
import com.outis.crm.service.mapper.QuotationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.outis.crm.domain.Quotation}.
 */
@Service
@Transactional
public class QuotationServiceImpl implements QuotationService {

    private static final Logger LOG = LoggerFactory.getLogger(QuotationServiceImpl.class);

    private final QuotationRepository quotationRepository;

    private final QuotationMapper quotationMapper;

    public QuotationServiceImpl(QuotationRepository quotationRepository, QuotationMapper quotationMapper) {
        this.quotationRepository = quotationRepository;
        this.quotationMapper = quotationMapper;
    }

    @Override
    public QuotationDTO save(QuotationDTO quotationDTO) {
        LOG.debug("Request to save Quotation : {}", quotationDTO);
        Quotation quotation = quotationMapper.toEntity(quotationDTO);
        quotation = quotationRepository.save(quotation);
        return quotationMapper.toDto(quotation);
    }

    @Override
    public QuotationDTO update(QuotationDTO quotationDTO) {
        LOG.debug("Request to update Quotation : {}", quotationDTO);
        Quotation quotation = quotationMapper.toEntity(quotationDTO);
        quotation = quotationRepository.save(quotation);
        return quotationMapper.toDto(quotation);
    }

    @Override
    public Optional<QuotationDTO> partialUpdate(QuotationDTO quotationDTO) {
        LOG.debug("Request to partially update Quotation : {}", quotationDTO);

        return quotationRepository
            .findById(quotationDTO.getId())
            .map(existingQuotation -> {
                quotationMapper.partialUpdate(existingQuotation, quotationDTO);

                return existingQuotation;
            })
            .map(quotationRepository::save)
            .map(quotationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuotationDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Quotations");
        return quotationRepository.findAll(pageable).map(quotationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<QuotationDTO> findOne(Long id) {
        LOG.debug("Request to get Quotation : {}", id);
        return quotationRepository.findById(id).map(quotationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Quotation : {}", id);
        quotationRepository.deleteById(id);
    }
}
