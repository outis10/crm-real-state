package com.outis.crm.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class OpportunityTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Opportunity getOpportunitySample1() {
        return new Opportunity().id(1L).name("name1").probability(1);
    }

    public static Opportunity getOpportunitySample2() {
        return new Opportunity().id(2L).name("name2").probability(2);
    }

    public static Opportunity getOpportunityRandomSampleGenerator() {
        return new Opportunity().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).probability(intCount.incrementAndGet());
    }
}
