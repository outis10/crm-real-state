package com.outis.realstate.crm.service.mapper;

import com.outis.realstate.crm.domain.Contact;
import com.outis.realstate.crm.domain.Customer;
import com.outis.realstate.crm.service.dto.ContactDTO;
import com.outis.realstate.crm.service.dto.CustomerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contact} and its DTO {@link ContactDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContactMapper extends EntityMapper<ContactDTO, Contact> {
    @Mapping(target = "assignedTo", source = "assignedTo", qualifiedByName = "customerEmail")
    ContactDTO toDto(Contact s);

    @Named("customerEmail")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    CustomerDTO toDtoCustomerEmail(Customer customer);
}
