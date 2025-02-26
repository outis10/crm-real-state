package com.outis.realstate.properties.service.mapper;

import com.outis.realstate.properties.domain.Rental;
import com.outis.realstate.properties.service.dto.RentalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rental} and its DTO {@link RentalDTO}.
 */
@Mapper(componentModel = "spring")
public interface RentalMapper extends EntityMapper<RentalDTO, Rental> {}
