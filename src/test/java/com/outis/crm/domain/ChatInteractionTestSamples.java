package com.outis.crm.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ChatInteractionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ChatInteraction getChatInteractionSample1() {
        return new ChatInteraction().id(1L).customerMessage("customerMessage1").chatbotResponse("chatbotResponse1");
    }

    public static ChatInteraction getChatInteractionSample2() {
        return new ChatInteraction().id(2L).customerMessage("customerMessage2").chatbotResponse("chatbotResponse2");
    }

    public static ChatInteraction getChatInteractionRandomSampleGenerator() {
        return new ChatInteraction()
            .id(longCount.incrementAndGet())
            .customerMessage(UUID.randomUUID().toString())
            .chatbotResponse(UUID.randomUUID().toString());
    }
}
