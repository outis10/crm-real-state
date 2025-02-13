package com.outis.crm.service.mapper;

import com.outis.crm.domain.Charge;
import com.outis.crm.domain.Customer;
import com.outis.crm.domain.RentalContract;
import com.outis.crm.service.dto.ChargeDTO;
import com.outis.crm.service.dto.CustomerDTO;
import com.outis.crm.service.dto.RentalContractDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Charge} and its DTO {@link ChargeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChargeMapper extends EntityMapper<ChargeDTO, Charge> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    @Mapping(target = "rentalContract", source = "rentalContract", qualifiedByName = "rentalContractId")
    ChargeDTO toDto(Charge s);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);

    @Named("rentalContractId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RentalContractDTO toDtoRentalContractId(RentalContract rentalContract);
}
