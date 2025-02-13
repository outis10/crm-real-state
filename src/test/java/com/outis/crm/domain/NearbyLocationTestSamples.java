package com.outis.crm.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class NearbyLocationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static NearbyLocation getNearbyLocationSample1() {
        return new NearbyLocation().id(1L).name("name1").type("type1").coordinates("coordinates1");
    }

    public static NearbyLocation getNearbyLocationSample2() {
        return new NearbyLocation().id(2L).name("name2").type("type2").coordinates("coordinates2");
    }

    public static NearbyLocation getNearbyLocationRandomSampleGenerator() {
        return new NearbyLocation()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .coordinates(UUID.randomUUID().toString());
    }
}
