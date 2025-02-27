package com.outis.realstate.ai.service.impl;

import com.outis.realstate.ai.domain.RAGContext;
import com.outis.realstate.ai.repository.RAGContextRepository;
import com.outis.realstate.ai.service.RAGContextService;
import com.outis.realstate.ai.service.dto.RAGContextDTO;
import com.outis.realstate.ai.service.mapper.RAGContextMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.outis.realstate.ai.domain.RAGContext}.
 */
@Service
public class RAGContextServiceImpl implements RAGContextService {

    private static final Logger LOG = LoggerFactory.getLogger(RAGContextServiceImpl.class);

    private final RAGContextRepository rAGContextRepository;

    private final RAGContextMapper rAGContextMapper;

    public RAGContextServiceImpl(RAGContextRepository rAGContextRepository, RAGContextMapper rAGContextMapper) {
        this.rAGContextRepository = rAGContextRepository;
        this.rAGContextMapper = rAGContextMapper;
    }

    @Override
    public RAGContextDTO save(RAGContextDTO rAGContextDTO) {
        LOG.debug("Request to save RAGContext : {}", rAGContextDTO);
        RAGContext rAGContext = rAGContextMapper.toEntity(rAGContextDTO);
        rAGContext = rAGContextRepository.save(rAGContext);
        return rAGContextMapper.toDto(rAGContext);
    }

    @Override
    public RAGContextDTO update(RAGContextDTO rAGContextDTO) {
        LOG.debug("Request to update RAGContext : {}", rAGContextDTO);
        RAGContext rAGContext = rAGContextMapper.toEntity(rAGContextDTO);
        rAGContext = rAGContextRepository.save(rAGContext);
        return rAGContextMapper.toDto(rAGContext);
    }

    @Override
    public Optional<RAGContextDTO> partialUpdate(RAGContextDTO rAGContextDTO) {
        LOG.debug("Request to partially update RAGContext : {}", rAGContextDTO);

        return rAGContextRepository
            .findById(rAGContextDTO.getId())
            .map(existingRAGContext -> {
                rAGContextMapper.partialUpdate(existingRAGContext, rAGContextDTO);

                return existingRAGContext;
            })
            .map(rAGContextRepository::save)
            .map(rAGContextMapper::toDto);
    }

    @Override
    public Page<RAGContextDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all RAGContexts");
        return rAGContextRepository.findAll(pageable).map(rAGContextMapper::toDto);
    }

    @Override
    public Optional<RAGContextDTO> findOne(String id) {
        LOG.debug("Request to get RAGContext : {}", id);
        return rAGContextRepository.findById(id).map(rAGContextMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete RAGContext : {}", id);
        rAGContextRepository.deleteById(id);
    }
}
