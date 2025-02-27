package com.outis.realstate.ai.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ChatInteractionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ChatInteraction getChatInteractionSample1() {
        return new ChatInteraction().id("id1").entityId(1L).customerQuestion("customerQuestion1").chatbotAnswer("chatbotAnswer1");
    }

    public static ChatInteraction getChatInteractionSample2() {
        return new ChatInteraction().id("id2").entityId(2L).customerQuestion("customerQuestion2").chatbotAnswer("chatbotAnswer2");
    }

    public static ChatInteraction getChatInteractionRandomSampleGenerator() {
        return new ChatInteraction()
            .id(UUID.randomUUID().toString())
            .entityId(longCount.incrementAndGet())
            .customerQuestion(UUID.randomUUID().toString())
            .chatbotAnswer(UUID.randomUUID().toString());
    }
}
