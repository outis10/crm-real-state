package com.outis.realstate.properties.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class RentalTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Rental getRentalSample1() {
        return new Rental().id(1L).propertyId(1L).customerId(1L).oportunityId(1L);
    }

    public static Rental getRentalSample2() {
        return new Rental().id(2L).propertyId(2L).customerId(2L).oportunityId(2L);
    }

    public static Rental getRentalRandomSampleGenerator() {
        return new Rental()
            .id(longCount.incrementAndGet())
            .propertyId(longCount.incrementAndGet())
            .customerId(longCount.incrementAndGet())
            .oportunityId(longCount.incrementAndGet());
    }
}
