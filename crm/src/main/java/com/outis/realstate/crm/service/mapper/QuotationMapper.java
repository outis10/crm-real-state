package com.outis.realstate.crm.service.mapper;

import com.outis.realstate.crm.domain.Quotation;
import com.outis.realstate.crm.service.dto.QuotationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Quotation} and its DTO {@link QuotationDTO}.
 */
@Mapper(componentModel = "spring")
public interface QuotationMapper extends EntityMapper<QuotationDTO, Quotation> {}
