package com.outis.realstate.crm.domain;

import static com.outis.realstate.crm.domain.CustomerTestSamples.*;
import static com.outis.realstate.crm.domain.OpportunityTestSamples.*;
import static com.outis.realstate.crm.domain.PropertyTestSamples.*;
import static com.outis.realstate.crm.domain.RentalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.outis.realstate.crm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RentalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rental.class);
        Rental rental1 = getRentalSample1();
        Rental rental2 = new Rental();
        assertThat(rental1).isNotEqualTo(rental2);

        rental2.setId(rental1.getId());
        assertThat(rental1).isEqualTo(rental2);

        rental2 = getRentalSample2();
        assertThat(rental1).isNotEqualTo(rental2);
    }

    @Test
    void propertyTest() {
        Rental rental = getRentalRandomSampleGenerator();
        Property propertyBack = getPropertyRandomSampleGenerator();

        rental.setProperty(propertyBack);
        assertThat(rental.getProperty()).isEqualTo(propertyBack);

        rental.property(null);
        assertThat(rental.getProperty()).isNull();
    }

    @Test
    void customerTest() {
        Rental rental = getRentalRandomSampleGenerator();
        Customer customerBack = getCustomerRandomSampleGenerator();

        rental.setCustomer(customerBack);
        assertThat(rental.getCustomer()).isEqualTo(customerBack);

        rental.customer(null);
        assertThat(rental.getCustomer()).isNull();
    }

    @Test
    void opportunityTest() {
        Rental rental = getRentalRandomSampleGenerator();
        Opportunity opportunityBack = getOpportunityRandomSampleGenerator();

        rental.setOpportunity(opportunityBack);
        assertThat(rental.getOpportunity()).isEqualTo(opportunityBack);

        rental.opportunity(null);
        assertThat(rental.getOpportunity()).isNull();
    }
}
