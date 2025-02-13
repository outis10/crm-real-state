package com.outis.crm.service.mapper;

import com.outis.crm.domain.NearbyLocation;
import com.outis.crm.domain.Property;
import com.outis.crm.service.dto.NearbyLocationDTO;
import com.outis.crm.service.dto.PropertyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NearbyLocation} and its DTO {@link NearbyLocationDTO}.
 */
@Mapper(componentModel = "spring")
public interface NearbyLocationMapper extends EntityMapper<NearbyLocationDTO, NearbyLocation> {
    @Mapping(target = "property", source = "property", qualifiedByName = "propertyCodeName")
    NearbyLocationDTO toDto(NearbyLocation s);

    @Named("propertyCodeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "codeName", source = "codeName")
    PropertyDTO toDtoPropertyCodeName(Property property);
}
