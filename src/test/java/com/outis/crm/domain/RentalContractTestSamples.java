package com.outis.crm.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class RentalContractTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static RentalContract getRentalContractSample1() {
        return new RentalContract().id(1L);
    }

    public static RentalContract getRentalContractSample2() {
        return new RentalContract().id(2L);
    }

    public static RentalContract getRentalContractRandomSampleGenerator() {
        return new RentalContract().id(longCount.incrementAndGet());
    }
}
