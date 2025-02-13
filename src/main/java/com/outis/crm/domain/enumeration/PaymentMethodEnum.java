package com.outis.crm.domain.enumeration;

/**
 * The PaymentMethodEnum enumeration.
 */
public enum PaymentMethodEnum {
    TRANSFER("Transfer"),
    CARD("Card"),
    CASH("Cash");

    private final String value;

    PaymentMethodEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
