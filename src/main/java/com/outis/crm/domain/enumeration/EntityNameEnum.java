package com.outis.crm.domain.enumeration;

/**
 * The EntityNameEnum enumeration.
 */
public enum EntityNameEnum {
    PROPERTY("Property"),
    CUSTOMER("Customer"),
    RENTAL_CONTRACT("Rental");

    private final String value;

    EntityNameEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
