package com.outis.crm.service.mapper;

import static com.outis.crm.domain.ChargeAsserts.*;
import static com.outis.crm.domain.ChargeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChargeMapperTest {

    private ChargeMapper chargeMapper;

    @BeforeEach
    void setUp() {
        chargeMapper = new ChargeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getChargeSample1();
        var actual = chargeMapper.toEntity(chargeMapper.toDto(expected));
        assertChargeAllPropertiesEquals(expected, actual);
    }
}
