package com.outis.crm.service.mapper;

import com.outis.crm.domain.Payment;
import com.outis.crm.domain.RentalContract;
import com.outis.crm.service.dto.PaymentDTO;
import com.outis.crm.service.dto.RentalContractDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payment} and its DTO {@link PaymentDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {
    @Mapping(target = "rentralContract", source = "rentralContract", qualifiedByName = "rentalContractId")
    PaymentDTO toDto(Payment s);

    @Named("rentalContractId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RentalContractDTO toDtoRentalContractId(RentalContract rentalContract);
}
