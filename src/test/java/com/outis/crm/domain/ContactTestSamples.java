package com.outis.crm.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ContactTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Contact getContactSample1() {
        return new Contact()
            .id(1L)
            .firstName("firstName1")
            .middleName("middleName1")
            .lastName("lastName1")
            .email("email1")
            .phone("phone1")
            .address("address1")
            .city("city1")
            .state("state1")
            .postalCode("postalCode1")
            .country("country1")
            .socialMediaProfiles("socialMediaProfiles1");
    }

    public static Contact getContactSample2() {
        return new Contact()
            .id(2L)
            .firstName("firstName2")
            .middleName("middleName2")
            .lastName("lastName2")
            .email("email2")
            .phone("phone2")
            .address("address2")
            .city("city2")
            .state("state2")
            .postalCode("postalCode2")
            .country("country2")
            .socialMediaProfiles("socialMediaProfiles2");
    }

    public static Contact getContactRandomSampleGenerator() {
        return new Contact()
            .id(longCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .middleName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString())
            .state(UUID.randomUUID().toString())
            .postalCode(UUID.randomUUID().toString())
            .country(UUID.randomUUID().toString())
            .socialMediaProfiles(UUID.randomUUID().toString());
    }
}
