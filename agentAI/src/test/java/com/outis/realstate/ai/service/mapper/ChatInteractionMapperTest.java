package com.outis.realstate.ai.service.mapper;

import static com.outis.realstate.ai.domain.ChatInteractionAsserts.*;
import static com.outis.realstate.ai.domain.ChatInteractionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChatInteractionMapperTest {

    private ChatInteractionMapper chatInteractionMapper;

    @BeforeEach
    void setUp() {
        chatInteractionMapper = new ChatInteractionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getChatInteractionSample1();
        var actual = chatInteractionMapper.toEntity(chatInteractionMapper.toDto(expected));
        assertChatInteractionAllPropertiesEquals(expected, actual);
    }
}
