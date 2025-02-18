package com.outis.crm.domain;

import static com.outis.crm.domain.ChatInteractionTestSamples.*;
import static com.outis.crm.domain.CustomerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.outis.crm.web.rest.TestUtil;
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

    @Test
    void customerTest() {
        ChatInteraction chatInteraction = getChatInteractionRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        chatInteraction.setCustomer(customerBack);
        assertThat(chatInteraction.getCustomer()).isEqualTo(customerBack);

        chatInteraction.customer(null);
        assertThat(chatInteraction.getCustomer()).isNull();
    }
}
