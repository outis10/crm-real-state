package com.outis.realstate.properties.service.mapper;

import com.outis.realstate.properties.domain.Payment;
import com.outis.realstate.properties.domain.Rental;
import com.outis.realstate.properties.service.dto.PaymentDTO;
import com.outis.realstate.properties.service.dto.RentalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payment} and its DTO {@link PaymentDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {
    @Mapping(target = "rental", source = "rental", qualifiedByName = "rentalId")
    PaymentDTO toDto(Payment s);

    @Named("rentalId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RentalDTO toDtoRentalId(Rental rental);
}
