package com.outis.realstate.properties.service.mapper;

import com.outis.realstate.properties.domain.Sale;
import com.outis.realstate.properties.service.dto.SaleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sale} and its DTO {@link SaleDTO}.
 */
@Mapper(componentModel = "spring")
public interface SaleMapper extends EntityMapper<SaleDTO, Sale> {}
