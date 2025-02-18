package com.outis.crm.service.mapper;

import com.outis.crm.domain.Customer;
import com.outis.crm.domain.Notification;
import com.outis.crm.service.dto.CustomerDTO;
import com.outis.crm.service.dto.NotificationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notification} and its DTO {@link NotificationDTO}.
 */
@Mapper(componentModel = "spring")
public interface NotificationMapper extends EntityMapper<NotificationDTO, Notification> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    NotificationDTO toDto(Notification s);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);
}
