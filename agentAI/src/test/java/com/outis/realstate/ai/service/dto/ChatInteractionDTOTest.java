package com.outis.realstate.ai.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.outis.realstate.ai.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChatInteractionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChatInteractionDTO.class);
        ChatInteractionDTO chatInteractionDTO1 = new ChatInteractionDTO();
        chatInteractionDTO1.setId("id1");
        ChatInteractionDTO chatInteractionDTO2 = new ChatInteractionDTO();
        assertThat(chatInteractionDTO1).isNotEqualTo(chatInteractionDTO2);
        chatInteractionDTO2.setId(chatInteractionDTO1.getId());
        assertThat(chatInteractionDTO1).isEqualTo(chatInteractionDTO2);
        chatInteractionDTO2.setId("id2");
        assertThat(chatInteractionDTO1).isNotEqualTo(chatInteractionDTO2);
        chatInteractionDTO1.setId(null);
        assertThat(chatInteractionDTO1).isNotEqualTo(chatInteractionDTO2);
    }
}
