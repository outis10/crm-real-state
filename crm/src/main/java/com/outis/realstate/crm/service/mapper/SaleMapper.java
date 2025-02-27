package com.outis.realstate.crm.service.mapper;

import com.outis.realstate.crm.domain.Sale;
import com.outis.realstate.crm.service.dto.SaleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sale} and its DTO {@link SaleDTO}.
 */
@Mapper(componentModel = "spring")
public interface SaleMapper extends EntityMapper<SaleDTO, Sale> {}
