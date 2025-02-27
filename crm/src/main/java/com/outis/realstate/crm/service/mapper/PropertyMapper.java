package com.outis.realstate.crm.service.mapper;

import com.outis.realstate.crm.domain.Property;
import com.outis.realstate.crm.service.dto.PropertyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Property} and its DTO {@link PropertyDTO}.
 */
@Mapper(componentModel = "spring")
public interface PropertyMapper extends EntityMapper<PropertyDTO, Property> {}
