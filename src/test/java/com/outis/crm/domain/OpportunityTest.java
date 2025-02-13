package com.outis.crm.domain;

import static com.outis.crm.domain.CustomerTestSamples.*;
import static com.outis.crm.domain.OpportunityTestSamples.*;
import static com.outis.crm.domain.PropertyTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.outis.crm.web.rest.TestUtil;
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
    void propertyTest() {
        Opportunity opportunity = getOpportunityRandomSampleGenerator();
        Property propertyBack = getPropertyRandomSampleGenerator();

        opportunity.setProperty(propertyBack);
        assertThat(opportunity.getProperty()).isEqualTo(propertyBack);

        opportunity.property(null);
        assertThat(opportunity.getProperty()).isNull();
    }
}
