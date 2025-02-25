package com.outis.crm.domain;

import static com.outis.crm.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class ChargeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertChargeAllPropertiesEquals(Charge expected, Charge actual) {
        assertChargeAutoGeneratedPropertiesEquals(expected, actual);
        assertChargeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertChargeAllUpdatablePropertiesEquals(Charge expected, Charge actual) {
        assertChargeUpdatableFieldsEquals(expected, actual);
        assertChargeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertChargeAutoGeneratedPropertiesEquals(Charge expected, Charge actual) {
        assertThat(actual)
            .as("Verify Charge auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertChargeUpdatableFieldsEquals(Charge expected, Charge actual) {
        assertThat(actual)
            .as("Verify Charge relevant properties")
            .satisfies(a -> assertThat(a.getType()).as("check type").isEqualTo(expected.getType()))
            .satisfies(a ->
                assertThat(a.getAmount()).as("check amount").usingComparator(bigDecimalCompareTo).isEqualTo(expected.getAmount())
            )
            .satisfies(a -> assertThat(a.getDueDate()).as("check dueDate").isEqualTo(expected.getDueDate()))
            .satisfies(a -> assertThat(a.getStatus()).as("check status").isEqualTo(expected.getStatus()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertChargeUpdatableRelationshipsEquals(Charge expected, Charge actual) {
        assertThat(actual)
            .as("Verify Charge relationships")
            .satisfies(a -> assertThat(a.getCustomer()).as("check customer").isEqualTo(expected.getCustomer()))
            .satisfies(a -> assertThat(a.getRentalContract()).as("check rentalContract").isEqualTo(expected.getRentalContract()));
    }
}
