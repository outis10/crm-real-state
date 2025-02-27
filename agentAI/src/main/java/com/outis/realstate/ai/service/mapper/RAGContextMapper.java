package com.outis.realstate.ai.service.mapper;

import com.outis.realstate.ai.domain.RAGContext;
import com.outis.realstate.ai.service.dto.RAGContextDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RAGContext} and its DTO {@link RAGContextDTO}.
 */
@Mapper(componentModel = "spring")
public interface RAGContextMapper extends EntityMapper<RAGContextDTO, RAGContext> {}
