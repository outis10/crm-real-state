package com.outis.crm.domain.enumeration;

/**
 * The ChargeStatusEnum enumeration.
 */
public enum ChargeStatusEnum {
    PENDING("Pending"),
    PAID("Paid"),
    OVERDUE("Overdue"),
    IN_REVIEW("In_review");

    private final String value;

    ChargeStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
