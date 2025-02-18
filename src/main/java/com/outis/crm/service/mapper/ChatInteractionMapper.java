package com.outis.crm.service.mapper;

import com.outis.crm.domain.ChatInteraction;
import com.outis.crm.domain.Customer;
import com.outis.crm.service.dto.ChatInteractionDTO;
import com.outis.crm.service.dto.CustomerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ChatInteraction} and its DTO {@link ChatInteractionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChatInteractionMapper extends EntityMapper<ChatInteractionDTO, ChatInteraction> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    ChatInteractionDTO toDto(ChatInteraction s);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);
}
