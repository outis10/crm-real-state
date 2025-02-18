package com.outis.crm.service.mapper;

import com.outis.crm.domain.Customer;
import com.outis.crm.domain.Opportunity;
import com.outis.crm.domain.Property;
import com.outis.crm.service.dto.CustomerDTO;
import com.outis.crm.service.dto.OpportunityDTO;
import com.outis.crm.service.dto.PropertyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Opportunity} and its DTO {@link OpportunityDTO}.
 */
@Mapper(componentModel = "spring")
public interface OpportunityMapper extends EntityMapper<OpportunityDTO, Opportunity> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerEmail")
    @Mapping(target = "property", source = "property", qualifiedByName = "propertyId")
    OpportunityDTO toDto(Opportunity s);

    @Named("customerEmail")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    CustomerDTO toDtoCustomerEmail(Customer customer);

    @Named("propertyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PropertyDTO toDtoPropertyId(Property property);
}
