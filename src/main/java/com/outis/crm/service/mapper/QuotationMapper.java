package com.outis.crm.service.mapper;

import com.outis.crm.domain.Customer;
import com.outis.crm.domain.Property;
import com.outis.crm.domain.Quotation;
import com.outis.crm.service.dto.CustomerDTO;
import com.outis.crm.service.dto.PropertyDTO;
import com.outis.crm.service.dto.QuotationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Quotation} and its DTO {@link QuotationDTO}.
 */
@Mapper(componentModel = "spring")
public interface QuotationMapper extends EntityMapper<QuotationDTO, Quotation> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    @Mapping(target = "property", source = "property", qualifiedByName = "propertyId")
    QuotationDTO toDto(Quotation s);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);

    @Named("propertyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PropertyDTO toDtoPropertyId(Property property);
}
