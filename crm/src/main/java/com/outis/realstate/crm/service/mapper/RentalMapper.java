package com.outis.realstate.crm.service.mapper;

import com.outis.realstate.crm.domain.Customer;
import com.outis.realstate.crm.domain.Opportunity;
import com.outis.realstate.crm.domain.Property;
import com.outis.realstate.crm.domain.Rental;
import com.outis.realstate.crm.service.dto.CustomerDTO;
import com.outis.realstate.crm.service.dto.OpportunityDTO;
import com.outis.realstate.crm.service.dto.PropertyDTO;
import com.outis.realstate.crm.service.dto.RentalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rental} and its DTO {@link RentalDTO}.
 */
@Mapper(componentModel = "spring")
public interface RentalMapper extends EntityMapper<RentalDTO, Rental> {
    @Mapping(target = "property", source = "property", qualifiedByName = "propertyCodeName")
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    @Mapping(target = "opportunity", source = "opportunity", qualifiedByName = "opportunityId")
    RentalDTO toDto(Rental s);

    @Named("propertyCodeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "codeName", source = "codeName")
    PropertyDTO toDtoPropertyCodeName(Property property);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);

    @Named("opportunityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OpportunityDTO toDtoOpportunityId(Opportunity opportunity);
}
