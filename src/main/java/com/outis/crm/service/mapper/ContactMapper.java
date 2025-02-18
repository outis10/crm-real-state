package com.outis.crm.service.mapper;

import com.outis.crm.domain.Contact;
import com.outis.crm.domain.Customer;
import com.outis.crm.service.dto.ContactDTO;
import com.outis.crm.service.dto.CustomerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contact} and its DTO {@link ContactDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContactMapper extends EntityMapper<ContactDTO, Contact> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    ContactDTO toDto(Contact s);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);
}
