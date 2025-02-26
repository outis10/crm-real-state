package com.outis.realstate.properties.service.mapper;

import com.outis.realstate.properties.domain.Quotation;
import com.outis.realstate.properties.service.dto.QuotationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Quotation} and its DTO {@link QuotationDTO}.
 */
@Mapper(componentModel = "spring")
public interface QuotationMapper extends EntityMapper<QuotationDTO, Quotation> {}
