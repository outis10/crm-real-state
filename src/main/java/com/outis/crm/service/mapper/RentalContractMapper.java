package com.outis.crm.service.mapper;

import com.outis.crm.domain.Customer;
import com.outis.crm.domain.Property;
import com.outis.crm.domain.RentalContract;
import com.outis.crm.service.dto.CustomerDTO;
import com.outis.crm.service.dto.PropertyDTO;
import com.outis.crm.service.dto.RentalContractDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RentalContract} and its DTO {@link RentalContractDTO}.
 */
@Mapper(componentModel = "spring")
public interface RentalContractMapper extends EntityMapper<RentalContractDTO, RentalContract> {
    @Mapping(target = "property", source = "property", qualifiedByName = "propertyId")
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    RentalContractDTO toDto(RentalContract s);

    @Named("propertyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PropertyDTO toDtoPropertyId(Property property);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);
}
