package com.outis.crm.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class QuotationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Quotation getQuotationSample1() {
        return new Quotation().id(1L).comments("comments1");
    }

    public static Quotation getQuotationSample2() {
        return new Quotation().id(2L).comments("comments2");
    }

    public static Quotation getQuotationRandomSampleGenerator() {
        return new Quotation().id(longCount.incrementAndGet()).comments(UUID.randomUUID().toString());
    }
}
