package com.outis.crm.service.mapper;

import static com.outis.crm.domain.NearbyLocationAsserts.*;
import static com.outis.crm.domain.NearbyLocationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NearbyLocationMapperTest {

    private NearbyLocationMapper nearbyLocationMapper;

    @BeforeEach
    void setUp() {
        nearbyLocationMapper = new NearbyLocationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getNearbyLocationSample1();
        var actual = nearbyLocationMapper.toEntity(nearbyLocationMapper.toDto(expected));
        assertNearbyLocationAllPropertiesEquals(expected, actual);
    }
}
