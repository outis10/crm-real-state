package com.outis.crm.service.mapper;

import static com.outis.crm.domain.QuotationAsserts.*;
import static com.outis.crm.domain.QuotationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuotationMapperTest {

    private QuotationMapper quotationMapper;

    @BeforeEach
    void setUp() {
        quotationMapper = new QuotationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getQuotationSample1();
        var actual = quotationMapper.toEntity(quotationMapper.toDto(expected));
        assertQuotationAllPropertiesEquals(expected, actual);
    }
}
