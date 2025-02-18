package com.outis.crm.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PropertyTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Property getPropertySample1() {
        return new Property()
            .id(1L)
            .name("name1")
            .codeName("codeName1")
            .type("type1")
            .location("location1")
            .city("city1")
            .state("state1")
            .postalCode("postalCode1")
            .area(1)
            .bedrooms(1)
            .bathrooms(1)
            .features("features1")
            .images("images1");
    }

    public static Property getPropertySample2() {
        return new Property()
            .id(2L)
            .name("name2")
            .codeName("codeName2")
            .type("type2")
            .location("location2")
            .city("city2")
            .state("state2")
            .postalCode("postalCode2")
            .area(2)
            .bedrooms(2)
            .bathrooms(2)
            .features("features2")
            .images("images2");
    }

    public static Property getPropertyRandomSampleGenerator() {
        return new Property()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .codeName(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .location(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString())
            .state(UUID.randomUUID().toString())
            .postalCode(UUID.randomUUID().toString())
            .area(intCount.incrementAndGet())
            .bedrooms(intCount.incrementAndGet())
            .bathrooms(intCount.incrementAndGet())
            .features(UUID.randomUUID().toString())
            .images(UUID.randomUUID().toString());
    }
}
