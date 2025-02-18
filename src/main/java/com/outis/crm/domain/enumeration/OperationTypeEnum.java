package com.outis.crm.domain.enumeration;

/**
 * The OperationTypeEnum enumeration.
 */
public enum OperationTypeEnum {
    SALES("Sales"),
    LEASE("Lease");

    private final String value;

    OperationTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
