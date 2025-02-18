package com.outis.crm.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class NearbyLocationAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNearbyLocationAllPropertiesEquals(NearbyLocation expected, NearbyLocation actual) {
        assertNearbyLocationAutoGeneratedPropertiesEquals(expected, actual);
        assertNearbyLocationAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNearbyLocationAllUpdatablePropertiesEquals(NearbyLocation expected, NearbyLocation actual) {
        assertNearbyLocationUpdatableFieldsEquals(expected, actual);
        assertNearbyLocationUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNearbyLocationAutoGeneratedPropertiesEquals(NearbyLocation expected, NearbyLocation actual) {
        assertThat(actual)
            .as("Verify NearbyLocation auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNearbyLocationUpdatableFieldsEquals(NearbyLocation expected, NearbyLocation actual) {
        assertThat(actual)
            .as("Verify NearbyLocation relevant properties")
            .satisfies(a -> assertThat(a.getName()).as("check name").isEqualTo(expected.getName()))
            .satisfies(a -> assertThat(a.getType()).as("check type").isEqualTo(expected.getType()))
            .satisfies(a -> assertThat(a.getDistance()).as("check distance").isEqualTo(expected.getDistance()))
            .satisfies(a -> assertThat(a.getCoordinates()).as("check coordinates").isEqualTo(expected.getCoordinates()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertNearbyLocationUpdatableRelationshipsEquals(NearbyLocation expected, NearbyLocation actual) {
        assertThat(actual)
            .as("Verify NearbyLocation relationships")
            .satisfies(a -> assertThat(a.getProperty()).as("check property").isEqualTo(expected.getProperty()));
    }
}
