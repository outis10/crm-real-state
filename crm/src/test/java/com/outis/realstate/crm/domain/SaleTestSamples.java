package com.outis.realstate.crm.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SaleTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sale getSaleSample1() {
        return new Sale().id(1L).propertyId(1L).customerId(1L).oportunityId(1L);
    }

    public static Sale getSaleSample2() {
        return new Sale().id(2L).propertyId(2L).customerId(2L).oportunityId(2L);
    }

    public static Sale getSaleRandomSampleGenerator() {
        return new Sale()
            .id(longCount.incrementAndGet())
            .propertyId(longCount.incrementAndGet())
            .customerId(longCount.incrementAndGet())
            .oportunityId(longCount.incrementAndGet());
    }
}
