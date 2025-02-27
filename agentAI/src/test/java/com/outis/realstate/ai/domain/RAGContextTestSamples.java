package com.outis.realstate.ai.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RAGContextTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static RAGContext getRAGContextSample1() {
        return new RAGContext().id("id1").entityId(1L).contextText("contextText1");
    }

    public static RAGContext getRAGContextSample2() {
        return new RAGContext().id("id2").entityId(2L).contextText("contextText2");
    }

    public static RAGContext getRAGContextRandomSampleGenerator() {
        return new RAGContext()
            .id(UUID.randomUUID().toString())
            .entityId(longCount.incrementAndGet())
            .contextText(UUID.randomUUID().toString());
    }
}
