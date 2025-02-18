package com.outis.crm.service.mapper;

import static com.outis.crm.domain.PropertyAsserts.*;
import static com.outis.crm.domain.PropertyTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PropertyMapperTest {

    private PropertyMapper propertyMapper;

    @BeforeEach
    void setUp() {
        propertyMapper = new PropertyMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPropertySample1();
        var actual = propertyMapper.toEntity(propertyMapper.toDto(expected));
        assertPropertyAllPropertiesEquals(expected, actual);
    }
}
