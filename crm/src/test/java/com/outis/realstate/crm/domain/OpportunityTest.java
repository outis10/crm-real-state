package com.outis.realstate.crm.domain;

import static com.outis.realstate.crm.domain.CustomerTestSamples.*;
import static com.outis.realstate.crm.domain.OpportunityTestSamples.*;
import static com.outis.realstate.crm.domain.RentalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.outis.realstate.crm.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OpportunityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Opportunity.class);
        Opportunity opportunity1 = getOpportunitySample1();
        Opportunity opportunity2 = new Opportunity();
        assertThat(opportunity1).isNotEqualTo(opportunity2);

        opportunity2.setId(opportunity1.getId());
        assertThat(opportunity1).isEqualTo(opportunity2);

        opportunity2 = getOpportunitySample2();
        assertThat(opportunity1).isNotEqualTo(opportunity2);
    }

    @Test
    void customerTest() {
        Opportunity opportunity = getOpportunityRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        opportunity.setCustomer(customerBack);
        assertThat(opportunity.getCustomer()).isEqualTo(customerBack);

        opportunity.customer(null);
        assertThat(opportunity.getCustomer()).isNull();
    }

    @Test
    void rentalTest() {
        Opportunity opportunity = getOpportunityRandomSampleGenerator();
        Rental rentalBack = getRentalRandomSampleGenerator();

        opportunity.addRental(rentalBack);
        assertThat(opportunity.getRentals()).containsOnly(rentalBack);
        assertThat(rentalBack.getOpportunity()).isEqualTo(opportunity);

        opportunity.removeRental(rentalBack);
        assertThat(opportunity.getRentals()).doesNotContain(rentalBack);
        assertThat(rentalBack.getOpportunity()).isNull();

        opportunity.rentals(new HashSet<>(Set.of(rentalBack)));
        assertThat(opportunity.getRentals()).containsOnly(rentalBack);
        assertThat(rentalBack.getOpportunity()).isEqualTo(opportunity);

        opportunity.setRentals(new HashSet<>());
        assertThat(opportunity.getRentals()).doesNotContain(rentalBack);
        assertThat(rentalBack.getOpportunity()).isNull();
    }
}
