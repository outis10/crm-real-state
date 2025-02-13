package com.outis.crm.service.mapper;

import static com.outis.crm.domain.RentalContractAsserts.*;
import static com.outis.crm.domain.RentalContractTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RentalContractMapperTest {

    private RentalContractMapper rentalContractMapper;

    @BeforeEach
    void setUp() {
        rentalContractMapper = new RentalContractMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRentalContractSample1();
        var actual = rentalContractMapper.toEntity(rentalContractMapper.toDto(expected));
        assertRentalContractAllPropertiesEquals(expected, actual);
    }
}
