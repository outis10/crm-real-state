package com.outis.crm.domain.enumeration;

/**
 * The ChargeTypeEnum enumeration.
 */
public enum ChargeTypeEnum {
    LEASE("Lease"),
    MAINTAIN("Maintain"),
    INSTALLMENT_PAYMENT("Installment_Payment"),
    OTHER("Other");

    private final String value;

    ChargeTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
