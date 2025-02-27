package com.outis.realstate.ai.service.mapper;

import static com.outis.realstate.ai.domain.RAGContextAsserts.*;
import static com.outis.realstate.ai.domain.RAGContextTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RAGContextMapperTest {

    private RAGContextMapper rAGContextMapper;

    @BeforeEach
    void setUp() {
        rAGContextMapper = new RAGContextMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRAGContextSample1();
        var actual = rAGContextMapper.toEntity(rAGContextMapper.toDto(expected));
        assertRAGContextAllPropertiesEquals(expected, actual);
    }
}
