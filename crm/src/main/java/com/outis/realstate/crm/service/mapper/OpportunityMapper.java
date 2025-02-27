package com.outis.realstate.crm.service.mapper;

import com.outis.realstate.crm.domain.Customer;
import com.outis.realstate.crm.domain.Opportunity;
import com.outis.realstate.crm.service.dto.CustomerDTO;
import com.outis.realstate.crm.service.dto.OpportunityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Opportunity} and its DTO {@link OpportunityDTO}.
 */
@Mapper(componentModel = "spring")
public interface OpportunityMapper extends EntityMapper<OpportunityDTO, Opportunity> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerEmail")
    OpportunityDTO toDto(Opportunity s);

    @Named("customerEmail")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    CustomerDTO toDtoCustomerEmail(Customer customer);
}
