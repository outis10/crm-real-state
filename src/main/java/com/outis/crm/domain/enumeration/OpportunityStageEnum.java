package com.outis.crm.domain.enumeration;

/**
 * The OpportunityStageEnum enumeration.
 */
public enum OpportunityStageEnum {
    PROSPECTING("Prospecting"),
    QUALIFICATION("Qualification"),
    PROPOSAL("Proposal"),
    NEGOTIATION("Negotiation"),
    CLOSED_WON("Won"),
    CLOSED_LOST("Lost");

    private final String value;

    OpportunityStageEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
