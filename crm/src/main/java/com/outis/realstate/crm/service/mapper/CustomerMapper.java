package com.outis.realstate.crm.service.mapper;

import com.outis.realstate.crm.domain.Customer;
import com.outis.realstate.crm.service.dto.CustomerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {}
