package com.outis.crm.domain.enumeration;

/**
 * The ContractStatusEnum enumeration.
 */
public enum ContractStatusEnum {
    ACTIVE("Active"),
    FINISHED("Finished"),
    CANCELED("Canceled");

    private final String value;

    ContractStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
