package com.outis.crm.domain;

import static com.outis.crm.domain.AssertUtils.bigDecimalCompareTo;
import static org.assertj.core.api.Assertions.assertThat;

public class PropertyAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPropertyAllPropertiesEquals(Property expected, Property actual) {
        assertPropertyAutoGeneratedPropertiesEquals(expected, actual);
        assertPropertyAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPropertyAllUpdatablePropertiesEquals(Property expected, Property actual) {
        assertPropertyUpdatableFieldsEquals(expected, actual);
        assertPropertyUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPropertyAutoGeneratedPropertiesEquals(Property expected, Property actual) {
        assertThat(expected)
            .as("Verify Property auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPropertyUpdatableFieldsEquals(Property expected, Property actual) {
        assertThat(expected)
            .as("Verify Property relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getCodeName()).as("check codeName").isEqualTo(actual.getCodeName()))
            .satisfies(e -> assertThat(e.getType()).as("check type").isEqualTo(actual.getType()))
            .satisfies(e -> assertThat(e.getOperationType()).as("check operationType").isEqualTo(actual.getOperationType()))
            .satisfies(e -> assertThat(e.getLocation()).as("check location").isEqualTo(actual.getLocation()))
            .satisfies(e -> assertThat(e.getCity()).as("check city").isEqualTo(actual.getCity()))
            .satisfies(e -> assertThat(e.getState()).as("check state").isEqualTo(actual.getState()))
            .satisfies(e -> assertThat(e.getPostalCode()).as("check postalCode").isEqualTo(actual.getPostalCode()))
            .satisfies(e -> assertThat(e.getPrice()).as("check price").usingComparator(bigDecimalCompareTo).isEqualTo(actual.getPrice()))
            .satisfies(e ->
                assertThat(e.getRentalPrice())
                    .as("check rentalPrice")
                    .usingComparator(bigDecimalCompareTo)
                    .isEqualTo(actual.getRentalPrice())
            )
            .satisfies(e -> assertThat(e.getArea()).as("check area").isEqualTo(actual.getArea()))
            .satisfies(e -> assertThat(e.getBedrooms()).as("check bedrooms").isEqualTo(actual.getBedrooms()))
            .satisfies(e -> assertThat(e.getBathrooms()).as("check bathrooms").isEqualTo(actual.getBathrooms()))
            .satisfies(e -> assertThat(e.getAppreciationRate()).as("check appreciationRate").isEqualTo(actual.getAppreciationRate()))
            .satisfies(e -> assertThat(e.getFeatures()).as("check features").isEqualTo(actual.getFeatures()))
            .satisfies(e -> assertThat(e.getStatus()).as("check status").isEqualTo(actual.getStatus()))
            .satisfies(e -> assertThat(e.getImages()).as("check images").isEqualTo(actual.getImages()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertPropertyUpdatableRelationshipsEquals(Property expected, Property actual) {
        // empty method
    }
}
