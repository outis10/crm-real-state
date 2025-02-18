package com.outis.crm.domain.enumeration;

/**
 * The PropertyStatusEnum enumeration.
 */
public enum PropertyStatusEnum {
    AVAILABLE("Available"),
    SOLD("Sold"),
    LEASED("Leased");

    private final String value;

    PropertyStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
