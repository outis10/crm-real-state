package com.outis.realstate.ai.domain;

import static com.outis.realstate.ai.domain.ChatInteractionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.outis.realstate.ai.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChatInteractionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChatInteraction.class);
        ChatInteraction chatInteraction1 = getChatInteractionSample1();
        ChatInteraction chatInteraction2 = new ChatInteraction();
        assertThat(chatInteraction1).isNotEqualTo(chatInteraction2);

        chatInteraction2.setId(chatInteraction1.getId());
        assertThat(chatInteraction1).isEqualTo(chatInteraction2);

        chatInteraction2 = getChatInteractionSample2();
        assertThat(chatInteraction1).isNotEqualTo(chatInteraction2);
    }
}
