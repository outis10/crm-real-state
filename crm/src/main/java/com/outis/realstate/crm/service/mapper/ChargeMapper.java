package com.outis.realstate.crm.service.mapper;

import com.outis.realstate.crm.domain.Charge;
import com.outis.realstate.crm.domain.Rental;
import com.outis.realstate.crm.service.dto.ChargeDTO;
import com.outis.realstate.crm.service.dto.RentalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Charge} and its DTO {@link ChargeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChargeMapper extends EntityMapper<ChargeDTO, Charge> {
    @Mapping(target = "rental", source = "rental", qualifiedByName = "rentalId")
    ChargeDTO toDto(Charge s);

    @Named("rentalId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RentalDTO toDtoRentalId(Rental rental);
}
